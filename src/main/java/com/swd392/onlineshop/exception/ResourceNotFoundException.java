package com.swd392.onlineshop.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, Object id) {
        super(resource + " voi id = " + id + " khong ton tai");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
