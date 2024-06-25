package com.siga.ecp.tn.domain;

import javax.persistence.*;
import java.util.List;

/**
 * A class representing an analytic of a leave request of a month.
 */
@Entity
@Table(name = "conge_statistics", uniqueConstraints = {@UniqueConstraint(columnNames = {"year_id", "month"})})
public class CongeStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The year of the analytic.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "year_id")
    private Year year;

    /**
     * The month of the analytic.
     */
    @Column(name = "month", nullable = false)
    private Integer month;

    /**
     * The total number of leave requests.
     */
    @Column(name = "total_demandes", nullable = false)
    private Integer totalDemandes;

    /**
     * The total number of accepted leave requests.
     */
    @Column(name = "total_accepted_demandes", nullable = false)
    private Integer totalAcceptedDemandes;

    /**
     * The total number of refused leave requests.
     */
    @Column(name = "total_refused_demandes", nullable = false)
    private Integer totalRefusedDemandes;

    /**
     * The list of the top types of leave requests with their counts.
     */
    @OneToMany(mappedBy = "congeStatistic", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeWithCount> typesWithCounts;

    public CongeStatistic() {
        // Empty constructor needed for Jackson.
    }

    public CongeStatistic(Year year, Integer month, Integer totalDemandes, Integer totalAcceptedDemandes, Integer totalRefusedDemandes) {
        this.year = year;
        this.month = month;
        this.totalDemandes = totalDemandes;
        this.totalAcceptedDemandes = totalAcceptedDemandes;
        this.totalRefusedDemandes = totalRefusedDemandes;
    }

    @Override
    public String toString() {
        return "CongeStatistic{" +
            "id=" + id +
            ", year=" + year +
            ", month=" + month +
            ", totalDemandes=" + totalDemandes +
            ", totalAcceptedDemandes=" + totalAcceptedDemandes +
            ", totalRefusedDemandes=" + totalRefusedDemandes +
            ", typesWithCounts=" + typesWithCounts +
            '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getTotalAcceptedDemandes() {
        return totalAcceptedDemandes;
    }

    public void setTotalAcceptedDemandes(Integer totalAcceptedDemandes) {
        this.totalAcceptedDemandes = totalAcceptedDemandes;
    }

    public Integer getTotalDemandes() {
        return totalDemandes;
    }

    public void setTotalDemandes(Integer totalDemandes) {
        this.totalDemandes = totalDemandes;
    }

    public Integer getTotalRefusedDemandes() {
        return totalRefusedDemandes;
    }

    public void setTotalRefusedDemandes(Integer totalRefusedDemandes) {
        this.totalRefusedDemandes = totalRefusedDemandes;
    }

    public List<TypeWithCount> getTypesWithCounts() {
        return typesWithCounts;
    }

    public void setTypesWithCounts(List<TypeWithCount> typeWithCounts) {
        this.typesWithCounts = typeWithCounts;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }
}

