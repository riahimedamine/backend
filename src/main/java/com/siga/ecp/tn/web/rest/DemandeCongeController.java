package com.siga.ecp.tn.web.rest;

import com.siga.ecp.tn.domain.TypeConge;
import com.siga.ecp.tn.repository.TypeCongeRepository;
import com.siga.ecp.tn.security.SecurityUtils;
import com.siga.ecp.tn.service.DemandeCongeService;
import com.siga.ecp.tn.service.dto.DemandeCongeDTO;
import com.siga.ecp.tn.service.dto.SoldeCongeDTO;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/demande-conges")
public class DemandeCongeController {

    private final Logger log = LoggerFactory.getLogger(DemandeCongeController.class);

    private final DemandeCongeService demandeCongeService;
    private final TypeCongeRepository typeCongeRepository;

    public DemandeCongeController(DemandeCongeService demandeCongeService, TypeCongeRepository typeCongeRepository) {
        this.demandeCongeService = demandeCongeService;
        this.typeCongeRepository = typeCongeRepository;
    }

    @GetMapping("")
    public List<DemandeCongeDTO> getAllDemandeConges(Pageable pageable) {
        log.debug("REST request to get all DemandeConges");
        return demandeCongeService.findAll(pageable);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public DemandeCongeDTO createDemandeConge(@RequestBody DemandeCongeDTO demandeCongeDTO) {
        log.debug("REST request to save DemandeConge : {}", demandeCongeDTO);
        return demandeCongeService.saveDemandeConge(demandeCongeDTO);
    }

    @GetMapping("/{id}")
    public Optional<DemandeCongeDTO> getDemandeConge(@PathVariable Long id) {
        log.debug("REST request to get DemandeConge : {}", id);
        return demandeCongeService.findDemandeConge(id);
    }

    @PutMapping("")
    public DemandeCongeDTO updateDemandeConge(@RequestBody DemandeCongeDTO demandeCongeDTO) {
        log.debug("REST request to update DemandeConge : {}", demandeCongeDTO);
        return demandeCongeService.updateDemandeConge(demandeCongeDTO);
    }

    @PatchMapping("")
    public Optional<DemandeCongeDTO> partialUpdateDemandeConge(@RequestBody DemandeCongeDTO demandeCongeDTO) {
        log.debug("REST request to partially update DemandeConge : {}", demandeCongeDTO);
        return demandeCongeService.partialUpdate(demandeCongeDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDemandeConge(@PathVariable Long id) {
        log.debug("REST request to delete DemandeConge : {}", id);
        demandeCongeService.delete(id);
    }

    @GetMapping("/user/{login}")
    public List<DemandeCongeDTO> getDemandeCongeByUser(@PathVariable String login) {
        log.debug("REST request to get DemandeConge by user : {}", login);
        return demandeCongeService.findByUser(login);
    }

    @GetMapping("/current-user")
    public List<DemandeCongeDTO> getDemandeCongeByCurrentUser() {
        log.debug("REST request to get DemandeConge by current user");
        return demandeCongeService.findByCurrentUser();
    }

    @PatchMapping("/validate/{id}")
    public void validateDemandeConge(@PathVariable Long id, @RequestParam int vld) {
        log.debug("REST request to validate DemandeConge : {}", id);
        demandeCongeService.validateDemandeConge(id, vld);
    }

    @GetMapping("/types")
    public List<TypeConge> types() {
        log.debug("REST request to get types");
        return typeCongeRepository.findAllByisDeletedFalse();
    }

    @GetMapping("/solde/{login}")
    public List<SoldeCongeDTO> solde(@PathVariable String login) {
        log.debug("REST request to get solde");
        return demandeCongeService.getSoldeCongeByUser(login, Pageable.unpaged());
    }

    @PostMapping("/check")
    public Boolean check(@RequestBody LocalDate dateDebut, @RequestBody LocalDate dateFin) {
        log.debug("REST request to check solde");
        return demandeCongeService.check(dateDebut, dateFin);
    }
}
