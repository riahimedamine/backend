package com.siga.ecp.tn.service.workflow;

import com.siga.ecp.tn.security.SecurityUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

/**
 * Service class for managing workflows
 */
@Service
public class WorkflowService {

    private static final Logger log = LoggerFactory.getLogger(WorkflowService.class);

    private final RepositoryService repositoryService;

    private final RuntimeService runtimeService;

    public WorkflowService(RepositoryService repositoryService, RuntimeService runtimeService) {
        this.repositoryService = repositoryService;
        this.runtimeService = runtimeService;
    }

    /**
     * Delete a process instance by id
     *
     * @param processInstanceId the id of the process instance to delete
     * @param deleteReason      the reason for deleting the process instance
     */
    public void deleteProcessInstance(String processInstanceId, String deleteReason) {
        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
    }

    /**
     * Start a process by name
     *
     * @param processName the name of the process to start
     * @param variables   the variables to pass to the process
     * @return the process instance
     */
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

    /**
     * Get the process id by name
     *
     * @param name the name of the process
     * @return the id of the process
     */
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
