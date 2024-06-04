package com.siga.ecp.tn.web.rest.vm;

import java.time.LocalDate;

public class Demande {
    public LocalDate dateDebut;
    public LocalDate dateFin;
    public boolean isUpdate;

    public Demande() {
        // Empty constructor needed for Jackson.
    }

    @Override
    public String toString() {
        return "Demande{" +
            "dateDebut=" + dateDebut +
            ", dateFin=" + dateFin +
            ", isUpdate=" + isUpdate +
            '}';
    }
}
