package com.scarabsoft.jrest.converter;

import java.io.IOException;
import java.io.InputStream;

public class VoidConverter implements Converter<Void> {
    @Override
    public Void convert(InputStream stream) throws IOException {
        return null;
    }
}
