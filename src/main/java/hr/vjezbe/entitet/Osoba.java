package hr.vjezbe.entitet;

import java.io.Serializable;

/**
 * Klasa koju nasljeduju sve osobe u programu (student, profesor)
 * sadrzi podatke o imenu i prezimenu
 */
public abstract class Osoba implements Serializable {

    private String ime;
    private String prezime;

    /**
     *
     * @param ime - ime osobe
     * @param prezime - prezime osobe
     */
    public Osoba(String ime, String prezime) {
        this.ime = ime;
        this.prezime = prezime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
}
