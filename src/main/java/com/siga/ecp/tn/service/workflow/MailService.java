package com.siga.ecp.tn.service.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailService implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.debug("Start event triggered. Process instance ID: " + delegateExecution.getProcessInstanceId());
    }
}
