package com.scarabsoft.jrest.converter.body;

import java.io.UnsupportedEncodingException;

public interface BodyConverter {

    byte[] toBody(Object obj) throws UnsupportedEncodingException;

    String getMimetype();
}
