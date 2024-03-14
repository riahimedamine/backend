package com.siga.ecp.tn.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A TypeConge.
 */
@Entity
@Table(name = "type_conge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class TypeConge extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", unique = true)
    private Integer code;

    @Column(name = "lib_fr")
    private String libFr;

    @Column(name = "lib_ar")
    private String libAr;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeConge id(Long id) {
        this.setId(id);
        return this;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public TypeConge code(Integer code) {
        this.setCode(code);
        return this;
    }

    public String getLibFr() {
        return this.libFr;
    }

    public void setLibFr(String libFr) {
        this.libFr = libFr;
    }

    public TypeConge libFr(String libFr) {
        this.setLibFr(libFr);
        return this;
    }

    public String getLibAr() {
        return this.libAr;
    }

    public void setLibAr(String libAr) {
        this.libAr = libAr;
    }

    public TypeConge libAr(String libAr) {
        this.setLibAr(libAr);
        return this;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public TypeConge isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeConge)) {
            return false;
        }
        return getId() != null && getId().equals(((TypeConge) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeConge{" +
            "id=" + getId() +
            ", code=" + getCode() +
            ", libFr='" + getLibFr() + "'" +
            ", libAr='" + getLibAr() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            "}";
    }
}
