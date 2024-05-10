package com.siga.ecp.tn.service.workflow;

import com.siga.ecp.tn.domain.DemandeConge;
import com.siga.ecp.tn.domain.User;
import com.siga.ecp.tn.repository.DemandeCongeRepository;
import com.siga.ecp.tn.repository.UserRepository;
import com.siga.ecp.tn.security.SecurityUtils;
import com.siga.ecp.tn.service.WorkflowService;
import com.siga.ecp.tn.service.dto.Notification;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class loadnotificationservice {

    private static final Logger log = LoggerFactory.getLogger(loadnotificationservice.class);
    private final TaskService taskService;
    private final WorkflowService workflowService;
    private final RuntimeService runtimeService;
    private final UserRepository userRepository;
    private final DemandeCongeRepository demandeCongeRepository;

    public loadnotificationservice(
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

    public List<Notification> getAllNotification() {
        List<Notification> notifications = new ArrayList<Notification>();
        try {
            List<Task> taskList = new ArrayList<>();
            Optional<List<Task>> allTasks = getAllAssignedTask("demande congé ");

            if (allTasks.isPresent()) {
                taskList.addAll(allTasks.get());
            }
            log.debug("REST request to get all Notifications");

            return taskList
                .stream()
                .map(this::extractNotifFromTask)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        } catch (Exception e) {
            // TODO: handle exception
        }

        return notifications;
    }

    public Optional<List<Task>> getAllAssignedTask(String processName) {
        TaskQuery query = taskService
            .createTaskQuery()
            /** Utiliser la fonction getIdProcessByName qui return l id de process **/

            .processDefinitionId(workflowService.getIdProcessByName(processName))
            .taskAssignee(SecurityUtils.getCurrentUserLogin().get().toString())
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
                Optional<User> user = userRepository.findOneByLogin(initiateur.get());

                /** Créer un DTO Notification **/

                Notification notif = new Notification(
                    processInstanceId,
                    task.getId(),
                    task.getName(),
                    task.getDueDate(),
                    type.get(),
                    initiateur.get(),
                    user.get().getFirstName().concat(" ").concat(user.get().getLastName()),
                    task.getCreateTime()
                );

                return Optional.ofNullable(notif);
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
            notif.setCollaborateur(user.get().getFirstName().concat(" ").concat(user.get().getLastName()));

            notif.setTaskId(taskId);
            notif.setTaskName(task.getName());
            notif.setProcessInstanceId(processInstanceId);
            DemandeConge dmd1 = (DemandeConge) demande;
            Long dmdId = dmd1.getId();
            DemandeConge cong = new DemandeConge();
            cong = demandeCongeRepository.findById(dmdId).get();
            notif.setDemande(cong);
        }

        return notif;
    }
}
