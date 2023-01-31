package hr.vjezbe.entitet;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Obrazovna ustanova sadrzi podatke o predmetima, profesorima, studentima te ispitima koji se odrzavaju u tim ustanovama
 */
public abstract class ObrazovnaUstanova implements Serializable {
    private String naziv;
    private ArrayList<Predmet> predmeti;
    private ArrayList<Profesor> profesori;
    private ArrayList<Student> studenti;
    private ArrayList<Ispit> ispiti;

    /**
     *
     * @param naziv - naziv ustanove
     * @param predmeti - predmeti koji se drze na ustanovi
     * @param profesori - profesori koji predaju predmete
     * @param studenti - studenti koji pohadaju predmete
     * @param ispiti - ispiti koji se odrzavaju na ustanovi
     */
    public ObrazovnaUstanova(String naziv, ArrayList<Predmet> predmeti, ArrayList<Profesor> profesori, ArrayList<Student> studenti, ArrayList<Ispit> ispiti) {
        this.naziv = naziv;
        this.predmeti = predmeti;
        this.profesori = profesori;
        this.studenti = studenti;
        this.ispiti = ispiti;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public ArrayList<Predmet> getPredmeti() {
        return predmeti;
    }



    public ArrayList<Profesor> getProfesori() {
        return profesori;
    }



    public ArrayList<Student> getStudenti() {
        return studenti;
    }



    public ArrayList<Ispit> getIspiti() {
        return ispiti;
    }



    public abstract Student odrediNajuspjesnijegStudentaNaGodini(int godina);
}
