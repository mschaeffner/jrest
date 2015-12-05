package com.scarabsoft.jrest.converter;

import com.google.gson.Gson;
import com.scarabsoft.jrest.converter.body.BodyConverter;

import java.io.UnsupportedEncodingException;

public class GsonBodyConverter implements BodyConverter {

    @Override
    public byte[] toBody(Object obj) throws UnsupportedEncodingException {
        return new Gson().toJson(obj).getBytes("UTF-8");
    }

    @Override
    public String getMimetype() {
        return "application/json";
    }
};

