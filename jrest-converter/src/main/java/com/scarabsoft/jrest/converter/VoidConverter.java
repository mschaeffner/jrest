package com.scarabsoft.jrest.converter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class VoidConverter implements Converter<Void> {

    @Override
    public Void convert(InputStream stream) throws IOException {
        return null;
    }

    @Override
    public Collection<Void> convertCollection(InputStream inputStream, Class<? extends Collection> collectionClazz) throws IOException {
        throw new RuntimeException("not supported yet.");
    }
}
