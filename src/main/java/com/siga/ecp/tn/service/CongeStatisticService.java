package com.siga.ecp.tn.service;

import com.siga.ecp.tn.domain.CongeStatistic;
import com.siga.ecp.tn.domain.TypeConge;
import com.siga.ecp.tn.domain.TypeWithCount;
import com.siga.ecp.tn.domain.Year;
import com.siga.ecp.tn.repository.CongeStatisticRepository;
import com.siga.ecp.tn.repository.DemandeCongeRepository;
import com.siga.ecp.tn.repository.TypeCongeRepository;
import com.siga.ecp.tn.repository.YearRepository;
import com.siga.ecp.tn.service.dto.CongeStatisticDTO;
import com.siga.ecp.tn.service.mapper.CongeStatisticMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CongeStatisticService {

    private static final Logger log = LoggerFactory.getLogger(CongeStatisticService.class);
    private final CongeStatisticRepository congeStatisticRepository;
    private final CongeStatisticMapper congeStatisticMapper;
    private final YearRepository yearRepository;
    private final DemandeCongeRepository demandeCongeRepository;
    private final TypeCongeRepository typeCongeRepository;

    public CongeStatisticService(CongeStatisticRepository congeStatisticRepository, CongeStatisticMapper congeStatisticMapper, YearRepository yearRepository, DemandeCongeRepository demandeCongeRepository, TypeCongeRepository typeCongeRepository) {
        this.congeStatisticRepository = congeStatisticRepository;
        this.congeStatisticMapper = congeStatisticMapper;
        this.yearRepository = yearRepository;
        this.demandeCongeRepository = demandeCongeRepository;
        this.typeCongeRepository = typeCongeRepository;
    }

    /**
     * Generates the statistics for the next month.
     */
    @Scheduled(cron = "0 0 0 L * ?")
    @Transactional
    public void autoGenerateMonthlyStatistics() {
        refreshStatistics();

        LocalDate nextMonthStart = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        int nextMonthYear = nextMonthStart.getYear();
        int nextMonth = nextMonthStart.getMonthValue();

        generateMonthlyStatistics(nextMonthYear, nextMonth);
    }

    @Transactional(readOnly = true)
    public List<CongeStatisticDTO> findAll() {
        return congeStatisticRepository.findAll().stream()
            .map(congeStatisticMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CongeStatisticDTO> findByYear(int year) {
        return congeStatisticRepository.findByYearYearOrderByMonthAsc(year).stream()
            .map(congeStatisticMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CongeStatisticDTO> findByYearAndMonth(int year, int month) {
        return congeStatisticRepository.findByYearYearAndMonth(year, month).stream()
            .map(congeStatisticMapper::toDto)
            .collect(Collectors.toList());
    }

    private void generateMonthlyStatistics(int monthYear, int month) {
        Year year = yearRepository.findByYear(monthYear).orElseGet(() -> {
            Year newYear = new Year(monthYear);
            return yearRepository.save(newYear);
        });

        CongeStatistic congeStatistic = congeStatisticRepository.save(new CongeStatistic(year, month));

        congeStatistic.setTypesWithCounts(getTypesWithCounts(congeStatistic));

        congeStatisticRepository.save(congeStatistic);
    }

    private List<TypeWithCount> getTypesWithCounts(CongeStatistic congeStatistic) {

        List<TypeConge> types = typeCongeRepository.findAll();

        Calendar calendar = Calendar.getInstance();

        Integer year = congeStatistic.getYear().getYear();
        Integer month = congeStatistic.getMonth();

        calendar.set(year, month - 1, 1);
        Date startDate = calendar.getTime();

        calendar.set(year, month - 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();

        List<TypeWithCount> typeWithCounts = new ArrayList<>();

        types.forEach(type -> {
            Integer count = demandeCongeRepository.countByTypeAndDateDebutBetween(type, startDate, endDate);
            typeWithCounts.add(new TypeWithCount(type, count, congeStatistic));
        });

        return typeWithCounts;

    }

    /**
     * Refreshes the statistics by deleting all the existing statistics and generating new ones.
     */
    public void refreshStatistics() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        int monthYear = startOfMonth.getYear();
        int month = startOfMonth.getMonthValue();

        congeStatisticRepository.findByYearYearAndMonth(monthYear, month).ifPresent(congeStatisticRepository::delete);

        generateMonthlyStatistics(monthYear, month);
    }
}
