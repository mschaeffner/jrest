package com.scarabsoft.jrest.converter;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamConverter implements Converter<InputStream> {
    @Override
    public InputStream convert(InputStream stream) throws IOException {
        return stream;
    }
}
