package com.siga.ecp.tn.web.rest;

import com.siga.ecp.tn.domain.Year;
import com.siga.ecp.tn.service.YearService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/years")
public class YearController {

    private final YearService yearService;

    public YearController(YearService yearService) {
        this.yearService = yearService;
    }

    @GetMapping("/all")
    public List<Integer> getYears() {
        return yearService.getYears();
    }

    @GetMapping("")
    public List<Year> getYear() {
        return yearService.findAll();
    }

    @PostMapping("")
    public Year addYear(@RequestBody Year year) {
        return yearService.add(year);
    }

    @PutMapping("")
    public Year updateYear(@RequestBody Year year) {
        return yearService.update(year);
    }

    @DeleteMapping("/{id}")
    public void deleteYear(@PathVariable Long id) {
        yearService.delete(id);
    }

    @GetMapping("/{id}")
    public Year getYearById(@PathVariable Long id) {
        return yearService.findById(id);
    }
}
