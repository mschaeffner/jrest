package com.scarabsoft.jrest;

final class ParamEntity {

    private final String name;
    private final Object value;
    private final String filename;
    private final String contentType;

    ParamEntity(String name, Object value, String filename, String contentType) {
        this.name = name;
        this.value = value;
        this.filename = filename;
        this.contentType = contentType;
    }

    String getName() {
        return name;
    }

    Object getValue() {
        return value;
    }

    String getFilename() {
        return filename;
    }

    public String getContentType() {return contentType;}
}
