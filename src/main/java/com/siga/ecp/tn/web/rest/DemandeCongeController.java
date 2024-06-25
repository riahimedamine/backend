package com.siga.ecp.tn.web.rest;

import com.siga.ecp.tn.domain.TreatTask;
import com.siga.ecp.tn.domain.TypeConge;
import com.siga.ecp.tn.repository.TypeCongeRepository;
import com.siga.ecp.tn.security.AuthoritiesConstants;
import com.siga.ecp.tn.security.SecurityUtils;
import com.siga.ecp.tn.service.DemandeCongeService;
import com.siga.ecp.tn.service.dto.DemandeCongeDTO;
import com.siga.ecp.tn.service.dto.DemandeCongeError;
import com.siga.ecp.tn.service.dto.SoldeCongeDTO;
import com.siga.ecp.tn.service.workflow.NotificationService;
import com.siga.ecp.tn.web.rest.vm.Demande;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link com.siga.ecp.tn.domain.DemandeConge}.
 */
@RestController
@RequestMapping("/api/demande-conges")
@Tag(name = "Demandes de conge", description = "Demandes Conge Management")
public class DemandeCongeController {

    private final Logger log = LoggerFactory.getLogger(DemandeCongeController.class);

    private final DemandeCongeService demandeCongeService;

    private final TypeCongeRepository typeCongeRepository;

    private final NotificationService notificationService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public DemandeCongeController(DemandeCongeService demandeCongeService, TypeCongeRepository typeCongeRepository, NotificationService notificationService) {
        this.demandeCongeService = demandeCongeService;
        this.typeCongeRepository = typeCongeRepository;
        this.notificationService = notificationService;
    }

    /**
     * {@code POST  /demande-conges/check} : check if creating a demande is possible.
     *
     * @param demande the demande to check.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the error object.
     */
    @PostMapping("/check")
    public DemandeCongeError check(@RequestBody Demande demande) {
        log.debug("REST request to check solde");

        return demandeCongeService.check(demande.dateDebut, demande.dateFin, demande.isUpdate);
    }

