package com.siga.ecp.tn.service.workflow;

import com.siga.ecp.tn.domain.TreatTask;
import com.siga.ecp.tn.security.SecurityUtils;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    public void completeTask(TreatTask treatTask) {
        Task currentTask = taskService.createTaskQuery().taskId(treatTask.getTaskId()).singleResult();

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("decision", treatTask.getValue());
        variables.put("comment", treatTask.getComment());
        variables.put("startProcessByKey", treatTask.getInitiator());
        variables.put("startedBy", treatTask.getInitiator());
        variables.put("dateComment", LocalDateTime.now());
        variables.put("treater", SecurityUtils.getCurrentUserLogin().get());

        currentTask.setDescription(treatTask.getComment());

        taskService.saveTask(currentTask);

        taskService.complete(treatTask.getTaskId(), variables);
    }

    public List<Task> getTasksByAssignee(String user) {
        return taskService.createTaskQuery().taskAssignee(user).list();
    }
}
