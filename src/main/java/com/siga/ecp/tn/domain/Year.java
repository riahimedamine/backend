package com.siga.ecp.tn.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "year")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Audited
public class Year implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Year{" + "id=" + id + ", year=" + year + '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;

    public Year() {}

    public Year(int year) {
        this.year = year;
    }

    public Year(Long id, int year) {
        this.id = id;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
