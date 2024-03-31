package com.siga.ecp.tn.service;

import com.siga.ecp.tn.domain.TypeConge;
import com.siga.ecp.tn.repository.TypeCongeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link com.siga.ecp.tn.domain.TypeConge}.
 */
@Service
@Transactional
public class TypeCongeService {

    private final Logger log = LoggerFactory.getLogger(TypeCongeService.class);

    private final TypeCongeRepository typeCongeRepository;

    public TypeCongeService(TypeCongeRepository typeCongeRepository) {
        this.typeCongeRepository = typeCongeRepository;
    }

    /**
     * Save a typeConge.
     *
     * @param typeConge the entity to save.
     * @return the persisted entity.
     */
    public TypeConge save(TypeConge typeConge) {
        log.debug("Request to save TypeConge : {}", typeConge);
        return typeCongeRepository.save(typeConge);
    }

    /**
     * Update a typeConge.
     *
     * @param typeConge the entity to save.
     * @return the persisted entity.
     */
    public TypeConge update(TypeConge typeConge) {
        log.debug("Request to update TypeConge : {}", typeConge);
        return typeCongeRepository.save(typeConge);
    }

    /**
     * Partially update a typeConge.
     *
     * @param typeConge the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TypeConge> partialUpdate(TypeConge typeConge) {
        log.debug("Request to partially update TypeConge : {}", typeConge);

        return typeCongeRepository
            .findById(typeConge.getId())
            .map(existingTypeConge -> {
                if (typeConge.getCode() != null) {
                    existingTypeConge.setCode(typeConge.getCode());
                }
                if (typeConge.getLibFr() != null) {
                    existingTypeConge.setLibFr(typeConge.getLibFr());
                }
                if (typeConge.getLibAr() != null) {
                    existingTypeConge.setLibAr(typeConge.getLibAr());
                }
                if (typeConge.getIsDeleted() != null) {
                    existingTypeConge.setIsDeleted(typeConge.getIsDeleted());
                }

                return existingTypeConge;
            })
            .map(typeCongeRepository::save);
    }

    /**
     * Get all the typeConges.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TypeConge> findAll() {
        log.debug("Request to get all TypeConges");
        return typeCongeRepository.findAllByisDeletedFalse();
    }

    /**
     * Get one typeConge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypeConge> findOne(Long id) {
        log.debug("Request to get TypeConge : {}", id);
        return typeCongeRepository.findById(id);
    }

    /**
     * Delete the typeConge by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeConge : {}", id);
        Optional<TypeConge> type = typeCongeRepository.findById(id);
        if (type.isEmpty()) return;
        type.get().setIsDeleted(true);
    }

    /**
     * Get one typeConge by code.
     *
     * @param code the code of the type.
     */
    public TypeConge findByCode(Integer code) {
        return typeCongeRepository.findByCode(code);
    }

    /**
     * Get one typeConge by libFr.
     *
     * @param libFr the libFr of the type.
     */
    public TypeConge findByLibFr(String libFr) {
        return typeCongeRepository.findByLibFr(libFr);
    }
}
