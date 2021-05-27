package domain;

import javax.persistence.criteria.CriteriaBuilder;

public class Bug {
    private Integer id;
    private String descriere;
    private Status status;
    private GradUrgenta gradUrgenta;
    private Proiect proiect;
    private String numeProgramator;

    public Bug() {}

    public Bug(String descriere, Status status, GradUrgenta gradUrgenta, Proiect proiect) {
        this.descriere = descriere;
        this.status = status;
        this.gradUrgenta = gradUrgenta;
        this.proiect = proiect;
        this.numeProgramator = null;
    }

    public String getNumeProgramator() {
        return numeProgramator;
    }

    public void setNumeProgramator(String numeProgramator) {
        this.numeProgramator = numeProgramator;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Proiect getProiect() {
        return proiect;
    }

    public void setProiect(Proiect proiect) {
        this.proiect = proiect;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public GradUrgenta getGradUrgenta() {
        return gradUrgenta;
    }

    public void setGradUrgenta(GradUrgenta gradUrgenta) {
        this.gradUrgenta = gradUrgenta;
    }
}
