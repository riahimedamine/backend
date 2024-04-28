package com.siga.ecp.tn.service.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CamundaMailService implements ExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(CamundaMailService.class);

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        log.debug("Start event triggered. Process instance ID: " + execution.getProcessInstanceId());
    }
}
