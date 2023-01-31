package hr.vjezbe.entitet;

import hr.vjezbe.baza.BazaPodataka;
import hr.vjezbe.iznimke.BazaPodatakaException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Ispit sadrzi podatke o ocjeni, studentu predmetu, datumu odrzavanja i dvorani
 * Takoder sadrzi podatke o software-u na kojem se odrzavaju online ispiti
 */
public final class Ispit implements Online, Serializable {

    private Long id;
    private Long predmetId;
    private Long studentId;
    private Ocjena ocjena;
    private LocalDateTime datumIVrijeme;
    private Dvorana dvorana;


    /**
     *
     * @param ocjena - ocjena studenta na ispitu
     * @param datumIVrijeme - datum odrzavanja ispita
     */
    public Ispit(Long id, Long predmetId, Long studentId, Ocjena ocjena, LocalDateTime datumIVrijeme) {
        this.id = id;
        this.predmetId = predmetId;
        this.studentId = studentId;
        this.ocjena = ocjena;
        this.datumIVrijeme = datumIVrijeme;
    }

    public Student getStudent(){
        Student s = null;
        try {
            List<Student> studenti = BazaPodataka.dohvatiSveStudente();
            for (Student student : studenti) {
                if (student.getId() == this.studentId) s = student;
            }
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }
        return s;
    }

    public Predmet getPredmet(){
        Predmet p = null;
        try {
            List<Predmet> predmeti = BazaPodataka.dohvatiSvePredmete();
            for (Predmet predmet : predmeti) {
                if (predmet.getId() == this.predmetId) p = predmet;
            }
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPredmetId() {
        return predmetId;
    }

    public void setPredmetId(Long predmetId) {
        this.predmetId = predmetId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public int getOcjena() {
        return ocjena.getNumber();
    }



    public LocalDateTime getDatumIVrijeme() {
        return datumIVrijeme;
    }

    public void setDatumIVrijeme(LocalDateTime datumIVrijeme) {
        this.datumIVrijeme = datumIVrijeme;
    }

    public Dvorana getDvorana() {
        return dvorana;
    }

    public void setDvorana(Dvorana dvorana) {
        this.dvorana = dvorana;
    }

    @Override
    public String softverZaIspit(String nazivSoftvera) {
        return nazivSoftvera;
    }
}
