package hr.vjezbe.niti;

import hr.vjezbe.baza.BazaPodataka;
import hr.vjezbe.controller.PretrazivacApplication;
import hr.vjezbe.entitet.Ispit;
import hr.vjezbe.entitet.Student;
import hr.vjezbe.iznimke.BazaPodatakaException;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class NajboljiStudentNit implements Runnable{

    List<Student> studenti = new ArrayList<>();
    List<Ispit> ispiti = new ArrayList<>();

    private Stage stage;
    public NajboljiStudentNit(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void run() {
        try {
            studenti = BazaPodataka.dohvatiSveStudente();
            ispiti = BazaPodataka.dohvatiSveIspite();
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }

        float suma = 0;
        float najProsjek = 0;
        int brojIspita = 0;
        Student najbolji = studenti.get(0);
        for (Student student : studenti) {
            suma = 0;
            brojIspita = 0;
            for (Ispit ispit : ispiti) {
                if (student.getId() == ispit.getStudentId()){
                    suma += ispit.getOcjena();
                    brojIspita++;
                }
            }
            float prosjek = suma / brojIspita;
            if (prosjek > najProsjek){
                najProsjek = prosjek;
                najbolji = student;
            }
        }

        stage = PretrazivacApplication.getMainStage();
        stage.setTitle("Najbolji student je: " + najbolji.toString() + "(" + najProsjek + ")");
    }
}
