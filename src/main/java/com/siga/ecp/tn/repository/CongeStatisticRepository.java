package com.siga.ecp.tn.repository;

import com.siga.ecp.tn.domain.CongeStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A repository for managing {@link CongeStatistic}.
 */
@Repository
public interface CongeStatisticRepository extends JpaRepository<CongeStatistic, Long> {

    void deleteByYearYearAndMonth(int monthYear, int month);

    List<CongeStatistic> findByYearYearAndMonth(int year, int month);

    List<CongeStatistic> findByYearYearOrderByMonthAsc(int year);
}
