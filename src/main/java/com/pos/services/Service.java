package com.pos.services;

import java.util.List;

public interface Service<T>{

    public List<T> findAll();

    public T add(T t);

    public void deleteUsingId(Long id);

    public void deleteUsingName(String name);

    public T update(Long id,T t);

    public T patch(Long id,T dto);
}
