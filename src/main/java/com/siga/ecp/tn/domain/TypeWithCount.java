package com.siga.ecp.tn.domain;

import javax.persistence.*;

@Entity
@Table(name = "types_with_counts")
public class TypeWithCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "type_conge_id")
    private TypeConge type;

    @Column(name = "count", nullable = false)
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conge_statistic_id")
    private CongeStatistic congeStatistic;

    public TypeWithCount() {
        // Empty constructor needed for Jackson.
    }

    public TypeWithCount(TypeConge type, Integer count, CongeStatistic congeStatistic) {
        this.type = type;
        this.count = count;
        this.congeStatistic = congeStatistic;
    }

    @Override
    public String toString() {
        return "TypesWithCounts{" +
            "id=" + id +
            ", type=" + type +
            ", count=" + count +
            ", congeStatistic=" + congeStatistic.getId() +
            '}';
    }

    public CongeStatistic getCongeStatistic() {
        return congeStatistic;
    }

    public void setCongeStatistic(CongeStatistic congeStatistic) {
        this.congeStatistic = congeStatistic;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeConge getType() {
        return type;
    }

    public void setType(TypeConge type) {
        this.type = type;
    }

}
