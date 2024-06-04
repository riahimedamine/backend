package com.siga.ecp.tn.repository;

import com.siga.ecp.tn.domain.DemandeConge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DemandeConge entity.
 */
@Repository
public interface DemandeCongeRepository extends JpaRepository<DemandeConge, Long> {

    Boolean existsByUserLoginAndVld(String login, Integer state);

    DemandeConge findByProcessInstanceId(String processInstanceId);

    @Query("select demandeConge from DemandeConge demandeConge where demandeConge.user.login = ?#{authentication.name} order by demandeConge.createdDate desc")
    Page<DemandeConge> findByUserIsCurrentUser(Pageable pageable);

    Page<DemandeConge> findByUserLoginOrderByDateDebutDesc(String login, Pageable pageable);
}
