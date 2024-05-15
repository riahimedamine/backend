package com.siga.ecp.tn.service.dto;

public class DemandeCongeError {
    private Boolean hasOne;
    private Boolean solde;

    public DemandeCongeError() {
        // Empty constructor needed for Jackson.
    }

    public DemandeCongeError(Boolean hasOne, Boolean solde) {
        this.hasOne = hasOne;
        this.solde = solde;
    }

    public Boolean getHasOne() {
        return hasOne;
    }

    public void setHasOne(Boolean hasOne) {
        this.hasOne = hasOne;
    }

    public Boolean getSolde() {
        return solde;
    }

    public void setSolde(Boolean solde) {
        this.solde = solde;
    }
}
