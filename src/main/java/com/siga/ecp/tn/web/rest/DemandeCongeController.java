package com.siga.ecp.tn.web.rest;

import com.siga.ecp.tn.domain.DemandeConge;
import com.siga.ecp.tn.service.DemandeCongeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/demande-conges")
public class DemandeCongeController {
    private final Logger log = LoggerFactory.getLogger(DemandeCongeController.class);

    private final DemandeCongeService demandeCongeService;

    public DemandeCongeController(DemandeCongeService demandeCongeService) {
        this.demandeCongeService = demandeCongeService;
    }

    @GetMapping("")
    public List<DemandeConge> getAllDemandeConges() {
        log.debug("REST request to get all DemandeConges");
        return demandeCongeService.findAll();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public DemandeConge createDemandeConge(@RequestBody DemandeConge demandeConge) {
        log.debug("REST request to save DemandeConge : {}", demandeConge);
        return demandeCongeService.saveDemandeConge(demandeConge);
    }

    @GetMapping("/{id}")
    public Optional<DemandeConge> getDemandeConge(@PathVariable Long id) {
        log.debug("REST request to get DemandeConge : {}", id);
        return demandeCongeService.findDemandeConge(id);
    }

    @PutMapping("")
    public DemandeConge updateDemandeConge(@RequestBody DemandeConge demandeConge) {
        log.debug("REST request to update DemandeConge : {}", demandeConge);
        return demandeCongeService.updateDemandeConge(demandeConge);
    }

    @PatchMapping("")
    public Optional<DemandeConge> partialUpdateDemandeConge(@RequestBody DemandeConge demandeConge) {
        log.debug("REST request to partially update DemandeConge : {}", demandeConge);
        return demandeCongeService.partialUpdate(demandeConge);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDemandeConge(@PathVariable Long id) {
        log.debug("REST request to delete DemandeConge : {}", id);
        demandeCongeService.delete(id);
    }

    @GetMapping("/user/{login}")
    public List<DemandeConge> getDemandeCongeByUser(@PathVariable String login) {
        log.debug("REST request to get DemandeConge by user : {}", login);
        return demandeCongeService.findByUser(login);
    }
}
