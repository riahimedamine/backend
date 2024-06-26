package com.siga.ecp.tn.service;

import com.siga.ecp.tn.domain.DemandeConge;
import com.siga.ecp.tn.domain.SoldeConge;
import com.siga.ecp.tn.domain.User;
import com.siga.ecp.tn.repository.DemandeCongeRepository;
import com.siga.ecp.tn.repository.SoldeCongeRepository;
import com.siga.ecp.tn.security.SecurityUtils;
import com.siga.ecp.tn.service.dto.DemandeCongeDTO;
import com.siga.ecp.tn.service.dto.DemandeCongeError;
import com.siga.ecp.tn.service.dto.SoldeCongeDTO;
import com.siga.ecp.tn.service.mapper.DemandeCongeMapper;
import com.siga.ecp.tn.service.workflow.NotificationService;
import com.siga.ecp.tn.service.workflow.WorkflowService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
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

    private final WorkflowService workflowService;

    private final NotificationService notificationService;

    private final String PROCESS_NAME = "demande congé";

    public DemandeCongeService(
        WorkflowService workflowService,
        DemandeCongeRepository demandeCongeRepository,
        DemandeCongeMapper demandeCongeMapper,
        SoldeCongeRepository soldeCongeRepository, NotificationService notificationService
    ) {
        this.workflowService = workflowService;
        this.demandeCongeRepository = demandeCongeRepository;
        this.demandeCongeMapper = demandeCongeMapper;
        this.soldeCongeRepository = soldeCongeRepository;
        this.notificationService = notificationService;
    }

    /**
     * Check if the user has already a pending demand or if the user has enough days in his balance.
     *
     * @param dateDebut the start date of the demand.
     * @param dateFin   the end date of the demand.
     * @return the error object.
     */
    @Transactional(readOnly = true)
    public DemandeCongeError check(LocalDate dateDebut, LocalDate dateFin, boolean isUpdate) {
        String login = SecurityUtils.getCurrentUserLogin().orElse(null);

        long days = (dateFin.toEpochDay() - dateDebut.toEpochDay());
        int year = dateDebut.getYear();
        SoldeConge soldeConge = soldeCongeRepository.findByYearYearAndUserLogin(year, login).orElse(null);
        return new DemandeCongeError(
            demandeCongeRepository.existsByUserLoginAndVld(login, 0) && !isUpdate,
            !(soldeConge != null && soldeConge.getSolde() >= days)
        );
    }

    // todo create isValidated method

    /**
     * Delete the demandeConge by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete DemandeConge : {}", id);
        DemandeConge demandeConge = demandeCongeRepository.findById(id).orElse(null);
        if (demandeConge == null) {
            return;
        }
        if (demandeConge.getVld() != 0) {
            throw new RuntimeException("Demande déjà validée");
        }
        workflowService.deleteProcessInstance(demandeConge.getProcessInstanceId(), "Demande de congé annulée");
        demandeCongeRepository.deleteById(id);
    }

    /**
     * Get all the demandeConges.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DemandeCongeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeConges");
        return demandeCongeRepository.findAll(pageable).map(DemandeCongeDTO::new);
    }

    /**
     * Get all the demandeConges by current user.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DemandeCongeDTO> findByCurrentUser(Pageable pageable) {
        log.debug("Request to get DemandeConge by current user");
        return demandeCongeRepository.findByUserIsCurrentUser(pageable).map(DemandeCongeDTO::new);
    }

    /**
     * Get all the demandeConges by Rh.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DemandeCongeDTO> findByRh() {
        log.debug("Request to get DemandeConge by Rh");
        return notificationService.getAllNotificationByCandidateGroup("RH").stream()
            .map(notification ->
                demandeCongeRepository.findById(notification.getDemande().getId())
                    .flatMap(
                        demande -> {
                            DemandeCongeDTO dto = demandeCongeMapper.demandeCongeToDemandeCongeDTO(demande);
                            dto.setTaskId(notification.getTaskId());
                            return Optional.of(dto);
                        }
                    )
                    .orElse(null)
            )
            .collect(Collectors.toList());
    }

    /**
     * Get all the demandeConges by user.
     *
     * @param login the login of the user.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DemandeCongeDTO> findByUser(String login, Pageable pageable) {
        log.debug("Request to get DemandeConge by user : {}", login);
        return demandeCongeRepository.findByUserLoginOrderByDateDebutDesc(login, pageable).map(DemandeCongeDTO::new);
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
     * Get all the soldeConges by user.
     *
     * @param login    the login of the user.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SoldeCongeDTO> getSoldeCongeByUser(String login, Pageable pageable) {
        log.debug("Request to get SoldeConge by user : {}", login);
        return soldeCongeRepository.findByUserLogin(login, pageable).map(SoldeCongeDTO::new);
    }

    /**
     * Partially update a demandeConge.
     *
     * @param demandeCongeDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Transactional
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
    @Transactional
    public DemandeCongeDTO saveDemandeConge(DemandeCongeDTO demandeCongeDTO) {
        log.debug("Request to save DemandeConge : {}", demandeCongeDTO);

        boolean autoComplete = false;
        boolean exist = demandeCongeRepository.existsByUserLoginAndVld(demandeCongeDTO.getUser(), 0);
        SoldeConge soldeConge = soldeCongeRepository
            .findByYearYearAndUserLogin(Calendar.getInstance().getWeekYear(), demandeCongeDTO.getUser())
            .orElse(null);
        if (soldeConge != null && !exist) {
            long nbJours = (demandeCongeDTO.getDateFin().getTime() - demandeCongeDTO.getDateDebut().getTime()) / (1000 * 60 * 60 * 24);
            if (soldeConge.getSolde() >= nbJours) {
                DemandeConge demande = demandeCongeRepository.save(demandeCongeMapper.demandeCongeDTOToDemandeConge(demandeCongeDTO));
                HashMap<String, Object> variables = new HashMap<>();
                variables.put("demande", demande);
                variables.put("type", demande.getType().getLibFr());
                User validator = demande.getUser().getValidator();
                if (validator != null) {
                    variables.put("assignee", validator.getLogin());
                } else {
                    variables.put("assignee", "RH");
                    autoComplete = true;
                }
                ProcessInstance processInstance = workflowService.startProcessById(PROCESS_NAME, variables);
                demande.setProcessInstanceId(processInstance.getId());
                demandeCongeRepository.save(demande);

                if (autoComplete) {
                    notificationService.completeAutoTask(PROCESS_NAME);
                }

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
    @Transactional
    public DemandeCongeDTO updateDemandeConge(DemandeCongeDTO demandeCongeDTO) {
        log.debug("Request to update DemandeConge : {}", demandeCongeDTO);
        if (demandeCongeRepository.findById(demandeCongeDTO.getId()).get().getVld() != 0) {
            throw new RuntimeException("Demande déjà validée");
        }

        return demandeCongeMapper.demandeCongeToDemandeCongeDTO(
            demandeCongeRepository.save(demandeCongeMapper.demandeCongeDTOToDemandeConge(demandeCongeDTO))
        );
    }

    /**
     * Validate a demandeConge.
     *
     * @param id  the id of the entity.
     * @param vld the validation state.
     */
    @Transactional
    public void validateDemandeConge(Long id, int vld) {
        log.debug("Request to validate DemandeConge : {}", id);
        DemandeConge demandeConge = demandeCongeRepository.findById(id).orElse(null);
        if (demandeConge != null) {
            demandeConge.setVld(vld);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(demandeConge.getDateDebut());
            int year = calendar.get(Calendar.YEAR);
            long nbJours = (demandeConge.getDateFin().getTime() - demandeConge.getDateDebut().getTime()) / (1000 * 60 * 60 * 24);

            if (vld == 1) {
                demandeConge.setVld(1);
                soldeCongeRepository
                    .findByYearYearAndUserLogin(year, demandeConge.getUser().getLogin())
                    .ifPresent(soldeConge -> {
                        soldeConge.setSolde((int) (soldeConge.getSolde() - nbJours));
                        soldeCongeRepository.save(soldeConge);
                        demandeCongeRepository.save(demandeConge);
                    });
            } else {
                demandeConge.setVld(-1);
                demandeCongeRepository.save(demandeConge);
            }
        }
    }

    /**
     * Get all the demandeConges by current validator.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DemandeCongeDTO> getDemandeCongeByCurrentValidator() {
        log.debug("Request to get DemandeConge by current validator");
        return notificationService.getAllNotification().stream()
            .map(notification ->
                demandeCongeRepository.findById(notification.getDemande().getId())
                    .flatMap(
                        demande -> {
                            DemandeCongeDTO dto = demandeCongeMapper.demandeCongeToDemandeCongeDTO(demande);
                            dto.setTaskId(notification.getTaskId());
                            return Optional.of(dto);
                        }
                    )
                    .orElse(null)
            )
            .collect(Collectors.toList());
    }
}
