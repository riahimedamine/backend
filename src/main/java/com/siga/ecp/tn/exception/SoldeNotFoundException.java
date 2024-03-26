package com.siga.ecp.tn.exception;

public class SoldeNotFoundException extends RuntimeException {
    public SoldeNotFoundException() {
        super("Solde not found");
    }
}
