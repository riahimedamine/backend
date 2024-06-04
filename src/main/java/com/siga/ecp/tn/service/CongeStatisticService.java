package com.siga.ecp.tn.service;

import com.siga.ecp.tn.domain.CongeStatistic;
import com.siga.ecp.tn.domain.TypeConge;
import com.siga.ecp.tn.domain.TypesWithCounts;
import com.siga.ecp.tn.domain.Year;
import com.siga.ecp.tn.repository.CongeStatisticRepository;
import com.siga.ecp.tn.repository.DemandeCongeRepository;
import com.siga.ecp.tn.repository.TypeCongeRepository;
import com.siga.ecp.tn.repository.YearRepository;
import com.siga.ecp.tn.service.dto.CongeStatisticDTO;
import com.siga.ecp.tn.service.mapper.CongeStatisticMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CongeStatisticService {

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
    public void autoGenerateMonthlyStatistics() {
        refreshStatistics();

        LocalDate nextMonthStart = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        int nextMonthYear = nextMonthStart.getYear();
        int nextMonth = nextMonthStart.getMonthValue();

        generateMonthlyStatistics(nextMonthYear, nextMonth);
    }

    public List<CongeStatisticDTO> findAll() {
        return congeStatisticRepository.findAll().stream()
            .map(congeStatisticMapper::toDto)
            .collect(Collectors.toList());
    }

    public List<CongeStatisticDTO> findByYear(int year) {
        return congeStatisticRepository.findByYearYearOrderByMonthAsc(year).stream()
            .map(congeStatisticMapper::toDto)
            .collect(Collectors.toList());
    }

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

        CongeStatistic congeStatistic = new CongeStatistic(year, month, getTypesWithCounts(monthYear, month));
        congeStatisticRepository.save(congeStatistic);
    }

    private List<TypesWithCounts> getTypesWithCounts(Integer year, Integer month) {

        List<TypeConge> types = typeCongeRepository.findAll();

        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, 1);
        Date startDate = calendar.getTime();

        calendar.set(year, month - 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();

        List<TypesWithCounts> typesWithCounts = new ArrayList<>();

        types.forEach(type -> {
            Integer count = demandeCongeRepository.countByTypeAndDateDebutBetween(type, startDate, endDate);
            typesWithCounts.add(new TypesWithCounts(type, count));
        });

        return typesWithCounts;

    }

    /**
     * Refreshes the statistics by deleting all the existing statistics and generating new ones.
     */
    public void refreshStatistics() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        int monthYear = startOfMonth.getYear();
        int month = startOfMonth.getMonthValue();

        congeStatisticRepository.deleteByYearYearAndMonth(monthYear, month);

        generateMonthlyStatistics(monthYear, month);
    }
}
