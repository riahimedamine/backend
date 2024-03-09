package com.siga.ecp.tn.repository;

import com.siga.ecp.tn.domain.SoldeConge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the SoldeConge entity.
 */
@Repository
public interface SoldeCongeRepository extends JpaRepository<SoldeConge, Long> {
    Optional<SoldeConge> findByYearAndUserLogin(Integer year, String login);

    List<SoldeConge> findByYear(Integer year);

    List<SoldeConge> findByUserLogin(String login);
}
