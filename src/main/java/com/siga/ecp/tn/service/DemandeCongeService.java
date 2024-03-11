package com.siga.ecp.tn.service;

import com.siga.ecp.tn.domain.DemandeConge;
import com.siga.ecp.tn.repository.DemandeCongeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link com.siga.ecp.tn.domain.DemandeConge}.
 */
@Service
@Transactional
public class DemandeCongeService {

    private final Logger log = LoggerFactory.getLogger(DemandeCongeService.class);

    private final DemandeCongeRepository demandeCongeRepository;

    public DemandeCongeService(DemandeCongeRepository demandeCongeRepository) {
        this.demandeCongeRepository = demandeCongeRepository;
    }

    /**
     * Save a demandeConge.
     *
     * @param demandeConge the entity to save.
     * @return the persisted entity.
     */
    public DemandeConge saveDemandeConge(DemandeConge demandeConge) {
        log.debug("Request to save DemandeConge : {}", demandeConge);
        return demandeCongeRepository.save(demandeConge);
    }

    /**
     * Update a demandeConge.
     *
     * @param demandeConge the entity to save.
     * @return the persisted entity.
     */
    public DemandeConge updateDemandeConge(DemandeConge demandeConge) {
        log.debug("Request to update DemandeConge : {}", demandeConge);
        return demandeCongeRepository.save(demandeConge);
    }

    /**
     * Partially update a demandeConge.
     *
     * @param demandeConge the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DemandeConge> partialUpdate(DemandeConge demandeConge) {
        log.debug("Request to partially update DemandeConge : {}", demandeConge);

        return demandeCongeRepository
            .findById(demandeConge.getId())
            .map(existingDemandeConge -> {
                if (demandeConge.getDateDebut() != null) {
                    existingDemandeConge.setDateDebut(demandeConge.getDateDebut());
                }
                if (demandeConge.getDateFin() != null) {
                    existingDemandeConge.setDateFin(demandeConge.getDateFin());
                }

                return existingDemandeConge;
            })
            .map(demandeCongeRepository::save);
    }

    /**
     * Get all the demandeConges.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DemandeConge> findAll() {
        log.debug("Request to get all DemandeConges");
        return demandeCongeRepository.findAll();
    }

    /**
     * Get one demandeConge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DemandeConge> findDemandeConge(Long id) {
        log.debug("Request to get DemandeConge : {}", id);
        return demandeCongeRepository.findById(id);
    }

    /**
     * Delete the demandeConge by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DemandeConge : {}", id);
        demandeCongeRepository.deleteById(id);
    }

    public List<DemandeConge> findByUser(String login) {
        log.debug("Request to get DemandeConge by user : {}", login);
        return demandeCongeRepository.findByUserLogin(login);
    }
}
