package com.siga.ecp.tn.repository;

import com.siga.ecp.tn.domain.DemandeConge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the DemandeConge entity.
 */
@Repository
public interface DemandeCongeRepository extends JpaRepository<DemandeConge, Long> {

    Boolean existsByUserLoginAndVld(String login, Integer state);

    DemandeConge findByProcessInstanceId(String processInstanceId);

    @Query("select demandeConge from DemandeConge demandeConge where demandeConge.user.login = ?#{authentication.name}")
    List<DemandeConge> findByUserIsCurrentUserOrderByDateDebutDesc();

    List<DemandeConge> findByUserLoginOrderByDateDebutDesc(String login);
}
