package domain;

import java.util.List;

public class Proiect {
    private Integer id;
    private String denumire;

    public Proiect() {}

    public Proiect(String denumire) {
        this.denumire = denumire;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

}
