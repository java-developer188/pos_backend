package com.pos.services;

import com.pos.adapter.CustomerAdapterImpl;
import com.pos.adapter.OrderAdapterImpl;
import com.pos.dto.CustomerDto;
import com.pos.dto.OrderDto;
import com.pos.entity.*;
import com.pos.entity.compositekey.ProductId;
import com.pos.exception.NameException;
import com.pos.exception.OutOfStockException;
import com.pos.exception.RecordNotFoundException;
import com.pos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerPOSServiceImpl implements POSService<Customer> {

    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    OrderRepository orderRepo;
    @Autowired
    ProductRepository productRepo;

    @Autowired
    DiscountRepository discountRepo;

    @Autowired
    ProductOrderRepository productOrderRepo;


    public Long calculateDiscount(List<Integer> totalAmountPerProduct,OrderDto orderDto ){
        Long totalTradePriceAllProducts = 0l;
        for (Integer amountWithRespectOfProductQuantity :totalAmountPerProduct) {
            totalTradePriceAllProducts += amountWithRespectOfProductQuantity;
        }
        Long sum = totalTradePriceAllProducts;

       ArrayList<Long> amountList = new ArrayList<>();
       Long finalAmount= 0l;
       if(null == orderDto.getDiscountList()) {
    	   return sum;
       }

        orderDto.getDiscountList().forEach(discount -> {
            int i=0;
            Long discountedValue=0l;
            if(amountList.size()==0) {
                discountedValue = sum - (sum * (discount.getDiscountPercentage()) / 100);
                amountList.add(discountedValue);
            }
            else{
                i= amountList.size()-1;
                discountedValue = amountList.get(i) - (amountList.get(i) * (discount.getDiscountPercentage()) / 100);
                amountList.add(discountedValue);
            }
      });
        
       finalAmount = amountList.get(amountList.size()-1);
       return finalAmount;
    }

    public void updateStockInInventory(int productQuantityInOrder, Product product1 ) {

        double totalStock= product1.getInventory().getAvailableStock();
        double totalSoldStock =product1.getInventory().getSoldStock();

        if (productQuantityInOrder < totalStock) {
            product1.getInventory().setAvailableStock(totalStock - productQuantityInOrder);
            if (totalSoldStock > 0) {
                product1.getInventory().setSoldStock((double) productQuantityInOrder + totalSoldStock);
            } else {
                product1.getInventory().setSoldStock((double) productQuantityInOrder);
            }
        } else {
            throw new OutOfStockException("Product Quantity is more than stock present in inventory");
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customerList = customerRepo.findAll();
        return customerList;
    }
    public List<String> findAllCustomerNames(){
        List<String> customerNames= customerRepo.findAllNames();
        return customerNames;
    }
    public Customer findByCustomerName(String name) throws RecordNotFoundException {
        Optional<Customer> customerEntity = customerRepo.findByName(name);
        Customer customer1 = null;
        if(customerEntity.isPresent()){
            customer1 = customerEntity.get();
        }
        else {
            throw new RecordNotFoundException("Customer not found. Try with space between name");
        }
        return customer1;
    }
    public List<String> findCustomerByProduct(Long product_id) throws RecordNotFoundException {
        List<String> customerNames = customerRepo.findCustomerNameByProduct(product_id);
        return customerNames;
    }
    @Override
    public Customer add(Customer customer) {
        if ( null != customer.getName() ) {
            if (!customer.getName().matches("^[a-zA-Z\\s]+")) {
                throw new NameException("Only alphabets and spaces are allowed for customer's name.");
            }
        }
        if(null == customer.getOrderList()) {
        	return customerRepo.save(customer);
        }
        List<Order> orderList = new ArrayList<>();
        customer.getOrderList().forEach(order1 ->{
            orderList.add(order1);
        });
        customer.getOrderList().forEach(order -> {
            order.setCustomer(customer);
            customer.addOrder(order);
        });
        return customerRepo.save(customer);
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Customer addCustomerWithOrder(CustomerDto customerDto) throws NameException,OutOfStockException {
    System.out.println("Customer order");;
        if ( null != customerDto.getName()) {
            if (!customerDto.getName().matches("^[a-zA-Z\\s]+")) {
                throw new NameException("Only alphabets and spaces are allowed for customer's name.");
            }
        }
        Optional<Customer> customerOptional = customerRepo.findByContactInfo(customerDto.getContactInfo());
        Customer customer1 = null;
        CustomerAdapterImpl customerAdapter = new CustomerAdapterImpl();
        if(customerOptional.isPresent()){
            customer1 = customerOptional.get();
        }
        else{
            customer1 = customerRepo.save(customerAdapter.convertDtoToDao(customerDto));
        }
        Customer orderPlacer=customer1;
        List<Product> productList = new ArrayList<>();
        OrderAdapterImpl orderAdapter = new OrderAdapterImpl();
        List<Integer> amountList = new ArrayList<>();

            customerDto.getOrderDtoList().forEach(orderDto -> {
                Order order = orderRepo.save(orderAdapter.convertDtoToDao(orderDto));
                orderDto.getProductList().forEach(product -> {
                    //Optional<Product> productOptional = productRepo.findById(product.getId());
                	Optional<Product> productOptional = productRepo.findByIdAndBatchNum(product.getId(),product.getBatchNum());
                    if (productOptional.isPresent()) {
                        Product product1 = productOptional.get();
                        productList.add(product1);
                        ProductOrder productOrder = new ProductOrder();
                        productOrder.setProduct(product1);
                        productOrder.setOrder(order);
                        productOrder.setQuantity(product.getQuantity());
                        int totalUnitPrice = product.getQuantity() * product1.getPrice();
//                        productOrder.setPrice(totalUnitPrice);
//                        amountList.add(totalUnitPrice);

//                        Calculating trade price with respect to quantity
                        int totalTradePrice = (int) (totalUnitPrice - totalUnitPrice*0.15);

                        productOrder.setPrice(totalUnitPrice);
                        productOrder.setTradePrice(totalTradePrice);
                        amountList.add(totalTradePrice);
                        productOrderRepo.save(productOrder);

                        int productQuantityInOrder = product.getQuantity();
                        updateStockInInventory(productQuantityInOrder,product1);
                    }
                });

                order.setTotalAmount(calculateDiscount(amountList, orderDto));

                order.setDiscounts(orderDto.getDiscountList());
                order.setStatus("Pending");
                order.setCustomer(orderPlacer);
            });
        return customer1;
       //return customerRepo.save(customer1);
    }
    @Override
    public void deleteUsingId(Long id) {
        Optional<Customer> customerEntity = customerRepo.findById(id);
        if(customerEntity.isPresent()){
            customerRepo.delete(customerEntity.get());
        }
        else {
            throw new RecordNotFoundException("Customer not found");
        }
    }

    @Override
    public void deleteUsingName(String name) {
        Optional<Customer> customerEntity = customerRepo.findByName(name);
        if(customerEntity.isPresent()){
            customerRepo.delete(customerEntity.get());
        }
        else {
            throw new RecordNotFoundException("Customer not found");
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer update(Long id, Customer customer) {

        Customer updateCustomer = null;
        Optional<Customer> customerEntity1 = customerRepo.findById(customer.getId());
        if(customerEntity1.isPresent()){
            updateCustomer = customerEntity1.get();
            updateCustomer.setName(customer.getName());
            updateCustomer.setAddress(customer.getAddress());
            updateCustomer.setContactInfo(customer.getContactInfo());
            updateCustomer.setNtn(customer.getNtn());
        }
        return updateCustomer;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer patch(Long id, Customer customer) {

        Customer patchCustomer = null;
        Optional<Customer> customerEntity1  = customerRepo.findById(id);
        if(customerEntity1.isPresent()){
            patchCustomer = customerEntity1.get();
            if (customer.getName() != null) {
                patchCustomer.setName(customer.getName());
            }
            else if (customer.getAddress()!= null) {
                patchCustomer.setAddress(customer.getAddress());
            }
            else if (customer.getContactInfo()!= null) {
                patchCustomer.setContactInfo(customer.getContactInfo());
            }
            else if (customer.getNtn()!= null) {
                patchCustomer.setNtn(customer.getNtn());
            }
        }
        return patchCustomer;
    }
}
