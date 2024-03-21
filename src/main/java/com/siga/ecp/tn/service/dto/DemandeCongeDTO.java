package com.siga.ecp.tn.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.siga.ecp.tn.domain.DemandeConge;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A DTO representing a demande de conge.
 */
public class DemandeCongeDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    private Integer vld; //0 en cours //1 validé //2 refusé
    private String notes;
    private Integer telephone;
    private String address;
    private Integer type;
    private String user;

    public DemandeCongeDTO(DemandeConge demandeConge) {
        this.id = demandeConge.getId();
        this.dateDebut = demandeConge.getDateDebut();
        this.dateFin = demandeConge.getDateFin();
        this.vld = demandeConge.getVld();
        this.notes = demandeConge.getNotes();
        this.telephone = demandeConge.getTelephone();
        this.address = demandeConge.getAddress();
        if (demandeConge.getType() != null) {
            this.type = demandeConge.getType().getCode();
        }
        if (demandeConge.getUser() != null) {
            this.user = demandeConge.getUser().getLogin();
        }
    }

    public DemandeCongeDTO() {
        // Empty constructor needed for Jackson.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DemandeCongeDTO that = (DemandeCongeDTO) o;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Integer getVld() {
        return vld;
    }

    public void setVld(Integer vld) {
        this.vld = vld;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "DemandeCongeDTO{" +
            "id=" + id +
            ", dateDebut=" + dateDebut +
            ", dateFin=" + dateFin +
            ", vld=" + vld +
            ", notes='" + notes + '\'' +
            ", telephone=" + telephone +
            ", address='" + address + '\'' +
            ", type=" + type +
            ", user='" + user + '\'' +
            '}';
    }
}
