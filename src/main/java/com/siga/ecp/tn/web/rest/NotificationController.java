package com.siga.ecp.tn.web.rest;

import com.siga.ecp.tn.service.dto.Notification;
import com.siga.ecp.tn.service.workflow.LoadNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    private final LoadNotificationService loadNotificationService;

    public NotificationController(LoadNotificationService loadNotificationService) {
        this.loadNotificationService = loadNotificationService;
    }

    @GetMapping
    public List<Notification> getAllNotification() {
        log.debug("REST request to get all Notifications");
        return loadNotificationService.getAllNotification();
    }
}
