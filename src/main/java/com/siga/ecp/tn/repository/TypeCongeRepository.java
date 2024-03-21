package com.siga.ecp.tn.repository;

import com.siga.ecp.tn.domain.TypeConge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeConge entity.
 */
@Repository
public interface TypeCongeRepository extends JpaRepository<TypeConge, Long> {
    TypeConge findByCode(Integer code);

    List<TypeConge> findAllByisDeletedFalse();
}
