package com.siga.ecp.tn.exception;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
        super("Cannot find User!");
    }
}
