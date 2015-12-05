package com.scarabsoft.jrest.converter;

import com.scarabsoft.jrest.converter.body.BodyConverter;

import java.lang.reflect.Type;

public interface ConverterFactory {

    Converter<?> getConverter(Type type);

    BodyConverter getBodyConverter();

}
