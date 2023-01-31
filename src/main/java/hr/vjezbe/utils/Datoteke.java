/*package hr.vjezbe.utils;


//!!!!!!!!!!!!!!!!!!!!!!!!!
//DEPRECATED - do not use or change, was used to get data using files before DB implementation
//TODO: check for usages and remove
//!!!!!!!!!!!!!!!!!!!!!!!!!


import hr.vjezbe.entitet.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Datoteke {

        private static ObservableList<Student> studenti = ucitajStudente();
        private static ObservableList<Profesor> profesori = ucitajProfesore();
        private static ObservableList<Predmet> predmeti = ucitajPredmete();
        private static final int BROJ_ZAPISA_ISPITA = 4;
        private static final int BROJ_ZAPISA_STUDENATA = 4;
        private static final int BROJ_ZAPISA_PROFESORA = 4;
        private static final int BROJ_ZAPISA_PREDMETA = 4;
        public static ObservableList<Student> ucitajStudente() {

            ObservableList<Student> studenti = FXCollections.observableArrayList();

            try (BufferedReader bufStudenti = new BufferedReader(new FileReader(new File("dat/studentinput.txt")))){
                System.out.println("Datoteka 'studentiinput' uspjesno ucitana");
                List<String> studentiDat = bufStudenti.lines().collect(Collectors.toList());

                for (int i = 0; i < studentiDat.size() / BROJ_ZAPISA_STUDENATA; i++){

                    String ime = studentiDat.get(i * BROJ_ZAPISA_STUDENATA);
                    String prezime = studentiDat.get(i*BROJ_ZAPISA_STUDENATA+1);
                    String datum = studentiDat.get(i*BROJ_ZAPISA_STUDENATA+2);
                    String jmbag = studentiDat.get(i*BROJ_ZAPISA_STUDENATA+3);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                    LocalDate datumRodenja = LocalDate.parse(datum, formatter);

                    studenti.add(new Student(ime, prezime, jmbag, datumRodenja));
                }

            } catch (IOException e) {
                System.out.println("Greška otvaranja datoteke");
            }

            return studenti;
        }

    public static ObservableList<Profesor> ucitajProfesore()  {

        ObservableList<Profesor> profesori = FXCollections.observableArrayList();

        try (BufferedReader bufProfesor = new BufferedReader(new FileReader(new File("dat/profesorinput.txt")))){
            System.out.println("Datoteka 'profesoriinput' uspjesno otvorena");
            List<String> profesoriDat = bufProfesor.lines().collect(Collectors.toList());

            for (int i = 0; i < profesoriDat.size() / BROJ_ZAPISA_PROFESORA;i++){

                String sifra = profesoriDat.get(i * BROJ_ZAPISA_PROFESORA);
                String ime = profesoriDat.get(i*BROJ_ZAPISA_PROFESORA+1);
                String prezime = profesoriDat.get(i*BROJ_ZAPISA_PROFESORA+2);
                String titula = profesoriDat.get(i*BROJ_ZAPISA_PROFESORA+3);
                profesori.add(new Profesor(sifra, ime, prezime, titula));
            }

        } catch (IOException e) {
            System.out.println("Greška otvaranja datoteke");
        }
        return profesori;
    }

    public static ObservableList<Predmet> ucitajPredmete()  {

        ObservableList<Predmet> predmeti = FXCollections.observableArrayList();

        try (BufferedReader bufPredmeti = new BufferedReader(new FileReader(new File("dat/predmetinput.txt")))){

            List<String> predmetiDat = bufPredmeti.lines().collect(Collectors.toList());

            for (int i = 0; i < predmetiDat.size() / BROJ_ZAPISA_PREDMETA; i++){

                String sifra = predmetiDat.get(i * BROJ_ZAPISA_PREDMETA);
                String naziv = predmetiDat.get(i*BROJ_ZAPISA_PREDMETA+1);
                String ects = predmetiDat.get(i*BROJ_ZAPISA_PREDMETA+2);
                String ime = predmetiDat.get(i*BROJ_ZAPISA_PROFESORA+3);
                Profesor nositelj = profesori.stream().filter(profesor -> profesor.toString().compareTo(ime) == 0).collect(Collectors.toList()).get(0);


                predmeti.add(new Predmet(sifra, naziv, Integer.parseInt(ects), nositelj));
            }

        } catch (IOException e) {
            System.out.println("Greška otvaranja datoteke");
        }

        return predmeti;
    }

    public static ObservableList<Ispit> ucitajIspite() {

            ObservableList<Ispit> ispiti = FXCollections.observableArrayList();

        try (BufferedReader bufIspiti = new BufferedReader(new FileReader(new File("dat/ispitiocjeneinput.txt")))){

            List<String> ispitiDat = bufIspiti.lines().collect(Collectors.toList());

            for (int i = 0; i < ispitiDat.size() / BROJ_ZAPISA_ISPITA; i++){

                String predmetUnos = ispitiDat.get(i * BROJ_ZAPISA_ISPITA);
                String studentUnos = ispitiDat.get(i*BROJ_ZAPISA_ISPITA+1);
                String ocjenaUnos = ispitiDat.get(i*BROJ_ZAPISA_ISPITA+2);
                String datum = ispitiDat.get(i*BROJ_ZAPISA_ISPITA+3);

                Predmet predmet = predmeti.stream().filter(predmet1 -> predmet1.getNaziv().compareTo(predmetUnos) == 0).collect(Collectors.toList()).get(0);
                Student student = studenti.stream().filter(s -> s.toString().compareTo(studentUnos) == 0).collect(Collectors.toList()).get(0);

                Ocjena ocjena = null;
                switch(Integer.parseInt(ocjenaUnos)) {
                    case 1:
                        ocjena = Ocjena.NEDOVOLJAN;
                    case 2:
                        ocjena = Ocjena.DOVOLJAN;
                    case 3:
                        ocjena = Ocjena.DOBAR;
                    case 4:
                        ocjena = Ocjena.VRLO_DOBAR;
                    case 5:
                        ocjena = Ocjena.ODLICAN;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
                LocalDateTime datumIVrijeme = LocalDateTime.parse(datum, formatter);

                ispiti.add(new Ispit(predmet, student, ocjena, datumIVrijeme));
                predmet.getStudenti().add(student);
            }

        } catch (IOException e) {
            System.out.println("Greška otvaranja datoteke");
        }
        return ispiti;
    }
}

*/