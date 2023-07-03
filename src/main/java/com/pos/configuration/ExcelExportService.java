package com.pos.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pos.response.InvoiceResponse;
import com.pos.response.Classes.ProductDescription;

public class ExcelExportService {
	public static void exportInvoice(InvoiceResponse invoiceResponse, Long order_id) {
		
		try (FileInputStream templateFile = new FileInputStream("Template.xlsx");
			Workbook workbook = new XSSFWorkbook(templateFile)) {
			// Access the first sheet of the workbook
            Sheet sheet = workbook.getSheetAt(0);
            
            //playing with date
            String dataAndTime[] = invoiceResponse.getOrder_Date().toString().split("T");


            // Define the data to be populated in the template
            Map<String, String> data = new HashMap<>();
            data.put("DATE", "DATE: "+ dataAndTime[0]);
            data.put("INVOICE_NUM","INVOICE # : "+invoiceResponse.getInvoice_id().toString());
            data.put("CustomerName","Customer Name : "+invoiceResponse.getCustomer_name().toUpperCase());
            data.put("CustomerContact","Cell # : "+invoiceResponse.getCustomer_contactInfo().toString());
            // Add more key-value pairs as needed

         // Replace the placeholders with the actual data
            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.STRING) {
                        String cellValue = cell.getStringCellValue();
                        if (cellValue.startsWith("${") && cellValue.endsWith("}")) {
                            String placeholder = cellValue.substring(2, cellValue.length() - 1);
                            String value = data.get(placeholder);
                            if (value != null) {
                                cell.setCellValue(value);
                            }
                        }
                    }
                }
            }
            
            int prodRowNum = 13;
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            int count = 1;
			int totalProductQuantity=0;
            for(ProductDescription desc: invoiceResponse.getProductDesc()) {
            	Row row = sheet.createRow(prodRowNum - 1);
            	Cell cell=row.createCell(0);
            	cell.setCellValue(count);
            	cell.setCellStyle(cellStyle);
                cell = row.createCell(1);
                cell.setCellValue(desc.getName());
                cell.setCellStyle(cellStyle);
                cell = row.createCell(2);
                cell.setCellValue(desc.getQuantity());
				totalProductQuantity+= desc.getQuantity();
                cell.setCellStyle(cellStyle);
                cell = row.createCell(3);
                cell.setCellValue(desc.getUnitPrice());
                cell.setCellStyle(cellStyle);
				cell = row.createCell(4);
				double productPrice = desc.getUnitPrice();
				double tradePrice = productPrice-(productPrice*0.15);
				cell.setCellValue(tradePrice);
				cell.setCellStyle(cellStyle);
				cell = row.createCell(5);
                cell.setCellValue(desc.getTotalPrice());
				//cell.setCellValue(desc.getQuantity()*tradePrice);
                cell.setCellStyle(cellStyle);

//                cell = row.createCell(5);
//                cellStyle.setBorderBottom(BorderStyle.THIN);
//                cellStyle.setBorderRight(BorderStyle.THIN);
//                cell.setCellStyle(cellStyle);
                setCellHeight(sheet, prodRowNum-1, 28);
                count++; //to get total number of products as count in excel's first row
                prodRowNum++;
            }
            	
	            cellStyle = workbook.createCellStyle();
	            cellStyle.setBorderRight(BorderStyle.THIN);
            
            	Row row = sheet.createRow(prodRowNum);
            	Cell cell = row.createCell(3);
            	cell.setCellStyle(cellStyle);
            	cell = row.createCell(4);
            	cell.setCellStyle(cellStyle);


//			    row=sheet.createRow(prodRowNum-1);
//			    cell=row.createCell(1);
//			    cell.setCellValue("TOTAL QUANTITY");
//			    cellFontColor(workbook, cell);
//			    cell = row.createCell(2);
//			    cell.setCellValue(totalQuantity);
//			    cellFontColor(workbook, cell);
//			    setCellHeight(sheet, prodRowNum-1, 30);
//			    prodRowNum++;

            	row=sheet.createRow(prodRowNum-1);
            	cell=row.createCell(4);
            	cell.setCellValue("SUB TOTAL");
            	cellFontColor(workbook, cell);
            	cell = row.createCell(5);
            	cell.setCellValue(invoiceResponse.getOrder_total_amount());
            	cellFontColor(workbook, cell);
            	setCellHeight(sheet, prodRowNum-1, 30);
            	prodRowNum++;
            	row=sheet.createRow(prodRowNum-1);
            	cell=row.createCell(4);
			    long totalDiscountPercentage = 0l;
			    for (int i = 0; i < invoiceResponse.getDiscountList().size(); i++) {
				    totalDiscountPercentage+=invoiceResponse.getDiscountList().get(i);
			    }
            	cell.setCellValue("DISCOUNT "+totalDiscountPercentage+"%");
            	cellFontColor(workbook, cell);
            	cell = row.createCell(5);
            	cell.setCellValue(invoiceResponse.getDiscounted_amount());
            	cellFontColor(workbook, cell);
            	setCellHeight(sheet, prodRowNum-1, 30);
            	prodRowNum++;
            	row=sheet.createRow(prodRowNum-1);
            	cell=row.createCell(4);
            	cell.setCellValue("TOTAL");
            	cellFontColorAndBorder(workbook, cell);
            	cell = row.createCell(5);
            	cell.setCellValue(invoiceResponse.getOrder_deducted_amount());
            	setCellHeight(sheet, prodRowNum-1, 30);
            	cellFontColorAndBorder(workbook, cell);

            	cellStyle = workbook.createCellStyle();
	            cellStyle.setBorderRight(BorderStyle.THIN);
	            
	            int i =0;
            	for(i=prodRowNum; i<prodRowNum+2;i++) {
            		row = sheet.createRow(i);
                	cell = row.createCell(3);
                	cell.setCellStyle(cellStyle);
                	cell = row.createCell(4);
                	cell.setCellStyle(cellStyle);
            	}
            	prodRowNum = i;
            	Row topRow = sheet.getRow(0);
            	Row targetRow = sheet.createRow(prodRowNum);
            	copyRow(topRow, targetRow);
            	
