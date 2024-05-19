package com.siga.ecp.tn.web.rest;

import com.siga.ecp.tn.domain.SoldeConge;
import com.siga.ecp.tn.security.AuthoritiesConstants;
import com.siga.ecp.tn.service.SoldeCongeService;
import com.siga.ecp.tn.service.dto.SoldeCongeDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link com.siga.ecp.tn.domain.SoldeConge}.
 */
@RestController
@RequestMapping("/api/solde-conges")
@Tag(name = "Soldes de Conge", description = "Soldes Conges management")
public class SoldeCongeController {

    private final Logger log = LoggerFactory.getLogger(SoldeCongeController.class);

    private final SoldeCongeService soldeCongeService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public SoldeCongeController(SoldeCongeService soldeCongeService) {
        this.soldeCongeService = soldeCongeService;
    }

    /**
     * {@code POST  /solde-conges} : Create a new soldeConge.
     *
     * @param soldeCongeDTO the soldeCongeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new soldeCongeDTO, or with status {@code 400 (Bad Request)} if the soldeConge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<SoldeConge> createSoldeConge(@RequestBody SoldeCongeDTO soldeCongeDTO) throws URISyntaxException {
        log.debug("REST request to save SoldeConge : {}", soldeCongeDTO);
        SoldeConge solde = soldeCongeService.saveSolde(soldeCongeDTO);
        return ResponseEntity
            .created(new URI("/api/solde-conges/" + solde.getId().toString()))
            .headers(HeaderUtil.createAlert(applicationName, "congeManagement.created", solde.getId().toString()))
            .body(solde);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Object> deleteSoldeConge(@PathVariable Long id) {
        log.debug("REST request to delete SoldeConge : {}", id);
        soldeCongeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "congeManagement.deleted", id.toString())).build();
    }

    /**
     * {@code GET  /solde-conges} : get all the soldeConges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of soldeConges in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<List<SoldeCongeDTO>> getAllSoldeConges(Pageable pageable) {
        log.debug("REST request to get all SoldeConges");
        final Page<SoldeCongeDTO> page = soldeCongeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET  /solde-conges/:id} : get the "id" soldeConge.
     *
     * @param id the id of the soldeCongeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the soldeCongeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.RH + "\")")
    public SoldeCongeDTO getSoldeConge(@PathVariable Long id) {
        log.debug("REST request to get SoldeConge : {}", id);
        return soldeCongeService.findById(id);
    }

    /**
     * {@code PUT  /solde-conges} : Updates an existing soldeConge.
     *
     * @param soldeCongeDTO the soldeCongeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soldeCongeDTO,
     * or with status {@code 404 (Not Found)} if the soldeCongeDTO is not found.
     */
    @PutMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<SoldeCongeDTO> updateSoldeConge(@RequestBody SoldeCongeDTO soldeCongeDTO) {
        log.debug("REST request to update SoldeConge : {}", soldeCongeDTO);
        SoldeCongeDTO solde = soldeCongeService.updateSolde(soldeCongeDTO);
        HttpHeaders header = HeaderUtil.createAlert(applicationName, "soldeManagement.updated", soldeCongeDTO.getId().toString());
        return new ResponseEntity<>(solde, header, HttpStatus.OK);
    }

    /**
     * {@code GET  /solde-conges/user/:login} : get all the soldeConges by user login.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of soldeConges in body.
     * @throws com.siga.ecp.tn.exception.UserNotFoundException with status {@code 404 (Not Found)} if the user login not found.
     */
    @GetMapping("/current-user")
    public ResponseEntity<List<SoldeCongeDTO>> getSoldeCongeByCurrentUser() {
        log.debug("REST request to get SoldeConge by current user");
        Page<SoldeCongeDTO> page = soldeCongeService.findByCurrentUser(Pageable.unpaged());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
