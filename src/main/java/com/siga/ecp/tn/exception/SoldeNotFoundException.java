package com.siga.ecp.tn.exception;

import com.siga.ecp.tn.web.rest.errors.BadRequestAlertException;
import com.siga.ecp.tn.web.rest.errors.ErrorConstants;

public class SoldeNotFoundException extends BadRequestAlertException {
    public SoldeNotFoundException() {
        super(ErrorConstants.SOLDE_NOT_FOUND_TYPE, "Solde not found!", "soldeManagement", "soldenotexists");
    }
}
