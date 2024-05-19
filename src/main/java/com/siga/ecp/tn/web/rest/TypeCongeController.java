package com.siga.ecp.tn.web.rest;

import com.siga.ecp.tn.domain.TypeConge;
import com.siga.ecp.tn.service.TypeCongeService;
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
 * REST controller for managing {@link TypeConge}.
 */
@RestController
@RequestMapping("/api/type-conges")
@Tag(name = "Types de conge", description = "Type Conge Management")
@SuppressWarnings({"java:S4684"})
public class TypeCongeController {

    private final Logger log = LoggerFactory.getLogger(TypeCongeController.class);

    private final TypeCongeService typeCongeService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public TypeCongeController(TypeCongeService typeCongeService) {
        this.typeCongeService = typeCongeService;
    }

    /**
     * {@code POST  /type-conges} : Create a new typeConge.
     *
     * @param typeConge the typeConge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeConge, or with status {@code 400 (Bad Request)} if the typeConge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority(\"" + "ROLE_ADMIN" + "\")")
    public ResponseEntity<TypeConge> createTypeConge(@RequestBody TypeConge typeConge) throws URISyntaxException {
        log.debug("REST request to save TypeConge : {}", typeConge);

        TypeConge type = typeCongeService.save(typeConge);
        return ResponseEntity
            .created(new URI("/api/demande-conge/" + type.getId()))
            .headers(HeaderUtil.createAlert(applicationName, "congeManagement.created", type.getId().toString()))
            .body(type);
    }

    /**
     * {@code DELETE  /type-conges/:id} : delete the "id" typeConge.
     *
     * @param id the id of the typeConge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + "ROLE_ADMIN" + "\")")
    public ResponseEntity<Object> deleteTypeConge(@PathVariable Long id) {
        log.debug("REST request to delete TypeConge : {}", id);
        typeCongeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "typeManagement.deleted", id.toString())).build();
    }

    /**
     * {@code GET  /type-conges} : get all the typeConges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeConges in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TypeConge>> getAllTypeConges(@ParameterObject Pageable pageable) {
        log.debug("REST request to get all TypeConges");
        Page<TypeConge> page = typeCongeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET  /type-conges/:id} : get the "id" typeConge.
     *
     * @param id the id of the typeConge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeConge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeConge> getTypeConge(@PathVariable Long id) {
        log.debug("REST request to get TypeConge : {}", id);
        return ResponseUtil.wrapOrNotFound(typeCongeService.findOne(id));
    }

    /**
     * {@code PATCH  /type-conges} : Updates an existing typeConge.
     *
     * @param typeConge the typeConge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeConge,
     * or with status {@code 400 (Bad Request)} if the typeConge is not valid,
     * or with status {@code 404 (Not Found)} if the typeConge is not found,
     */
    @PatchMapping("")
    @PreAuthorize("hasAuthority(\"" + "ROLE_ADMIN" + "\")")
    public ResponseEntity<TypeConge> partialUpdateTypeConge(@RequestBody TypeConge typeConge) {
        log.debug("REST request to partially update TypeConge : {}", typeConge);
        return ResponseUtil.wrapOrNotFound(typeCongeService.partialUpdate(typeConge));
    }

    /**
     * {@code PUT  /type-conges} : Updates an existing typeConge.
     *
     * @param typeConge the typeConge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeConge,
     * or with status {@code 400 (Bad Request)} if the typeConge is not valid,
     */
    @PutMapping("")
    @PreAuthorize("hasAuthority(\"" + "ROLE_ADMIN" + "\")")
    public ResponseEntity<TypeConge> updateTypeConge(@RequestBody TypeConge typeConge) {
        log.debug("REST request to update TypeConge : {}", typeConge);
        TypeConge type = typeCongeService.update(typeConge);
        HttpHeaders header = HeaderUtil.createAlert(applicationName, "typeManagement.updated", typeConge.getId().toString());
        return new ResponseEntity<>(type, header, HttpStatus.OK);
    }


}
