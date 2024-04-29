package com.siga.ecp.tn.service;

import com.siga.ecp.tn.domain.DemandeConge;
import com.siga.ecp.tn.domain.SoldeConge;
import com.siga.ecp.tn.repository.DemandeCongeRepository;
import com.siga.ecp.tn.repository.SoldeCongeRepository;
import com.siga.ecp.tn.security.SecurityUtils;
import com.siga.ecp.tn.service.dto.DemandeCongeDTO;
import com.siga.ecp.tn.service.dto.SoldeCongeDTO;
import com.siga.ecp.tn.service.mapper.DemandeCongeMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.siga.ecp.tn.domain.DemandeConge}.
 */
@Service
@Transactional
public class DemandeCongeService {

    private final WorkflowService workflowService;

    private final Logger log = LoggerFactory.getLogger(DemandeCongeService.class);

    private final DemandeCongeRepository demandeCongeRepository;

    private final DemandeCongeMapper demandeCongeMapper;
    private final SoldeCongeRepository soldeCongeRepository;

    public DemandeCongeService(
        WorkflowService workflowService,
        DemandeCongeRepository demandeCongeRepository,
        DemandeCongeMapper demandeCongeMapper,
        SoldeCongeRepository soldeCongeRepository
    ) {
        this.workflowService = workflowService;
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
        boolean exist = demandeCongeRepository.existsByUserLoginAndVld(demandeCongeDTO.getUser(), 0);
        SoldeConge soldeConge = soldeCongeRepository
            .findByYearYearAndUserLogin(Calendar.getInstance().getWeekYear(), demandeCongeDTO.getUser())
            .orElse(null);
        if (soldeConge != null && !exist) {
            long nbJours = (demandeCongeDTO.getDateFin().getTime() - demandeCongeDTO.getDateDebut().getTime()) / (1000 * 60 * 60 * 24);
            if (soldeConge.getSolde() >= nbJours) {
                DemandeConge demande = demandeCongeMapper.demandeCongeDTOToDemandeConge(demandeCongeDTO);
                HashMap<String, Object> variables = new HashMap<String, Object>();
                // replace ny current matricule
                variables.put("demande", demande);
                variables.put("type", demande.getType().getLibFr());
                ProcessInstance processInstance = workflowService.startProcessById("demande congé", variables);

                return demandeCongeMapper.demandeCongeToDemandeCongeDTO(demande);
            }
        }
        throw new RuntimeException("Solde insuffisant");
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
            demandeConge.setVld(vld);
            demandeCongeRepository.save(demandeConge);
        }
    }

    public List<SoldeCongeDTO> getSoldeCongeByUser(String login, Pageable pageable) {
        log.debug("Request to get SoldeConge by user : {}", login);
        return soldeCongeRepository.findByUserLogin(login, pageable).stream().map(SoldeCongeDTO::new).collect(Collectors.toList());
    }

    public boolean check(LocalDate dateDebut, LocalDate dateFin) {
        String login = SecurityUtils.getCurrentUserLogin().get();

        long days = (dateFin.toEpochDay() - dateDebut.toEpochDay());
        int year = dateDebut.getYear();
        SoldeConge soldeConge = soldeCongeRepository.findByYearYearAndUserLogin(year, login).orElse(null);
        return soldeConge != null && soldeConge.getSolde() >= days && !demandeCongeRepository.existsByUserLoginAndVld(login, 0);
    }
}
