package com.siga.ecp.tn.service.dto;

import com.siga.ecp.tn.domain.SoldeConge;
import java.time.Instant;
import java.util.Objects;

public class SoldeCongeDTO {

    private Long id;
    private Integer solde;
    private Integer year;
    private String user;
    private String createdBy;
    private String lastModifiedBy;
    private Instant createdDate;
    private Instant lastModifiedDate;

    public SoldeCongeDTO() {}

    public SoldeCongeDTO(SoldeConge soldeConge) {
        this.id = soldeConge.getId();
        this.solde = soldeConge.getSolde();
        this.year = soldeConge.getYear().getYear();
        this.user = soldeConge.getUser().getLogin();
        this.createdBy = soldeConge.getCreatedBy();
        this.lastModifiedBy = soldeConge.getLastModifiedBy();
        this.createdDate = soldeConge.getCreatedDate();
        this.lastModifiedDate = soldeConge.getLastModifiedDate();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SoldeCongeDTO that = (SoldeCongeDTO) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(solde, that.solde)) return false;
        if (!Objects.equals(year, that.year)) return false;
        if (!Objects.equals(user, that.user)) return false;
        if (!Objects.equals(createdBy, that.createdBy)) return false;
        if (!Objects.equals(lastModifiedBy, that.lastModifiedBy)) return false;
        if (!Objects.equals(createdDate, that.createdDate)) return false;
        return Objects.equals(lastModifiedDate, that.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (solde != null ? solde.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (lastModifiedBy != null ? lastModifiedBy.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return (
            "SoldeCongeDTO{" +
            "id=" +
            id +
            ", solde=" +
            solde +
            ", year=" +
            year +
            ", user='" +
            user +
            '\'' +
            ", createdBy='" +
            createdBy +
            '\'' +
            ", LastModifiedBy='" +
            lastModifiedBy +
            '\'' +
            ", createdDate=" +
            createdDate +
            ", lastModifiedDate=" +
            lastModifiedDate +
            '}'
        );
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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
}
