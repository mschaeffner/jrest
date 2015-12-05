package com.scarabsoft.jrest.converter;

import java.io.InputStream;

public class LazyConverter implements Converter<Void> {

    @Override
    public Void convert(InputStream stream) {
        return null;
    }

}
