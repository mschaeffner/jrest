package com.scarabsoft.jrest.converter.exception;

import com.scarabsoft.jrest.util.IOUtil;

import java.io.IOException;
import java.io.InputStream;

public class StringExceptionConverter implements ExceptionConverter<RuntimeException> {

    @Override
    public RuntimeException convert(InputStream stream) throws IOException {
        final String message = IOUtil.streamToString(stream);
        return new RuntimeException(message);
    }
}
