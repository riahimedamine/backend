package com.siga.ecp.tn.repository;

import com.siga.ecp.tn.domain.TypeConge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TypeConge entity.
 */
@Repository
public interface TypeCongeRepository extends JpaRepository<TypeConge, Long> {
    TypeConge findByCode(Integer code);

    Page<TypeConge> findAllByisDeletedFalse(Pageable pageable);

    TypeConge findByLibFr(String libFr);
}
