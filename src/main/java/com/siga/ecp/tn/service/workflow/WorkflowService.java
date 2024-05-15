package com.siga.ecp.tn.service.workflow;

import com.siga.ecp.tn.security.SecurityUtils;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

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
    public ProcessInstance startProcessById(String processName, Map<String, Object> variables) {
        if (variables.get("initiateur") == null) {
            variables.put("initiator", SecurityUtils.getCurrentUserLogin().get());
            variables.put("startProcessByKey", SecurityUtils.getCurrentUserLogin().get());
            variables.put("startedBy", SecurityUtils.getCurrentUserLogin().get());
            variables.put("initiateur", SecurityUtils.getCurrentUserLogin().get());
        }

        log.info("process id {} for {} ", this.getIdProcessByName(processName), processName);
        return runtimeService.startProcessInstanceById(this.getIdProcessByName(processName), variables);
    }

    public String getIdProcessByName(String name) {
        Optional<ProcessDefinitionQuery> def = Optional.ofNullable(
            repositoryService.createProcessDefinitionQuery()
                .active()
                .latestVersion()
                .startableInTasklist()
                .processDefinitionName(name)
        );

        if (def.isPresent() && def.get().singleResult() != null) {
            log.debug("def found");
            return def.get().singleResult().getId();
        }
        log.debug("def not found");
        return null;
    }
}
