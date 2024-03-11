package com.siga.ecp.tn.service.dto;

import com.siga.ecp.tn.domain.SoldeConge;

public class SoldeCongeDTO {
    private Long id;
    private Integer solde;
    private Integer year;
    private String user;
    public SoldeCongeDTO() {
    }
    public SoldeCongeDTO(SoldeConge soldeConge) {
        this.id = soldeConge.getId();
        this.solde = soldeConge.getSolde();
        this.year = soldeConge.getYear();
        this.user = soldeConge.getUser().getLogin();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSolde() {
        return solde;
    }

    public void setSolde(Integer solde) {
        this.solde = solde;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "SoldeCongeDTO{" +
            "solde=" + solde +
            ", year=" + year +
            ", user='" + user + '\'' +
            '}';
    }
}
