package com.scibee.freya.converter;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Collection;

public class GsonConverter<T> implements Converter<T> {

    private final TypeAdapter<T> typeAdapter;

    Type type;

    public GsonConverter(TypeAdapter<T> typeAdapter, Type type) {
        this.typeAdapter = typeAdapter;
        this.type = type;
    }

    @Override
    public T convert(InputStream inputStream) throws IOException {
        if (typeAdapter == null) {
            return null;
        }
        return typeAdapter.read(new JsonReader(new InputStreamReader(inputStream)));
    }

    @Override
    public Collection<T> convertCollection(InputStream inputStream, Class<? extends Collection> collectionClazz) throws IOException {
        JsonParser parser = new JsonParser();
        JsonElement parse = parser.parse(new JsonReader(new InputStreamReader(inputStream)));
        Collection<T> data = null;
        try {
            data = collectionClazz.newInstance();
            JsonArray rootArray = parse.getAsJsonArray();
            for (JsonElement json : rootArray) {
                try {
                    data.add(new Gson().fromJson(json, type));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public BodyConverter getBodyConverter() {
        return new BodyConverter() {
            @Override
            public byte[] toBody(Object obj) throws UnsupportedEncodingException {
                return new Gson().toJson(obj).getBytes("UTF-8");
            }

            @Override
            public String getMimetype() {
                return "application/json";
            }
        };
    }
}
