package hr.vjezbe.entitet;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Student nasljeduje klasu Osoba
 * Takoder sadrzi podatke o JMBAG-u studenta i datumu rodenja
 */
public class Student extends Osoba{

    private Long id;
    private String jmbag;
    private LocalDate datumRodjenja;
    public boolean nedovoljnaOcjenaNaIspitu = false;

    /**
     *
     * @param ime - ime studenta
     * @param prezime -prezime studenta
     * @param jmbag - JMBAG studenta
     * @param datumRodjenja - datum rodenja studenta
     */
    public Student(Long id, String ime, String prezime, String jmbag, LocalDate datumRodjenja) {
        super(ime, prezime);
        this.id = id;
        this.jmbag = jmbag;
        this.datumRodjenja = datumRodjenja;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return super.getIme();
    }


    public String getPrezime() {
        return super.getPrezime();
    }


    public String getJmbag() {
        return jmbag;
    }


    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return nedovoljnaOcjenaNaIspitu == student.nedovoljnaOcjenaNaIspitu && jmbag.equals(student.jmbag) && datumRodjenja.equals(student.datumRodjenja);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag, datumRodjenja, nedovoljnaOcjenaNaIspitu);
    }

    @Override
    public String toString() {
        return getIme() + " " + getPrezime();
    }
}
