package com.siga.ecp.tn.service;

import com.siga.ecp.tn.domain.TypeConge;
import com.siga.ecp.tn.repository.TypeCongeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * Delete the typeConge by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete TypeConge : {}", id);
        Optional<TypeConge> type = typeCongeRepository.findById(id);
        if (type.isEmpty()) return;
        type.get().setIsDeleted(true);
        typeCongeRepository.save(type.get());
    }

    /**
     * Get all the typeConges.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeConge> findAll(Pageable pageable) {
        log.debug("Request to get all TypeConges");
        return typeCongeRepository.findAllByisDeletedFalse(pageable);
    }

    /**
     * Get one typeConge by code.
     *
     * @param code the code of the type.
     */
    @Transactional
    public TypeConge findByCode(Integer code) {
        return typeCongeRepository.findByCode(code);
    }

    /**
     * Get one typeConge by libFr.
     *
     * @param libFr the libFr of the type.
     */
    @Transactional
    public TypeConge findByLibFr(String libFr) {
        return typeCongeRepository.findByLibFr(libFr);
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
     * Partially update a typeConge.
     *
     * @param typeConge the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
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
     * Save a typeConge.
     *
     * @param typeConge the entity to save.
     * @return the persisted entity.
     */
    @Transactional
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
    @Transactional
    public TypeConge update(TypeConge typeConge) {
        log.debug("Request to update TypeConge : {}", typeConge);
        return typeCongeRepository.save(typeConge);
    }
}
