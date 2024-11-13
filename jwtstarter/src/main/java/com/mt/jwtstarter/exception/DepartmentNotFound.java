package com.mt.jwtstarter.exception;

public class DepartmentNotFound extends RuntimeException{
    public DepartmentNotFound(String message) {
        super(message);
    }
}
