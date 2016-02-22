package com.scarabsoft.jrest.converter.exception;

import com.scarabsoft.jrest.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class StringExceptionConverter implements ExceptionConverter<RuntimeException> {

    @Override
    public RuntimeException convert(InputStream stream) throws IOException {
        final String message = IOUtils.streamToString(stream);
        return new RuntimeException(message);
    }

    @Override
    public Collection<RuntimeException> convertCollection(InputStream inputStream, Class<? extends Collection> collectionClazz) throws IOException {
        throw new RuntimeException("not supported yet.");
    }
}
