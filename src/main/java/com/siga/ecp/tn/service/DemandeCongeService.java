package com.siga.ecp.tn.service;

import com.siga.ecp.tn.domain.DemandeConge;
import com.siga.ecp.tn.domain.SoldeConge;
import com.siga.ecp.tn.repository.DemandeCongeRepository;
import com.siga.ecp.tn.repository.SoldeCongeRepository;
import com.siga.ecp.tn.service.dto.DemandeCongeDTO;
import com.siga.ecp.tn.service.mapper.DemandeCongeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
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
    private final SoldeCongeRepository soldeCongeRepository;

    public DemandeCongeService(
        DemandeCongeRepository demandeCongeRepository,
        DemandeCongeMapper demandeCongeMapper,
        SoldeCongeRepository soldeCongeRepository
    ) {
        this.demandeCongeRepository = demandeCongeRepository;
        this.demandeCongeMapper = demandeCongeMapper;
        this.soldeCongeRepository = soldeCongeRepository;
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

    /**
     * Get all the demandeConges.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DemandeCongeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeConges");
        return demandeCongeRepository.findAll(pageable).stream().map(DemandeCongeDTO::new).collect(Collectors.toList());
    }

    public List<DemandeCongeDTO> findByCurrentUser() {
        log.debug("Request to get DemandeConge by current user");
        return demandeCongeRepository.findByUserIsCurrentUser().stream().map(DemandeCongeDTO::new).collect(Collectors.toList());
    }

    public List<DemandeCongeDTO> findByUser(String login) {
        log.debug("Request to get DemandeConge by user : {}", login);
        return demandeCongeRepository.findByUserLogin(login).stream().map(DemandeCongeDTO::new).collect(Collectors.toList());
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
     * Partially update a demandeConge.
     *
     * @param demandeCongeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DemandeCongeDTO> partialUpdate(DemandeCongeDTO demandeCongeDTO) {
        log.debug("Request to partially update DemandeConge : {}", demandeCongeDTO);

        return demandeCongeRepository
            .findById(demandeCongeDTO.getId())
            .map(existingDemandeConge -> {
                if (demandeCongeDTO.getDateDebut() != null) {
                    existingDemandeConge.setDateDebut(demandeCongeDTO.getDateDebut());
                }
                if (demandeCongeDTO.getDateFin() != null) {
                    existingDemandeConge.setDateFin(demandeCongeDTO.getDateFin());
                }

                return existingDemandeConge;
            })
            .map(demandeCongeRepository::save)
            .map(demandeCongeMapper::demandeCongeToDemandeCongeDTO);
    }

    /**
     * Save a demandeConge.
     *
     * @param demandeCongeDTO the entity to save.
     * @return the persisted entity.
     */
    public DemandeCongeDTO saveDemandeConge(DemandeCongeDTO demandeCongeDTO) {
        log.debug("Request to save DemandeConge : {}", demandeCongeDTO);
        return demandeCongeMapper.demandeCongeToDemandeCongeDTO(
            demandeCongeRepository.save(demandeCongeMapper.demandeCongeDTOToDemandeConge(demandeCongeDTO))
        );
    }

    /**
     * Update a demandeConge.
     *
     * @param demandeCongeDTO the entity to save.
     * @return the persisted entity.
     */
    public DemandeCongeDTO updateDemandeConge(DemandeCongeDTO demandeCongeDTO) {
        log.debug("Request to update DemandeConge : {}", demandeCongeDTO);
        return demandeCongeMapper.demandeCongeToDemandeCongeDTO(
            demandeCongeRepository.save(demandeCongeMapper.demandeCongeDTOToDemandeConge(demandeCongeDTO))
        );
    }

    public void validateDemandeConge(Long id, int vld) {
        log.debug("Request to validate DemandeConge : {}", id);
        DemandeConge demandeConge = demandeCongeRepository.findById(id).orElse(null);
        if (demandeConge != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(demandeConge.getDateDebut());
            int year = cal.getWeekYear();
            String user = demandeConge.getUser().getLogin();
            long nbJours = (demandeConge.getDateFin().getTime() - demandeConge.getDateDebut().getTime()) / (1000 * 60 * 60 * 24);
            SoldeConge soldeConge = soldeCongeRepository.findByYearYearAndUserLogin(year, user).orElse(null);
            log.debug("nbJours : {}", nbJours);
            log.debug("year : {}", year);
            if (vld == 1 && soldeConge != null && soldeConge.getSolde() >= nbJours) {
                soldeConge.setSolde((int) (soldeConge.getSolde() - nbJours));
                soldeCongeRepository.save(soldeConge);
                demandeConge.setVld(1);
            } else {
                demandeConge.setVld(2);
            }
            demandeCongeRepository.save(demandeConge);
        }
    }
}
