package com.scarabsoft.jrest.util;

import java.io.*;
import java.util.stream.Collectors;

public class IOUtils {

    private IOUtils() {
        throw new RuntimeException("use static methods");
    }

    public static String streamToString(InputStream in) {
        return new BufferedReader(new InputStreamReader(in))
                .lines()
                .collect(Collectors.joining());
    }

    public static byte[] convert(InputStream is) throws IOException {
        int len;
        int size = 1024;
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[size];
        while ((len = is.read(buffer, 0, size)) != -1) {
            bos.write(buffer, 0, len);
        }
        return bos.toByteArray();
    }
}