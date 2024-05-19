package com.siga.ecp.tn.service;

import com.siga.ecp.tn.domain.Year;
import com.siga.ecp.tn.repository.YearRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class YearService {

    private final YearRepository yearRepository;

    public YearService(YearRepository yearRepository) {
        this.yearRepository = yearRepository;
    }

    public Year add(Year year) {
        return yearRepository.save(year);
    }

    public void delete(Long id) {
        yearRepository.deleteById(id);
    }

    public Page<Year> findAll(Pageable pageable) {
        return yearRepository.findAll(pageable);
    }

    public Year findById(Long id) {
        return yearRepository.findById(id).orElse(null);
    }

    public Year update(Year year) {
        return yearRepository.save(year);
    }

    public List<Integer> getYearsList() {
        return yearRepository.findAll().stream().map(Year::getYear).collect(Collectors.toList());
    }
}
