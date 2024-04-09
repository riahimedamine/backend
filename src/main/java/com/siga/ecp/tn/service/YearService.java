package com.siga.ecp.tn.service;

import com.siga.ecp.tn.domain.Year;
import com.siga.ecp.tn.repository.YearRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class YearService {

    private final YearRepository yearRepository;

    public YearService(YearRepository yearRepository) {
        this.yearRepository = yearRepository;
    }

    public Year add(Year year) {
        return yearRepository.save(year);
    }

    public Year update(Year year) {
        return yearRepository.save(year);
    }

    public void delete(Long id) {
        yearRepository.deleteById(id);
    }

    public Year findById(Long id) {
        return yearRepository.findById(id).orElse(null);
    }

    public List<Year> findAll() {
        return yearRepository.findAll();
    }

    public List<Integer> getYears() {
        return yearRepository.findAll().stream().map(Year::getYear).collect(Collectors.toList());
    }
}
