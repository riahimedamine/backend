package com.siga.ecp.tn.service.dto;

import java.util.Map;

public class CongeStatisticDTO {
    private Long id;
    private Integer year;
    private int month;
    private int totalDemandes;
    private int totalAcceptedDemandes;
    private int totalRefusedDemandes;
    private Map<String, Integer> typesWithCounts;

    public CongeStatisticDTO() {
        // Empty constructor needed for Jackson.
    }

    @Override
    public String toString() {
        return "CongeStatisticDTO{" +
            "id=" + id +
            ", yearId=" + year +
            ", month=" + month +
            ", topTypesWithCounts=" + typesWithCounts +
            '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getTotalAcceptedDemandes() {
        return totalAcceptedDemandes;
    }

    public void setTotalAcceptedDemandes(int totalAcceptedDemandes) {
        this.totalAcceptedDemandes = totalAcceptedDemandes;
    }

    public int getTotalDemandes() {
        return totalDemandes;
    }

    public void setTotalDemandes(int totalDemandes) {
        this.totalDemandes = totalDemandes;
    }

    public int getTotalRefusedDemandes() {
        return totalRefusedDemandes;
    }

    public void setTotalRefusedDemandes(int totalRefusedDemandes) {
        this.totalRefusedDemandes = totalRefusedDemandes;
    }

    public Map<String, Integer> getTypesWithCounts() {
        return typesWithCounts;
    }

    public void setTypesWithCounts(Map<String, Integer> typesWithCounts) {
        this.typesWithCounts = typesWithCounts;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
