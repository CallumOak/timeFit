package com.callumezmoney.timefit.mapper;

public interface WebMapper<T, S> {
    T dtoToEntity(S dto);
    S entityToDto(T entity);
    String toURI(T object);
    T fromURI(String uri);
}
