package com.siga.ecp.tn.repository;

import com.siga.ecp.tn.domain.SoldeConge;
import com.siga.ecp.tn.domain.Year;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SoldeConge entity.
 */
@Repository
public interface SoldeCongeRepository extends JpaRepository<SoldeConge, Long> {
    Optional<SoldeConge> findByYearYearAndUserLogin(int year, String login);

    List<SoldeConge> findByYear(Integer year);

    Page<SoldeConge> findByUserLogin(String login, Pageable pageable);

    void deleteByUserLogin(String login);
}
