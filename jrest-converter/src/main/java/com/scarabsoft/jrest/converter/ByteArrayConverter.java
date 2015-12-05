package com.scarabsoft.jrest.converter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteArrayConverter implements Converter<byte[]> {

    @Override
    public byte[] convert(InputStream is) throws IOException {
        int len;
        int size = 1024;
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[size];
        while ((len = is.read(buffer, 0, size)) != -1) {
            bos.write(buffer, 0, len);
        }
        return bos.toByteArray();
    }

}
