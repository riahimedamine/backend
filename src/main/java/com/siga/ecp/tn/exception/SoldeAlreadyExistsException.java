package com.siga.ecp.tn.exception;

/**
 * Exception thrown when the solde already exist.
 */
public class SoldeAlreadyExistsException extends RuntimeException {

    public SoldeAlreadyExistsException() {
        super("Login name already used!");
    }
}
