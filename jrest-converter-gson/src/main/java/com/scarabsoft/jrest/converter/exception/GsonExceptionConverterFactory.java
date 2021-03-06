package com.scarabsoft.jrest.converter.exception;

import com.google.gson.Gson;

public abstract class GsonExceptionConverterFactory implements ExceptionConverter.ExceptionConverterFactory {

    protected abstract Class<? extends RuntimeException> getReturnType();

    @Override
    public ExceptionConverter<?> get() {
        return new GsonExceptionConverter<>(new Gson().getAdapter(getReturnType()));
    }

}
