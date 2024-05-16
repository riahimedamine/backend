package com.siga.ecp.tn.service.workflow;

import com.siga.ecp.tn.domain.DemandeConge;
import com.siga.ecp.tn.repository.DemandeCongeRepository;
import com.siga.ecp.tn.service.DemandeCongeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation class for managing workflows
 */
@Component
public class ValidateService implements JavaDelegate {

    private final DemandeCongeRepository demandeCongeRepository;

    private final DemandeCongeService demandeCongeService;

    public ValidateService(DemandeCongeRepository demandeCongeRepository, DemandeCongeService demandeCongeService) {
        this.demandeCongeRepository = demandeCongeRepository;
        this.demandeCongeService = demandeCongeService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void execute(DelegateExecution execution) throws Exception {
        DemandeConge demandeConge = (DemandeConge) execution.getVariable("demande");
        demandeConge = demandeCongeRepository.findById(demandeConge.getId()).get();

        demandeCongeService.validateDemandeConge(demandeConge.getId(), 1);

    }
}
