package com.siga.ecp.tn.service;

import com.siga.ecp.tn.security.SecurityUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkflowService {

    private static final Logger log = LoggerFactory.getLogger(WorkflowService.class);

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ProcessInstance startProcessById(String processName, HashMap<String, Object> variables) {
        if (variables.get("initiateur") == null) {
            variables.put("initiator", SecurityUtils.getCurrentUserLogin().get()); // replace ny current matricule
            variables.put("startProcessByKey", SecurityUtils.getCurrentUserLogin().get()); // replace ny current matricule
            variables.put("startedBy", SecurityUtils.getCurrentUserLogin().get()); // replace ny current matricule
            variables.put("initiateur", SecurityUtils.getCurrentUserLogin().get());
        }
        //identityService.setAuthenticatedUserId(SecurityUtils.getCurrentUserMatricule().get().toString());

        log.info("process id {} for {} ", this.getIdProcessByName(processName), processName);
        return runtimeService.startProcessInstanceById(this.getIdProcessByName(processName), variables);
        // throw new BadRequestAlertException("Processus "+processName +" Non trouvé ","","Workflow");
    }

    public String getIdProcessByName(String Name) {
        Optional<ProcessDefinitionQuery> def = Optional.of(
            repositoryService.createProcessDefinitionQuery().active().latestVersion().startableInTasklist().processDefinitionName(Name)
        );
        if (def.isPresent() && def.get().singleResult() != null) {
            log.debug("def found");
            return def.get().singleResult().getId();
        }
        log.debug("def not found");
        return null;
    }
}
