package com.scarabsoft.jrest.util;

import java.io.*;

public class IOUtils {

    private IOUtils() {
        throw new RuntimeException("use static methods");
    }

    public static String streamToString(InputStream in) {
        final StringBuilder builder = new StringBuilder();
        String line;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
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