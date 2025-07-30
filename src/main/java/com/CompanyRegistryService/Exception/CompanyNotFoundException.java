package com.CompanyRegistryService.Exception;

public class CompanyNotFoundException extends RuntimeException {

    public CompanyNotFoundException(String message) {
        super(message);
    }

    public CompanyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
