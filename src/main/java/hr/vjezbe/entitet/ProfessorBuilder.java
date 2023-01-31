package hr.vjezbe.entitet;

import hr.vjezbe.baza.BazaPodataka;
import hr.vjezbe.iznimke.BazaPodatakaException;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;

/**
 * ProfesorBuilder klasa za klasu Profesor
 */
public class ProfessorBuilder{
    private String sifra;
    private String titula;
    private String ime;
    private String prezime;

    public ProfessorBuilder setSifra(String sifra) {
        this.sifra = sifra;
        return this;
    }
    public ProfessorBuilder setTitula(String titula){
        this.titula = titula;
        return this;
    }

    public ProfessorBuilder setIme(String ime) {
        this.ime = ime;
        return this;
    }

    public ProfessorBuilder setPrezime(String prezime) {
        this.prezime = prezime;
        return this;
    }

    public Profesor getProfesor(){
        List<Profesor> profesori = new ArrayList<>();
        try {
            profesori = BazaPodataka.dohvatiSveProfesore();
        } catch (BazaPodatakaException e) {
            System.out.println("Greska baze podataka!");
        }
        OptionalLong maxId = profesori.stream()
                .mapToLong(profesor -> profesor.getId()).max();
        return new Profesor(maxId.getAsLong(), sifra, ime, prezime, titula);
    }
}
