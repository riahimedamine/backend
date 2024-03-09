package com.siga.ecp.tn.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DemandeConge.
 */
@Entity
@Table(name = "demande_conge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DemandeConge extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_debut")
    private Instant dateDebut;

    @Column(name = "date_fin")
    private Instant dateFin;

    @Column(name = "state")
    private String state;

    @Column(name = "notes")
    private String notes;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = TypeConge.class)
    @JoinColumn(name = "type", referencedColumnName = "code")
    private TypeConge type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", referencedColumnName = "login")
    private User user;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public DemandeConge state(String state) {
        this.setState(state);
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public DemandeConge notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DemandeConge id(Long id) {
        this.setId(id);
        return this;
    }

    public Instant getDateDebut() {
        return this.dateDebut;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public DemandeConge dateDebut(Instant dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public Instant getDateFin() {
        return this.dateFin;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public DemandeConge dateFin(Instant dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public TypeConge getType() {
        return this.type;
    }

    public void setType(TypeConge typeConge) {
        this.type = typeConge;
    }

    public DemandeConge type(TypeConge typeConge) {
        this.setType(typeConge);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DemandeConge userId(User user) {
        this.setUser(user);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeConge)) {
            return false;
        }
        return getId() != null && getId().equals(((DemandeConge) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeConge{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            "}";
    }
}
