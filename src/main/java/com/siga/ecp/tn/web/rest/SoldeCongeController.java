package com.siga.ecp.tn.web.rest;

import com.siga.ecp.tn.domain.SoldeConge;
import com.siga.ecp.tn.service.SoldeCongeService;
import com.siga.ecp.tn.service.dto.SoldeCongeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solde-conges")
public class SoldeCongeController {

    private final Logger log = LoggerFactory.getLogger(SoldeCongeController.class);

    private final SoldeCongeService soldeCongeService;

    public SoldeCongeController(SoldeCongeService soldeCongeService) {
        this.soldeCongeService = soldeCongeService;
    }

    @GetMapping("")
    public List<SoldeCongeDTO> getAllSoldeConges(Pageable pageable) {
        log.debug("REST request to get all SoldeConges");
        return soldeCongeService.findAll(pageable);
    }

    @PostMapping("")
    public SoldeConge createSoldeConge(@RequestBody SoldeCongeDTO soldeCongeDTO) {
        log.debug("REST request to save SoldeConge : {}", soldeCongeDTO);
        return soldeCongeService.saveSolde(soldeCongeDTO);
    }

    @PutMapping("")
    public SoldeCongeDTO updateSoldeConge(@RequestBody SoldeCongeDTO soldeCongeDTO) {
        log.debug("REST request to update SoldeConge : {}", soldeCongeDTO);
        return soldeCongeService.updateSolde(soldeCongeDTO);
    }

    @GetMapping("/{id}")
    public SoldeCongeDTO getSoldeConge(@PathVariable Long id) {
        log.debug("REST request to get SoldeConge : {}", id);
        return soldeCongeService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteSoldeConge(@PathVariable Long id) {
        log.debug("REST request to delete SoldeConge : {}", id);
        soldeCongeService.delete(id);
    }

    @GetMapping("/user/{login}")
    public List<SoldeCongeDTO> getSoldeCongeByUser(@PathVariable String login, Pageable pageable) {
        log.debug("REST request to get SoldeConge by user : {}", login);
        return soldeCongeService.findByUserLogin(login, pageable);
    }
}
