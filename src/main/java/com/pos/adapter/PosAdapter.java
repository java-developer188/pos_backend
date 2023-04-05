package com.pos.adapter;

public interface PosAdapter<T,U> {

    public U convertDtoToDao(T t);
    public T convertDaoToDto(U u);
}
