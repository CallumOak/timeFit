package com.callumezmoney.timefit.mapper;

import java.text.ParseException;

public interface WebMapper<T, S> {
    T dtoToEntity(S dto) throws ParseException;
    S entityToDto(T entity);
    T fromURI(String uri);
}
