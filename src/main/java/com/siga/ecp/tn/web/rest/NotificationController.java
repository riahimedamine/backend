package com.siga.ecp.tn.web.rest;

import com.siga.ecp.tn.security.AuthoritiesConstants;
import com.siga.ecp.tn.service.UserService;
import com.siga.ecp.tn.service.dto.Notification;
import com.siga.ecp.tn.service.workflow.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tasks", description = "Tasks Management")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    private final NotificationService notificationService;

    private final UserService userService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    /**
     * {@code GET  /notifications} : get all the notifications.
     *
     * @return the {@link List} of notifications.
     */
    @GetMapping
    public List<Notification> getAllNotification() {
        log.debug("REST request to get all Notifications");
        return notificationService.getAllNotification();
    }

    /**
     * {@code GET  /notifications} : get all the notifications.
     *
     * @return the {@link List} of notifications.
     */
    @GetMapping("/rh")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.RH + "\")")
    public List<Notification> getAllNotificationByRh() {
        log.debug("REST request to get all Notifications by Rh");
        return notificationService.getAllNotificationByCandidateGroup("RH");
    }

    /**
     * {@code GET  /notifications} : get all the notifications.
     *
     * @return the {@link List} of notifications.
     */
    @GetMapping("/supervisees")
    public List<String> getSupervisees() {
        log.debug("REST request to get all Supervisees");
        return userService.getSupervisees();
    }
}
