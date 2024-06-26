package com.siga.ecp.tn.web.rest.errors;

import com.siga.ecp.tn.exception.SoldeAlreadyExistsException;
import com.siga.ecp.tn.exception.SoldeNotFoundException;
import com.siga.ecp.tn.exception.UserNotFoundException;
import com.siga.ecp.tn.service.UsernameAlreadyUsedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.*;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.violations.ConstraintViolationProblem;
import tech.jhipster.config.JHipsterConstants;
import tech.jhipster.web.util.HeaderUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (<a href="https://tools.ietf.org/html/rfc7807">RFC7807</a>).
 */
@RestControllerAdvice
public class ExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {

    private static final String FIELD_ERRORS_KEY = "fieldErrors";
    private static final String MESSAGE_KEY = "message";
    private static final String PATH_KEY = "path";
    private static final String VIOLATIONS_KEY = "violations";
    private final Environment env;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public ExceptionTranslator(Environment env) {
        this.env = env;
    }

    @ExceptionHandler({BadRequestAlertException.class})
    public ResponseEntity<Problem> handleBadRequestAlertException(BadRequestAlertException ex, NativeWebRequest request) {
        return create(
            ex,
            request,
            HeaderUtil.createFailureAlert(applicationName, true, ex.getEntityName(), ex.getErrorKey(), ex.getMessage())
        );
    }

    @ExceptionHandler({EmailAlreadyUsedException.class})
    public ResponseEntity<Problem> handleConcurrencyFailure(ConcurrencyFailureException ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.CONFLICT).with(MESSAGE_KEY, ErrorConstants.ERR_CONCURRENCY_FAILURE).build();
        return create(ex, problem, request);
    }

    @ExceptionHandler({EmailAlreadyUsedException.class})
    public ResponseEntity<Problem> handleEmailAlreadyUsedException(
        com.siga.ecp.tn.service.EmailAlreadyUsedException ex,
        NativeWebRequest request
    ) {
        EmailAlreadyUsedException problem = new EmailAlreadyUsedException();
        return create(
            problem,
            request,
            HeaderUtil.createFailureAlert(applicationName, true, problem.getEntityName(), problem.getErrorKey(), problem.getMessage())
        );
    }

    @ExceptionHandler({InvalidPasswordException.class})
    public ResponseEntity<Problem> handleInvalidPasswordException(
        com.siga.ecp.tn.service.InvalidPasswordException ex,
        NativeWebRequest request
    ) {
        return create(new InvalidPasswordException(), request);
    }

    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @Nonnull NativeWebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldErrorVM> fieldErrors = result
            .getFieldErrors()
            .stream()
            .map(f ->
                new FieldErrorVM(
                    f.getObjectName().replaceFirst("DTO$", ""),
                    f.getField(),
                    StringUtils.isNotBlank(f.getDefaultMessage()) ? f.getDefaultMessage() : f.getCode()
                )
            )
            .collect(Collectors.toList());

        Problem problem = Problem
            .builder()
            .withType(ErrorConstants.CONSTRAINT_VIOLATION_TYPE)
            .withTitle("Method argument not valid")
            .withStatus(defaultConstraintViolationStatus())
            .with(MESSAGE_KEY, ErrorConstants.ERR_VALIDATION)
            .with(FIELD_ERRORS_KEY, fieldErrors)
            .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler({SoldeAlreadyExistsException.class})
    public ResponseEntity<Problem> handleSoldeAlreadyExistsException(
        SoldeAlreadyExistsException ex,
        NativeWebRequest request) {

        Problem problem = Problem.builder().withStatus(Status.BAD_REQUEST).with(MESSAGE_KEY, ex.getMessage()).build();
        return create(ex, problem, request);
    }

    @ExceptionHandler({SoldeNotFoundException.class})
    public ResponseEntity<Problem> handleSoldeNotFoundException(
        SoldeNotFoundException ex,
        NativeWebRequest request
    ) {
        SoldeNotFoundException problem = new SoldeNotFoundException();
        return create(
            problem,
            request,
            HeaderUtil.createFailureAlert(applicationName, true, problem.getEntityName(), problem.getErrorKey(), problem.getMessage())
        );
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Problem> handleUserNotFoundException(
        com.siga.ecp.tn.exception.UserNotFoundException ex,
        NativeWebRequest request
    ) {
        UserNotFoundException problem = new UserNotFoundException();
        return create(
            problem,
            request,
            HeaderUtil.createFailureAlert(applicationName, true, problem.getEntityName(), problem.getErrorKey(), problem.getMessage())
        );
    }

    @ExceptionHandler({UsernameAlreadyUsedException.class})
    public ResponseEntity<Problem> handleUsernameAlreadyUsedException(
        com.siga.ecp.tn.service.UsernameAlreadyUsedException ex,
        NativeWebRequest request
    ) {
        LoginAlreadyUsedException problem = new LoginAlreadyUsedException();
        return create(
            problem,
            request,
            HeaderUtil.createFailureAlert(applicationName, true, problem.getEntityName(), problem.getErrorKey(), problem.getMessage())
        );
    }

    @Override
    public ProblemBuilder prepare(@Nonnull final Throwable throwable, @Nonnull final StatusType status, @Nonnull final URI type) {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());

        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            if (throwable instanceof HttpMessageConversionException) {
                return Problem
                    .builder()
                    .withType(type)
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail("Unable to convert http message")
                    .withCause(
                        Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null)
                    );
            }
            if (throwable instanceof DataAccessException) {
                return Problem
                    .builder()
                    .withType(type)
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail("Failure during data access")
                    .withCause(
                        Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null)
                    );
            }
            if (containsPackageName(throwable.getMessage())) {
                return Problem
                    .builder()
                    .withType(type)
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail("Unexpected runtime exception")
                    .withCause(
                        Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null)
                    );
            }
        }

        return Problem
            .builder()
            .withType(type)
            .withTitle(status.getReasonPhrase())
            .withStatus(status)
            .withDetail(throwable.getMessage())
            .withCause(
                Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null)
            );
    }

    private boolean containsPackageName(String message) {
        // This list is for sure not complete
        return StringUtils.containsAny(message, "org.", "java.", "net.", "javax.", "com.", "io.", "de.", "com.siga.ecp.tn");
    }

    /**
     * Post-process the Problem payload to add the message key for the front-end if needed.
     */
    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, @Nonnull NativeWebRequest request) {
        if (entity == null) {
            return null;
        }
        Problem problem = entity.getBody();
        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }

        HttpServletRequest nativeRequest = request.getNativeRequest(HttpServletRequest.class);
        String requestUri = nativeRequest != null ? nativeRequest.getRequestURI() : StringUtils.EMPTY;
        ProblemBuilder builder = Problem
            .builder()
            .withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? ErrorConstants.DEFAULT_TYPE : problem.getType())
            .withStatus(problem.getStatus())
            .withTitle(problem.getTitle())
            .with(PATH_KEY, requestUri);

        if (problem instanceof ConstraintViolationProblem) {
            builder
                .with(VIOLATIONS_KEY, ((ConstraintViolationProblem) problem).getViolations())
                .with(MESSAGE_KEY, ErrorConstants.ERR_VALIDATION);
        } else {
            builder.withCause(((DefaultProblem) problem).getCause()).withDetail(problem.getDetail()).withInstance(problem.getInstance());
            problem.getParameters().forEach(builder::with);
            if (!problem.getParameters().containsKey(MESSAGE_KEY) && problem.getStatus() != null) {
                builder.with(MESSAGE_KEY, "error.http." + problem.getStatus().getStatusCode());
            }
        }
        return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }
}
