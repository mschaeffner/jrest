package com.scarabsoft.jrest.converter.body;

import java.io.UnsupportedEncodingException;

public class StringBodyConverter implements BodyConverter {

    @Override
    public byte[] toBody(Object obj) throws UnsupportedEncodingException {
        return obj.toString().getBytes("UTF-8");
    }

    @Override
    public String getMimetype() {
        return "text/plain";
    }
};
