package com.siga.ecp.tn.service.mapper;

import com.siga.ecp.tn.domain.CongeStatistic;
import com.siga.ecp.tn.domain.TypeWithCount;
import com.siga.ecp.tn.service.dto.CongeStatisticDTO;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CongeStatisticMapper {

    public CongeStatisticDTO toDto(CongeStatistic statistic) {
        CongeStatisticDTO dto = new CongeStatisticDTO();
        dto.setId(statistic.getId());
        dto.setYear(statistic.getYear().getYear());
        dto.setMonth(statistic.getMonth());

        Map<String, Integer> typesWithCountsMap = statistic.getTypesWithCounts().stream()
            .collect(
                Collectors.toMap(
                    (typesWithCounts -> typesWithCounts.getType().getLibFr()),
                    TypeWithCount::getCount
                )
            );

        dto.setTypesWithCounts(typesWithCountsMap);

        return dto;
    }
}