    /**
     * {@code POST  /demande-conges} : Create a new demandeConge.
     *
     * @param demandeCongeDTO the demandeCongeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandeCongeDTO, or with status {@code 400 (Bad Request)} if the demandeConge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DemandeCongeDTO> createDemandeConge(@RequestBody DemandeCongeDTO demandeCongeDTO) throws URISyntaxException {
        log.debug("REST request to save DemandeConge : {}", demandeCongeDTO);
        if (demandeCongeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(applicationName, true, "congeManagement", "idexists", "A new demande Conge cannot already have an ID")).body(null);
        }
        DemandeCongeDTO demande = demandeCongeService.saveDemandeConge(demandeCongeDTO);
        return ResponseEntity
            .created(new URI("/api/demande-conge/" + demande.getId()))
            .headers(HeaderUtil.createAlert(applicationName, "congeManagement.created", demande.getId().toString()))
            .body(demande);
    }

    /**
     * {@code DELETE  /demande-conges/:id} : delete the "id" demandeConge.
     *
     * @param id the id of the demandeCongeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteDemandeConge(@PathVariable Long id) {
        log.debug("REST request to delete DemandeConge : {}", id);
        demandeCongeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "congeManagement.deleted", id.toString())).build();
    }

    /**
     * {@code GET  /demande-conges} : get all the demandeConges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeConges in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<List<DemandeCongeDTO>> getAllDemandeConges(Pageable pageable) {
        log.debug("REST request to get all DemandeConges");
        final Page<DemandeCongeDTO> page = demandeCongeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET  /demande-conges/:id} : get the "id" demandeConge.
     *
     * @param id the id of the demandeCongeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandeCongeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DemandeCongeDTO> getDemandeConge(@PathVariable Long id) {
        log.debug("REST request to get DemandeConge : {}", id);
        return ResponseUtil.wrapOrNotFound(demandeCongeService.findDemandeConge(id));
    }

    /**
     * {@code GET  /demande-conges/current-user} : get all the demandeConges of the current user.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeConges in body.
     */
    @GetMapping("/current-user")
    public ResponseEntity<List<DemandeCongeDTO>> getDemandeCongeByCurrentUser(@ParameterObject Pageable pageable) {
        log.debug("REST request to get DemandeConge by current user");
        final Page<DemandeCongeDTO> page = demandeCongeService.findByCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET  /demande-conges/user/:login} : get the "login" demandeConge.
     *
     * @param login the login of the demandeCongeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandeCongeDTO.
     */
    @GetMapping("/user/{login}")
    public ResponseEntity<List<DemandeCongeDTO>> getDemandeCongeByUser(@ParameterObject Pageable pageable, @PathVariable String login) {
        log.debug("REST request to get DemandeConge by user : {}", login);
        final Page<DemandeCongeDTO> page = demandeCongeService.findByUser(login, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code PATCH  /demande-conges} : Partial updates given fields of an existing demandeConge, field will ignore if it is null
     *
     * @param demandeCongeDTO the demandeCongeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeCongeDTO,
     * or with status {@code 400 (Bad Request)} if the demandeCongeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the demandeCongeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandeCongeDTO couldn't be updated.
     */
    @PatchMapping("")
    public ResponseEntity<DemandeCongeDTO> partialUpdateDemandeConge(@RequestBody DemandeCongeDTO demandeCongeDTO) {
        log.debug("REST request to partially update DemandeConge : {}", demandeCongeDTO);
        return ResponseUtil.wrapOrNotFound(demandeCongeService.partialUpdate(demandeCongeDTO));
    }

    /**
     * {@code GET  /demande-conges/solde/:login} : get the solde of a user.
     *
     * @param login the login of the user.
     * @return a list of {@link SoldeCongeDTO} with status {@code 200 (OK)}.
     */
    @GetMapping("/solde/{login}")
    public List<SoldeCongeDTO> solde(@PathVariable String login) {
        log.debug("REST request to get solde");
        return demandeCongeService.getSoldeCongeByUser(login, Pageable.unpaged()).getContent();
    }

    /**
     * {@code GET  /demande-conges/types} : get all the types.
     *
     * @return the list of {@link TypeConge} with status {@code 200 (OK)}.
     */
    @GetMapping("/types")
    public List<TypeConge> types() {
        log.debug("REST request to get types");
        return typeCongeRepository.findAllByisDeletedFalse(Pageable.unpaged()).getContent();
    }

    /**
     * {@code PUT  /demande-conges} : Updates an existing demandeConge.
     *
     * @param demandeCongeDTO the demandeCongeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeCongeDTO,
     * or with status {@code 400 (Bad Request)} if the demandeCongeDTO is not valid,
     */
    @PutMapping("")
    public ResponseEntity<DemandeCongeDTO> updateDemandeConge(@RequestBody DemandeCongeDTO demandeCongeDTO) {
        log.debug("REST request to update DemandeConge : {}", demandeCongeDTO);
        DemandeCongeDTO demande = demandeCongeService.updateDemandeConge(demandeCongeDTO);
        HttpHeaders header = HeaderUtil.createAlert(applicationName, "congeManagement.updated", demandeCongeDTO.getId().toString());
        return new ResponseEntity<>(demande, header, HttpStatus.OK);
    }

    /**
     * {@code PATCH  /demande-conges/validate/:taskId} : validate a demandeConge.
     *
     * @param taskId the taskId of the demandeConge to validate.
     * @param vld    the validation of the demandeConge.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @PatchMapping("/validate/{taskId}")
    public ResponseEntity<Object> validateDemandeConge(@PathVariable String taskId, @RequestParam int vld) {
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
        treatTask.setInitiator(SecurityUtils.getCurrentUserLogin().orElse(null));
        notificationService.completeTask(treatTask);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "congeManagement.validated", "")).build();
    }

    /**
     * {@code GET  /demande-conges/validate} : get all the demandeConges to validate.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeConges in body.
     */
    @GetMapping("/validate")
    public List<DemandeCongeDTO> getDemadeCongeByValidator() {
        log.debug("REST request to get DemandeConge by validator");
        return demandeCongeService.getDemandeCongeByCurrentValidator();
    }

    /**
     * {@code GET  /demande-conges/rh} : get all the demandeConges of the RH.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeConges in body.
     */
    @GetMapping("/rh")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.RH + "\")")
    public List<DemandeCongeDTO> getDemandeCongeRh() {
        log.debug("REST request to get DemandeConge by Rh");
        return demandeCongeService.findByRh();
    }
}
