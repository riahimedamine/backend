package com.siga.ecp.tn.web.rest;

import com.siga.ecp.tn.domain.Year;
import com.siga.ecp.tn.security.AuthoritiesConstants;
import com.siga.ecp.tn.service.YearService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link Year}.
 */
@RestController
@RequestMapping("/api/years")
@Tag(name = "Years", description = "Years management")
public class YearController {

    private final YearService yearService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public YearController(YearService yearService) {
        this.yearService = yearService;
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Year> addYear(@RequestBody Year year) throws URISyntaxException {
        Year newYear = yearService.add(year);
        return ResponseEntity
            .created(new URI("/api/years/" + year.getId()))
            .headers(HeaderUtil.createAlert(applicationName, "yearManagement.created", String.valueOf(newYear.getYear())))
            .body(year);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.RH + "\")")
    public void deleteYear(@PathVariable Long id) {
        yearService.delete(id);
    }

    @GetMapping("/{id}")
    public Year getYearById(@PathVariable Long id) {
        return yearService.findById(id);
    }

    @GetMapping("")
    public ResponseEntity<List<Year>> getYears(Pageable pageable) {
        final Page<Year> page = yearService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @PutMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.RH + "\")")
    public ResponseEntity<Year> updateYear(@RequestBody Year year) {
        Year newYear = yearService.update(year);
        HttpHeaders header = HeaderUtil.createAlert(applicationName, "yearManagement.updated", newYear.getId().toString());
        return new ResponseEntity<>(newYear, header, HttpStatus.OK);
    }

    /**
     * {@code GET  /years} : get all the years.
     *
     * @return the list of years with status 200 (OK)
     */
    @GetMapping("/all")
    public List<Integer> getYearsList() {
        return yearService.getYearsList();
    }
}
