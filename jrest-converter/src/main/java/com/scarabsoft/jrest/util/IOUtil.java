package com.scarabsoft.jrest.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class IOUtil {

    private IOUtil() {
        throw new RuntimeException("use static methods");
    }

    public static String streamToString(InputStream in) {
        return new BufferedReader(new InputStreamReader(in))
                .lines()
                .collect(Collectors.joining());
    }
}