//            	for (Row row1 : sheet) {
//                    for (Cell cell1 : row) {
//                    	int v = cell1.getColumnIndex();
//                    	if(v == 4) {
//                    		cellStyle.setBorderRight(BorderStyle.THIN);
//                        	cell1.setCellStyle(cellStyle);
//                    	}
//                        
//                    }
//                }
            	
            
            String outputFileName = "Invoice_"+order_id+".xlsx";
            File existingFile = new File("outputFileName");
            if (existingFile.exists()) {
                // Delete the existing file
                boolean deleted = existingFile.delete();
                if (!deleted) {
                    System.out.println("Error deleting existing file.");
                }
            }
            try (FileOutputStream outputFile = new FileOutputStream(outputFileName)) {
            	
                workbook.write(outputFile);
            }

            System.out.println("Excel exported successfully.");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
//		try (Workbook workbook = new XSSFWorkbook()) {
//            Sheet sheet = workbook.createSheet("Data");
//
//            int rowCount = 0;
//            for (String data : dataList) {
//                Row row = sheet.createRow(rowCount++);
//                Cell cell = row.createCell(0);
//                cell.setCellValue(data);
//            }
//
//            try (FileOutputStream outputStream = new FileOutputStream("output.xlsx")) {
//                workbook.write(outputStream);
//            }
//
//            System.out.println("Data exported to Excel successfully.");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
	}
	
	  private static void setCellHeight(Sheet sheet, int rowIndex, int heightInPoints) {
	        Row row = sheet.getRow(rowIndex);
	        if (row != null) {
	            row.setHeightInPoints(heightInPoints);
	        }
	    }
	  
	  private static void cellFontColor(Workbook workbook, Cell cell) {
		  Font font = workbook.createFont();
          font.setColor(IndexedColors.BLACK.getIndex());
          font.setBold(true);
          

          // Create a cell style with the custom font
          CellStyle cellStyle = workbook.createCellStyle();
          cellStyle.setBorderBottom(BorderStyle.DASHED);
          cellStyle.setBorderRight(BorderStyle.THIN);
          
          cellStyle.setFont(font);
          //cellStyle.setBorderBottom(BorderStyle.THIN);
          
          
          cell.setCellStyle(cellStyle);
	  }
	  
	  private static void cellFontColorAndBorder(Workbook workbook, Cell cell) {
		  Font font = workbook.createFont();
          font.setColor(IndexedColors.BLACK.getIndex());
          font.setBold(true);
          

          // Create a cell style with the custom font
          CellStyle cellStyle = workbook.createCellStyle();
          cellStyle.setBorderBottom(BorderStyle.DOUBLE);
          cellStyle.setBorderRight(BorderStyle.THIN);
          cellStyle.setBorderTop(BorderStyle.DOUBLE);
          //cellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
          cellStyle.setFont(font);
          //cellStyle.setBorderBottom(BorderStyle.THIN);
          
          
          cell.setCellStyle(cellStyle);
	  }
	  
	  private static void copyRow(Row sourceRow, Row targetRow) {
	        targetRow.setHeight(sourceRow.getHeight());

	        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
	            Cell sourceCell = sourceRow.getCell(i);
	            Cell targetCell = targetRow.createCell(i);

	            if (sourceCell != null) {
	                targetCell.setCellStyle(sourceCell.getCellStyle());
	                targetCell.setCellType(sourceCell.getCellType());

	                switch (sourceCell.getCellType()) {
	                    case STRING:
	                        targetCell.setCellValue(sourceCell.getStringCellValue());
	                        break;
	                    case NUMERIC:
	                        targetCell.setCellValue(sourceCell.getNumericCellValue());
	                        break;
	                    case BOOLEAN:
	                        targetCell.setCellValue(sourceCell.getBooleanCellValue());
	                        break;
	                    case FORMULA:
	                        targetCell.setCellValue(sourceCell.getCellFormula());
	                        break;
	                    default:
	                        // Handle other cell types if needed
	                        break;
	                }
	            }
	        }
	  }
	  

}
