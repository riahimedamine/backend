package com.siga.ecp.tn.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/**
 * A DemandeConge.
 */
@Entity
@Table(name = "demande_conge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class DemandeConge extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @Column(name = "vld")
    private Integer vld; //-1: invalid, 0: pending, 1: valid1, 2: validFinal

    @Column(name = "notes")
    private String notes;

    @Column(name = "telephone")
    private Long telephone;

    @Column(name = "address")
    private String address;

    @Column(name = "process_instance_id")
    private String processInstanceId;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = TypeConge.class)
    @JoinColumn(name = "type", referencedColumnName = "code")
    private TypeConge type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", referencedColumnName = "login")
    private User user;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public String toString() {
        return (
            "DemandeConge{" +
            "id=" +
            id +
            ", dateDebut=" +
            dateDebut +
            ", dateFin=" +
            dateFin +
            ", vld='" +
            vld +
            '\'' +
            ", notes='" +
            notes +
            '\'' +
            ", telephone=" +
            telephone +
            ", address='" +
            address +
            '\'' +
            ", type=" +
            type +
            ", user=" +
            user +
            '}'
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DemandeConge that = (DemandeConge) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(dateDebut, that.dateDebut)) return false;
        if (!Objects.equals(dateFin, that.dateFin)) return false;
        if (!Objects.equals(vld, that.vld)) return false;
        if (!Objects.equals(notes, that.notes)) return false;
        if (!Objects.equals(telephone, that.telephone)) return false;
        if (!Objects.equals(address, that.address)) return false;
        if (!Objects.equals(type, that.type)) return false;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dateDebut != null ? dateDebut.hashCode() : 0);
        result = 31 * result + (dateFin != null ? dateFin.hashCode() : 0);
        result = 31 * result + (vld != null ? vld.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getVld() {
        return vld;
    }

    public void setVld(Integer vld) {
        this.vld = vld;
    }

    public DemandeConge vld(Integer vld) {
        this.setVld(vld);
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

    public Date getDateDebut() {
        return this.dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public DemandeConge dateDebut(Date dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public Date getDateFin() {
        return this.dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public DemandeConge dateFin(Date dateFin) {
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

    public DemandeConge user(User user) {
        this.setUser(user);
        return this;
    }
}
