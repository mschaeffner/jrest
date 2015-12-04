package com.scarabsoft.jrest.converter.exception;

import com.scarabsoft.jrest.converter.Converter;

public interface ExceptionConverter<T extends RuntimeException> extends Converter<T> {

    interface ExceptionConverterFactory {

        ExceptionConverter<?> get();

    }

}
