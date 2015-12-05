package com.scarabsoft.jrest;

final class BodyEntity {

    private final byte[] bytes;
    private final String mimeType;

    BodyEntity(byte[] bytes, String mimeType) {
        this.bytes = bytes;
        this.mimeType = mimeType;
    }

    byte[] getBytes() {
        return bytes;
    }

    String getMimeType() {
        return mimeType;
    }

}
