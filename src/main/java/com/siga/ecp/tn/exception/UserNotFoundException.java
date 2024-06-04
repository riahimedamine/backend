package com.siga.ecp.tn.exception;

import com.siga.ecp.tn.web.rest.errors.BadRequestAlertException;
import com.siga.ecp.tn.web.rest.errors.ErrorConstants;

public class UserNotFoundException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
        super(ErrorConstants.USER_NOT_FOUND_TYPE, "Login name already used!", "userManagement", "userdoesnotexists");

    }
}
