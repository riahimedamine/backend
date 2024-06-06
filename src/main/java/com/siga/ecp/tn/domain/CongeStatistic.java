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
     * The list of the top types of leave requests with their counts.
     */
    @OneToMany(mappedBy = "congeStatistic", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeWithCount> typesWithCounts;

    public CongeStatistic() {
        // Empty constructor needed for Jackson.
    }

    public CongeStatistic(Year year, Integer month, List<TypeWithCount> typesWithCounts) {
        this.year = year;
        this.month = month;
        this.typesWithCounts = typesWithCounts;
    }

    public CongeStatistic(Year year, Integer month) {
        this.year = year;
        this.month = month;
    }

    @Override
    public String toString() {
        return "CongeStatistic{" +
            "id=" + id +
            ", year=" + year +
            ", month=" + month +
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

