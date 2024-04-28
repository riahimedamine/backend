package com.siga.ecp.tn.repository;

import com.siga.ecp.tn.domain.DemandeConge;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DemandeConge entity.
 */
@Repository
public interface DemandeCongeRepository extends JpaRepository<DemandeConge, Long> {
    @Query("select demandeConge from DemandeConge demandeConge where demandeConge.user.login = ?#{authentication.name}")
    List<DemandeConge> findByUserIsCurrentUser();

    List<DemandeConge> findByVld(Integer state);

    List<DemandeConge> findByUserId(Long id);

    List<DemandeConge> findByUserIdAndVld(Long id, Integer state);

    List<DemandeConge> findByUserLogin(String login);

    List<DemandeConge> findByUserLoginAndVld(String login, Integer state);

    Boolean existsByUserLoginAndVld(String login, Integer state);
}
