package com.scarabsoft.jrest.converter;

import com.scarabsoft.jrest.converter.body.BodyConverter;
import com.scarabsoft.jrest.converter.body.StringBodyConverter;

import java.lang.reflect.Type;

public class StringConverterFactory implements ConverterFactory {

    @Override
    public Converter<?> getConverter(Type type) {
        return new StringConverter();
    }

    @Override
    public BodyConverter getBodyConverter() {
        return new StringBodyConverter();
    }

}
