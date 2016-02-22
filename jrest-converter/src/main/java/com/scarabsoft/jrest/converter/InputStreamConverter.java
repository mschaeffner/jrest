package com.scarabsoft.jrest.converter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class InputStreamConverter implements Converter<InputStream> {
    @Override
    public InputStream convert(InputStream stream) throws IOException {
        return stream;
    }

    @Override
    public Collection<InputStream> convertCollection(InputStream inputStream, Class<? extends Collection> collectionClazz) throws IOException {
        throw new RuntimeException("not supported yet.");
    }
}
