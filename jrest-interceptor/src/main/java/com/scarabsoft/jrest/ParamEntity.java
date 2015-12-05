package com.scarabsoft.jrest;

final class ParamEntity {

    private final String name;
    private final Object value;
    private final String filename;

    ParamEntity(String name, Object value, String filename) {
        this.name = name;
        this.value = value;
        this.filename = filename;
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
}
