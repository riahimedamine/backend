package com.siga.ecp.tn.web.rest.errors;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String PROBLEM_BASE_URL = "";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    public static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");
    public static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");
    public static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");
    public static final URI USER_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/user-not-found");
    public static final URI SOLDE_ALREADY_EXISTS_TYPE = URI.create(PROBLEM_BASE_URL + "/solde-already-exists");
    public static final URI SOLDE_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/solde-not-found");

    private ErrorConstants() {}
}
