package com.siga.ecp.tn.web.rest;

import com.siga.ecp.tn.service.CongeStatisticService;
import com.siga.ecp.tn.service.dto.CongeStatisticDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class CongeStatisticController {

    private final CongeStatisticService congeStatisticService;

    public CongeStatisticController(CongeStatisticService congeStatisticService) {
        this.congeStatisticService = congeStatisticService;
    }

    @GetMapping("/conges/{year}")
    public List<CongeStatisticDTO> getCongeStatisticsByYear(@PathVariable int year) {
        return congeStatisticService.findByYear(year);
    }

    @GetMapping("/conges/{year}/{month}")
    public List<CongeStatisticDTO> getCongeStatisticsByYearAndMonth(@PathVariable int year, @PathVariable int month) {
        return congeStatisticService.findByYearAndMonth(year, month);
    }

    @PutMapping("/conges/refresh")
    public void refreshCongeStatistics() {
        congeStatisticService.refreshStatistics();
    }

    @GetMapping("/conges")
    public List<CongeStatisticDTO> getCongeStatistics() {
        return congeStatisticService.findAll();
    }
}
