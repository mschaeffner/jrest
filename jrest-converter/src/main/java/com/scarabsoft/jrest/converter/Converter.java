package com.scarabsoft.jrest.converter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public interface Converter<T> {

    T convert(InputStream stream) throws IOException;

     Collection<T> convertCollection(InputStream inputStream, Class<? extends Collection> collectionClazz) throws IOException;

}
