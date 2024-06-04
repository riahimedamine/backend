package com.siga.ecp.tn.repository;

import com.siga.ecp.tn.domain.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface YearRepository extends JpaRepository<Year, Long> {
    Optional<Year> findByYear(int year);
}
