package com.siga.ecp.tn.service.dto;

import com.siga.ecp.tn.domain.DemandeConge;

import java.util.Date;

public class Notification {

    private String initiateur;
    private String type;
    private String collaborateur;
    private String taskName;
    private String processInstanceId;
    private String taskId;
    private DemandeConge demande;
    private Date createdAt;
    private Date dueDate;

    public Notification(
        String processInstanceId,
        String taskId,
        String taskName,
        Date dueDate,
        String type,
        String initiateur,
        String collaborateur,
        Date createdAt
    ) {
        this.initiateur = initiateur;
        this.type = type;
        this.collaborateur = collaborateur;
        this.taskName = taskName;
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
    }

    public Notification() {
    }

    public String getCollaborateur() {
        return collaborateur;
    }

    public void setCollaborateur(String collaborateur) {
        this.collaborateur = collaborateur;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public DemandeConge getDemande() {
        return demande;
    }

    public void setDemande(DemandeConge demande) {
        this.demande = demande;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getInitiateur() {
        return initiateur;
    }

    public void setInitiateur(String initiateur) {
        this.initiateur = initiateur;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
