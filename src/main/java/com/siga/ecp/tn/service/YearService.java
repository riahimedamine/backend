package com.siga.ecp.tn.service;

import com.siga.ecp.tn.domain.Year;
import com.siga.ecp.tn.repository.YearRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing {@link com.siga.ecp.tn.domain.Year}.
 */
@Service
public class YearService {

    private final static Logger log = LoggerFactory.getLogger(YearService.class);

    private final YearRepository yearRepository;

    public YearService(YearRepository yearRepository) {
        this.yearRepository = yearRepository;
    }

    // todo : create checkYearExists method

    /**
     * Save a year.
     *
     * @param year the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Year add(Year year) {
        log.debug("Request to save Year : {}", year);
        yearRepository.findByYear(year.getYear()).ifPresent(y -> {
            throw new RuntimeException("Year already exists");
        });
        return yearRepository.save(year);
    }

    /**
     * Delete the year by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Year : {}", id);
        yearRepository.deleteById(id);
    }

    /**
     * Get all the years.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Year> findAll(Pageable pageable) {
        log.debug("Request to get all Years");
        return yearRepository.findAll(pageable);
    }

    /**
     * Get one year by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Year findById(Long id) {
        log.debug("Request to get Year : {}", id);
        return yearRepository.findById(id).orElse(null);
    }

    /**
     * Update a year.
     *
     * @param year the entity to update.
     * @return the persisted entity.
     */
    @Transactional
    public Year update(Year year) {
        log.debug("Request to update Year : {}", year);
        return yearRepository.save(year);
    }

    /**
     * Get all the years as integer list.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Integer> getYearsList() {
        log.debug("Request to get all Years as integer list");
        return yearRepository.findAll().stream().map(Year::getYear).collect(Collectors.toList());
    }
}
