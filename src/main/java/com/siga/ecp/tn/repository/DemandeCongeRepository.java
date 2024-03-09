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
    @Query("select demandeConge from DemandeConge demandeConge where demandeConge.user.login = ?#{authentication.name}")
    List<DemandeConge> findByUserIdIsCurrentUser();

    @Query("select demandeConge from DemandeConge demandeConge where demandeConge.user.login = ?#{authentication.name} and demandeConge.state = ?1")
    List<DemandeConge> findByUserIdIsCurrentUserAndState(String state);

    List<DemandeConge> findByState(String state);

    List<DemandeConge> findByUserId(Long id);

    List<DemandeConge> findByUserIdAndState(Long id, String state);

    List<DemandeConge> findByUserLogin(String login);

    List<DemandeConge> findByUserLoginAndState(String login, String state);
}
