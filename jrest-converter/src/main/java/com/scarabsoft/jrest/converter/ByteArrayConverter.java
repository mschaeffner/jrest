package com.scarabsoft.jrest.converter;

import com.scarabsoft.jrest.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class ByteArrayConverter implements Converter<byte[]> {

    @Override
    public byte[] convert(InputStream is) throws IOException {
        return IOUtils.convert(is);
    }

    @Override
    public Collection<byte[]> convertCollection(InputStream inputStream, Class<? extends Collection> collectionClazz) throws IOException {
        throw new RuntimeException("not supported yet.");
    }

}
