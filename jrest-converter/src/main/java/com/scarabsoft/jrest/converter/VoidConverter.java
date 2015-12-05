package com.scarabsoft.jrest.converter;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class VoidConverter implements Converter<Void> {
    @Override
    public Void convert(InputStream stream) throws IOException {
        return null;
    }

    @Override
    public BodyConverter getBodyConverter() {
        return new BodyConverter() {
            @Override
            public byte[] toBody(Object obj) throws UnsupportedEncodingException {
                return new byte[0];
            }

            @Override
            public String getMimetype() {
                return null;
            }
        };
    }
}
