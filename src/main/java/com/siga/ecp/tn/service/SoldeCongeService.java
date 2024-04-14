package com.siga.ecp.tn.service;

import com.siga.ecp.tn.domain.SoldeConge;
import com.siga.ecp.tn.exception.SoldeNotFoundException;
import com.siga.ecp.tn.exception.UserNotFoundException;
import com.siga.ecp.tn.repository.SoldeCongeRepository;
import com.siga.ecp.tn.service.dto.SoldeCongeDTO;
import com.siga.ecp.tn.service.mapper.SoldeCongeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link com.siga.ecp.tn.domain.SoldeConge}.
 */
@Service
@Transactional
public class SoldeCongeService {
    private final Logger log = LoggerFactory.getLogger(SoldeCongeService.class);

    private final SoldeCongeRepository soldeCongeRepository;

    private final SoldeCongeMapper soldeCongeMapper;

    public SoldeCongeService(SoldeCongeRepository soldeCongeRepository, SoldeCongeMapper soldeCongeMapper) {
        this.soldeCongeRepository = soldeCongeRepository;
        this.soldeCongeMapper = soldeCongeMapper;
    }

    /**
     * Save a soldeConge.
     *
     * @param soldeCongeDTO the entity to save.
     * @return the persisted entity.
     */
    public SoldeConge saveSolde(SoldeCongeDTO soldeCongeDTO) {
        log.debug("Request to save SoldeConge : {}", soldeCongeDTO);
        SoldeConge soldeConge = soldeCongeMapper.soldeCongeDTOToSoldeConge(soldeCongeDTO);
        return soldeCongeRepository.save(soldeConge);
    }

    /**
     * Update a soldeConge.
     *
     * @param soldeCongeDTO the entity to save.
     * @return the persisted entity.
     */
    public SoldeCongeDTO updateSolde(SoldeCongeDTO soldeCongeDTO) {
        log.debug("Request to update SoldeConge : {}", soldeCongeDTO);

        SoldeConge soldeConge = soldeCongeRepository.findById(soldeCongeDTO.getId()).orElseThrow(UserNotFoundException::new);
        SoldeConge updatedSoldeConge = soldeCongeMapper.updateSoldeCongeFromDTO(soldeCongeDTO, soldeConge);

        return soldeCongeMapper.soldeCongeToSoldeCongeDTO(soldeCongeRepository.save(updatedSoldeConge));
    }

    /**
     * Get all the soldeConges.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SoldeCongeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SoldeConges");
        return soldeCongeRepository.findAll(pageable).map(soldeCongeMapper::soldeCongeToSoldeCongeDTO).toList();
    }

    /**
     * Get one soldeConge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public SoldeCongeDTO findById(Long id) {
        log.debug("Request to get SoldeConge : {}", id);
        Optional<SoldeConge> soldeConge = soldeCongeRepository.findById(id);
        if (soldeConge.isEmpty()) {
            throw new SoldeNotFoundException();
        } else {
            return soldeCongeMapper.soldeCongeToSoldeCongeDTO(soldeConge.get());
        }
    }

    /**
     * Delete the soldeConge by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SoldeConge : {}", id);
        soldeCongeRepository.deleteById(id);
    }

    /**
     * Get all the soldeConges by user login.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SoldeCongeDTO> findByUserLogin(String login, Pageable pageable) {
        log.debug("Request to get all SoldeConges by user Login");
        return soldeCongeRepository.findByUserLogin(login, pageable).map(soldeCongeMapper::soldeCongeToSoldeCongeDTO).toList();
    }

}
