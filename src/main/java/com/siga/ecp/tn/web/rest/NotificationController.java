package com.siga.ecp.tn.web.rest;

import com.siga.ecp.tn.service.UserService;
import com.siga.ecp.tn.service.dto.Notification;
import com.siga.ecp.tn.service.workflow.NotificationService;
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

    private final NotificationService notificationService;

    private final UserService userService;

    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping
    public List<Notification> getAllNotification() {
        log.debug("REST request to get all Notifications");
        return notificationService.getAllNotification();
    }

    @GetMapping("/rh")
    public List<Notification> getAllNotificationByRh() {
        log.debug("REST request to get all Notifications by Rh");
        return notificationService.getAllNotificationByCandidateGroup("RH");
    }

    @GetMapping("/supervisees")
    public List<String> getSupervisees() {
        log.debug("REST request to get all Supervisees");
        return userService.getSupervisees();
    }
}
