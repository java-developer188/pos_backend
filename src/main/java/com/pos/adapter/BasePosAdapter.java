package com.pos.adapter;

public interface BasePosAdapter<T,U> {

    public U convertDtoToDao(T t);
    public T convertDaoToDto(U u);
}
