package hr.vjezbe.entitet;

import hr.vjezbe.baza.BazaPodataka;
import hr.vjezbe.iznimke.BazaPodatakaException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Klasa koja se koristi za predmete na odredenom studiju
 * Sadrzi podatke o sifri predmeta, nazivu, broju ECTS bodova, nositelju i studentima
 */
public class Predmet implements Serializable {

    private Long id;
    private String sifra;
    private String naziv;
    private Integer brojEctsBodova;
    private Long profesorId;

    private Set<Student> studenti = new HashSet<>();

    /**
     *
     * @param sifra - sifra predmeta
     * @param naziv - naziv predemeta
     * @param brojEctsBodova - broj ECTS bodova predmeta
     */
    public Predmet(Long id, String sifra, String naziv, Integer brojEctsBodova, Long profesorId) {
        this.id = id;
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.profesorId = profesorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profesor getNositelj(){
        List<Profesor> profesori = new ArrayList<>();
        Profesor p = null;
        try {
            profesori = BazaPodataka.dohvatiSveProfesore();
        } catch (BazaPodatakaException e) {
            System.out.println("Greška baze podataka!");;
        }
        for (Profesor profesor : profesori) {
            if (profesor.getId() == profesorId) p = profesor;
        }
        return p;
    }

    public void setStudenti(Set<Student> studenti) {
        this.studenti = studenti;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getBrojEctsBodova() {
        return brojEctsBodova;
    }

    public void setBrojEctsBodova(Integer brojEctsBodova) {
        this.brojEctsBodova = brojEctsBodova;
    }

    public Long getProfesorId(){
        return this.profesorId;
    }


    public Set<Student> getStudenti(){
        return studenti;
    }

    @Override
    public String toString() {
        return getNaziv();
    }
}
