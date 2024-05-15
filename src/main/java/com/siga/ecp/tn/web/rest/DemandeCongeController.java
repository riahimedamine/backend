package com.siga.ecp.tn.web.rest;

import com.siga.ecp.tn.domain.TreatTask;
import com.siga.ecp.tn.domain.TypeConge;
import com.siga.ecp.tn.repository.TypeCongeRepository;
import com.siga.ecp.tn.security.SecurityUtils;
import com.siga.ecp.tn.service.DemandeCongeService;
import com.siga.ecp.tn.service.dto.DemandeCongeDTO;
import com.siga.ecp.tn.service.dto.DemandeCongeError;
import com.siga.ecp.tn.service.dto.SoldeCongeDTO;
import com.siga.ecp.tn.service.workflow.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/demande-conges")
public class DemandeCongeController {

    private final Logger log = LoggerFactory.getLogger(DemandeCongeController.class);

    private final DemandeCongeService demandeCongeService;
    private final TypeCongeRepository typeCongeRepository;

    private final NotificationService notificationService;

    public DemandeCongeController(DemandeCongeService demandeCongeService, TypeCongeRepository typeCongeRepository, NotificationService notificationService) {
        this.demandeCongeService = demandeCongeService;
        this.typeCongeRepository = typeCongeRepository;
        this.notificationService = notificationService;
    }

    @PostMapping("/check")
    public DemandeCongeError check(@RequestBody Demande demande) {
        log.debug("REST request to check solde");
        return demandeCongeService.check(demande.getDateDebut(), demande.getDateFin());
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public DemandeCongeDTO createDemandeConge(@RequestBody DemandeCongeDTO demandeCongeDTO) {
        log.debug("REST request to save DemandeConge : {}", demandeCongeDTO);
        return demandeCongeService.saveDemandeConge(demandeCongeDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDemandeConge(@PathVariable Long id) {
        log.debug("REST request to delete DemandeConge : {}", id);
        demandeCongeService.delete(id);
    }

    @GetMapping("")
    public List<DemandeCongeDTO> getAllDemandeConges(Pageable pageable) {
        log.debug("REST request to get all DemandeConges");
        return demandeCongeService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Optional<DemandeCongeDTO> getDemandeConge(@PathVariable Long id) {
        log.debug("REST request to get DemandeConge : {}", id);
        return demandeCongeService.findDemandeConge(id);
    }

    @GetMapping("/user/{login}")
    public List<DemandeCongeDTO> getDemandeCongeByUser(@PathVariable String login) {
        log.debug("REST request to get DemandeConge by user : {}", login);
        return demandeCongeService.findByUser(login);
    }

    @PatchMapping("")
    public Optional<DemandeCongeDTO> partialUpdateDemandeConge(@RequestBody DemandeCongeDTO demandeCongeDTO) {
        log.debug("REST request to partially update DemandeConge : {}", demandeCongeDTO);
        return demandeCongeService.partialUpdate(demandeCongeDTO);
    }

    @GetMapping("/solde/{login}")
    public List<SoldeCongeDTO> solde(@PathVariable String login) {
        log.debug("REST request to get solde");
        return demandeCongeService.getSoldeCongeByUser(login, Pageable.unpaged());
    }

    @GetMapping("/types")
    public List<TypeConge> types() {
        log.debug("REST request to get types");
        return typeCongeRepository.findAllByisDeletedFalse();
    }

    @PutMapping("")
    public DemandeCongeDTO updateDemandeConge(@RequestBody DemandeCongeDTO demandeCongeDTO) {
        log.debug("REST request to update DemandeConge : {}", demandeCongeDTO);
        return demandeCongeService.updateDemandeConge(demandeCongeDTO);
    }

    @PatchMapping("/validate/{taskId}")
    public void validateDemandeConge(@PathVariable String taskId, @RequestParam int vld) {
        log.debug("REST request to validate DemandeConge : {}", taskId);
        TreatTask treatTask = new TreatTask();
        treatTask.setTaskId(taskId);
        if (vld == 1 || vld == 2) {
            treatTask.setComment("Demande de congé validée");
            treatTask.setValue("accord");
        } else {
            treatTask.setComment("Demande de congé refusée");
            treatTask.setValue("rejet");
        }
        treatTask.setInitiator(SecurityUtils.getCurrentUserLogin().get());
        notificationService.completeTask(treatTask);
    }

    @GetMapping("/validate")
    public List<DemandeCongeDTO> getDemadeCongeByValidator() {
        log.debug("REST request to get DemandeConge by validator");
        return demandeCongeService.getDemandeCongeByCurrentValidator();
    }

    @GetMapping("/current-user")
    public List<DemandeCongeDTO> getDemandeCongeByCurrentUser() {
        log.debug("REST request to get DemandeConge by current user");
        return demandeCongeService.findByCurrentUser();
    }

    @GetMapping("/rh")
    public List<DemandeCongeDTO> getDemandeCongeRh() {
        log.debug("REST request to get DemandeConge by Rh");
        return demandeCongeService.findByRh();
    }

    public static class Demande {

        private LocalDate dateDebut;
        private LocalDate dateFin;

        public Demande() {
            // Empty constructor needed for Jackson.
        }

        public LocalDate getDateDebut() {
            return dateDebut;
        }

        public void setDateDebut(LocalDate dateDebut) {
            this.dateDebut = dateDebut;
        }

        public LocalDate getDateFin() {
            return dateFin;
        }

        public void setDateFin(LocalDate dateFin) {
            this.dateFin = dateFin;
        }
    }
}
