package com.scarabsoft.jrest.converter;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Collection;

public interface Converter<T> {

    T convert(InputStream stream) throws IOException;

    BodyConverter getBodyConverter();

    default Collection<T> convertCollection(InputStream inputStream,Class<? extends Collection> collectionClazz) throws IOException {
        throw new RuntimeException("not implemented yet");
    }

    interface BodyConverter {
        byte[] toBody(Object obj) throws UnsupportedEncodingException;

        String getMimetype();
    }

    interface ConverterFactory {
        Converter<?> get(Type type);
    }
}
