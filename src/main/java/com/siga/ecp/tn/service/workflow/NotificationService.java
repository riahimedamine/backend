package com.siga.ecp.tn.service.workflow;

import com.siga.ecp.tn.domain.DemandeConge;
import com.siga.ecp.tn.domain.TreatTask;
import com.siga.ecp.tn.domain.User;
import com.siga.ecp.tn.repository.DemandeCongeRepository;
import com.siga.ecp.tn.repository.UserRepository;
import com.siga.ecp.tn.security.SecurityUtils;
import com.siga.ecp.tn.service.dto.Notification;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final TaskService taskService;
    private final WorkflowService workflowService;
    private final RuntimeService runtimeService;
    private final UserRepository userRepository;
    private final DemandeCongeRepository demandeCongeRepository;

    public NotificationService(
        TaskService taskService,
        WorkflowService workflowService,
        RuntimeService runtimeService,
        UserRepository userRepository,
        DemandeCongeRepository demandeCongeRepository
    ) {
        this.taskService = taskService;
        this.workflowService = workflowService;
        this.runtimeService = runtimeService;
        this.userRepository = userRepository;
        this.demandeCongeRepository = demandeCongeRepository;
    }

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

    public List<Notification> getAllNotificationByCandidateGroup(String group) {
        List<Notification> notifications = new ArrayList<>();
        try {
            List<Task> taskList = new ArrayList<>();
            Optional<List<Task>> allTasks = getAllAssignedTask("demande congé", group);

            allTasks.ifPresent(taskList::addAll);

            return taskList
                .stream()
                .map(this::extractNotifFromTask)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return notifications;
    }

    public Optional<List<Task>> getAllAssignedTask(String processName, String group) {
        TaskQuery query = taskService
            .createTaskQuery()
            .processDefinitionId(workflowService.getIdProcessByName(processName))
            .taskCandidateGroup(group)
            .orderByTaskCreateTime()
            .desc();

        return Optional.ofNullable(query.list());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<Notification> extractNotifFromTask(Task task) {
        try {
            String processInstanceId = task.getProcessInstanceId();

            Optional<String> initiateur = Optional.ofNullable((String) runtimeService.getVariable(processInstanceId, "initiateur"));

            Optional<String> type = Optional.ofNullable((String) runtimeService.getVariable(processInstanceId, "type"));

            if (initiateur.isPresent() && type.isPresent()) {

                Notification notification = detailsNotification(task.getId(), processInstanceId);

                return Optional.of(notification);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }

    public Notification detailsNotification(String taskId, String processInstanceId) {
        Notification notif = new Notification();

        Optional<String> initiateur = Optional.ofNullable((String) runtimeService.getVariable(processInstanceId, "initiateur"));

        Optional<String> type = Optional.ofNullable((String) runtimeService.getVariable(processInstanceId, "type"));

        Object demande = runtimeService.getVariable(processInstanceId, "demande");

        if (type.isPresent()) {
            Optional<User> user = userRepository.findOneByLogin(initiateur.get());

            Task task = taskService.createTaskQuery().taskId(taskId).processInstanceId(processInstanceId).singleResult();
            notif.setInitiateur(initiateur.get());
            notif.setType(type.get());
            notif.setCollaborateur(user.get().getFirstName() + " " + user.get().getLastName());

            notif.setTaskId(taskId);
            notif.setTaskName(task.getName());
            notif.setProcessInstanceId(processInstanceId);
            DemandeConge dmd = (DemandeConge) demande;
            Long dmdId = dmd.getId();
            DemandeConge cong = demandeCongeRepository.findById(dmdId).orElse(new DemandeConge());
            notif.setDemande(cong);
        }

        return notif;
    }

    public List<Notification> getAllNotification() {
        List<Notification> notifications = new ArrayList<>();
        try {
            List<Task> taskList = new ArrayList<>();
            Optional<List<Task>> allTasks = getAllAssignedTask("demande congé");

            allTasks.ifPresent(taskList::addAll);

            return taskList
                .stream()
                .map(this::extractNotifFromTask)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return notifications;
    }

    public Optional<List<Task>> getAllAssignedTask(String processName) {
        TaskQuery query = taskService
            .createTaskQuery()
            .processDefinitionId(workflowService.getIdProcessByName(processName))
            .taskAssignee(SecurityUtils.getCurrentUserLogin().get())
            .orderByTaskCreateTime()
            .desc();

        return Optional.ofNullable(query.list());
    }
}
