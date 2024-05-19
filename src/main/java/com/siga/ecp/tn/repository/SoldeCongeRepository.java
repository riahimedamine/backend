package com.siga.ecp.tn.repository;

import com.siga.ecp.tn.domain.SoldeConge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the SoldeConge entity.
 */
@Repository
public interface SoldeCongeRepository extends JpaRepository<SoldeConge, Long> {

    Page<SoldeConge> findByUserLogin(String login, Pageable pageable);

    Optional<SoldeConge> findByYearYearAndUserLogin(int year, String login);
}
