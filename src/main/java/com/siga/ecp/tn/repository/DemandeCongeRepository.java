package com.siga.ecp.tn.repository;

import com.siga.ecp.tn.domain.DemandeConge;
import com.siga.ecp.tn.domain.TypeConge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Spring Data JPA repository for the DemandeConge entity.
 */
@Repository
public interface DemandeCongeRepository extends JpaRepository<DemandeConge, Long> {

    Integer countByDateDebutBetween(Date startDate, Date endDate);

    /**
     * Count the number of requests between two dates and with a specific state.
     *
     * @param startDate
     * @param endDate
     * @param state
     * @return the number of requests
     */
    Integer countByDateDebutBetweenAndVld(Date startDate, Date endDate, Integer state);

    Integer countByTypeAndDateDebutBetween(TypeConge type, Date startDate, Date endDate);

    Boolean existsByUserLoginAndVld(String login, Integer state);

    @Query("select demandeConge from DemandeConge demandeConge where demandeConge.user.login = ?#{authentication.name} order by demandeConge.createdDate desc")
    Page<DemandeConge> findByUserIsCurrentUser(Pageable pageable);

    Page<DemandeConge> findByUserLoginOrderByDateDebutDesc(String login, Pageable pageable);
}
