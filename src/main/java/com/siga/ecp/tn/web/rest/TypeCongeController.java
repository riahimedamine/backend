package com.siga.ecp.tn.web.rest;

import com.siga.ecp.tn.domain.TypeConge;
import com.siga.ecp.tn.service.TypeCongeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/type-conges")
public class TypeCongeController {

    private final Logger log = LoggerFactory.getLogger(TypeCongeController.class);

    private final TypeCongeService typeCongeService;

    public TypeCongeController(TypeCongeService typeCongeService) {
        this.typeCongeService = typeCongeService;
    }

    @GetMapping("")
    public List<TypeConge> getAllTypeConges() {
        log.debug("REST request to get all TypeConges");
        return typeCongeService.findAll();
    }

    @PostMapping("")
    public TypeConge createTypeConge(TypeConge typeConge) {
        log.debug("REST request to save TypeConge : {}", typeConge);
        return typeCongeService.save(typeConge);
    }

    @PutMapping("")
    public TypeConge updateTypeConge(TypeConge typeConge) {
        log.debug("REST request to update TypeConge : {}", typeConge);
        return typeCongeService.update(typeConge);
    }

    @PatchMapping("")
    public Optional<TypeConge> partialUpdateTypeConge(TypeConge typeConge) {
        log.debug("REST request to partially update TypeConge : {}", typeConge);
        return typeCongeService.partialUpdate(typeConge);
    }

    @GetMapping("/{id}")
    public Optional<TypeConge> getTypeConge(@PathVariable Long id) {
        log.debug("REST request to get TypeConge : {}", id);
        return typeCongeService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTypeConge(@PathVariable Long id) {
        log.debug("REST request to delete TypeConge : {}", id);
        typeCongeService.delete(id);
    }


}
