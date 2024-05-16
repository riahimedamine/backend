package com.siga.ecp.tn.service.workflow;

import com.siga.ecp.tn.domain.DemandeConge;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Implementation class for managing workflows
 */
@Component("camundaMailService")
public class MailService implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.debug("Start event triggered. Process instance ID: {}", delegateExecution.getProcessInstanceId());

        // TODO: Implement mail sending logic
        DemandeConge demandeConge = (DemandeConge) delegateExecution.getVariable("demande");
        log.debug("************** demande", demandeConge);
    }
}
