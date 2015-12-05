package com.scarabsoft.jrest.converter;

import com.scarabsoft.jrest.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayConverter implements Converter<byte[]> {

    @Override
    public byte[] convert(InputStream is) throws IOException {
        return IOUtils.convert(is);
    }

}
