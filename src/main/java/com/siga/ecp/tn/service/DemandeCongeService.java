package com.siga.ecp.tn.service;

import com.siga.ecp.tn.repository.DemandeCongeRepository;
import com.siga.ecp.tn.service.dto.DemandeCongeDTO;
import com.siga.ecp.tn.service.mapper.DemandeCongeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link com.siga.ecp.tn.domain.DemandeConge}.
 */
@Service
@Transactional
public class DemandeCongeService {

    private final Logger log = LoggerFactory.getLogger(DemandeCongeService.class);

    private final DemandeCongeRepository demandeCongeRepository;

    private final DemandeCongeMapper demandeCongeMapper;

    public DemandeCongeService(DemandeCongeRepository demandeCongeRepository, DemandeCongeMapper demandeCongeMapper) {
        this.demandeCongeRepository = demandeCongeRepository;
        this.demandeCongeMapper = demandeCongeMapper;
    }

    /**
     * Save a demandeConge.
     *
     * @param demandeCongeDTO the entity to save.
     * @return the persisted entity.
     */
    public DemandeCongeDTO saveDemandeConge(DemandeCongeDTO demandeCongeDTO) {
        log.debug("Request to save DemandeConge : {}", demandeCongeDTO);
        return demandeCongeMapper.demandeCongeToDemandeCongeDTO(demandeCongeRepository.save(demandeCongeMapper.demandeCongeDTOToDemandeConge(demandeCongeDTO)));
    }

    /**
     * Update a demandeConge.
     *
     * @param demandeCongeDTO the entity to save.
     * @return the persisted entity.
     */
    public DemandeCongeDTO updateDemandeConge(DemandeCongeDTO demandeCongeDTO) {
        log.debug("Request to update DemandeConge : {}", demandeCongeDTO);
        return demandeCongeMapper.demandeCongeToDemandeCongeDTO(demandeCongeRepository.save(demandeCongeMapper.demandeCongeDTOToDemandeConge(demandeCongeDTO)));
    }

    /**
     * Partially update a demandeConge.
     *
     * @param demandeCongeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DemandeCongeDTO> partialUpdate(DemandeCongeDTO demandeCongeDTO) {
        log.debug("Request to partially update DemandeConge : {}", demandeCongeDTO);

        return demandeCongeRepository.findById(demandeCongeDTO.getId()).map(existingDemandeConge -> {
            if (demandeCongeDTO.getDateDebut() != null) {
                existingDemandeConge.setDateDebut(demandeCongeDTO.getDateDebut());
            }
            if (demandeCongeDTO.getDateFin() != null) {
                existingDemandeConge.setDateFin(demandeCongeDTO.getDateFin());
            }

            return existingDemandeConge;
        }).map(demandeCongeRepository::save).map(demandeCongeMapper::demandeCongeToDemandeCongeDTO);
    }

    /**
     * Get all the demandeConges.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DemandeCongeDTO> findAll() {
        log.debug("Request to get all DemandeConges");
        return demandeCongeRepository.findAll().stream().map(DemandeCongeDTO::new).collect(Collectors.toList());
    }

    /**
     * Get one demandeConge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DemandeCongeDTO> findDemandeConge(Long id) {
        log.debug("Request to get DemandeConge : {}", id);
        if (demandeCongeRepository.findById(id).isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(demandeCongeMapper.demandeCongeToDemandeCongeDTO(demandeCongeRepository.findById(id).get()));
        }
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

    public List<DemandeCongeDTO> findByUser(String login) {
        log.debug("Request to get DemandeConge by user : {}", login);
        return demandeCongeRepository.findByUserLogin(login).stream().map(DemandeCongeDTO::new).collect(Collectors.toList());
    }

    public List<DemandeCongeDTO> findByCurrentUser() {
        log.debug("Request to get DemandeConge by current user");
        return demandeCongeRepository.findByUserIsCurrentUser().stream().map(DemandeCongeDTO::new).collect(Collectors.toList());
    }
}
