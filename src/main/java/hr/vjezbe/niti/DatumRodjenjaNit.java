package hr.vjezbe.niti;

import hr.vjezbe.entitet.Predmet;
import hr.vjezbe.entitet.Student;
import hr.vjezbe.iznimke.BazaPodatakaException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.util.Duration;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import static hr.vjezbe.baza.BazaPodataka.dohvatiPredmetePremaKriterijima;
import static hr.vjezbe.baza.BazaPodataka.spajanjeNaBazu;

public class DatumRodjenjaNit implements Runnable {

    @Override
    public void run() {

        ArrayList<Student> studenti = new ArrayList<>();
        try (Connection veza = spajanjeNaBazu()) {
            PreparedStatement sqlUpit = veza.prepareStatement("SELECT * FROM STUDENT WHERE DAY(DATUM_RODJENJA) = (?) AND MONTH(DATUM_RODJENJA) = (?)");
            sqlUpit.setInt(1, LocalDate.now().getDayOfMonth());
            sqlUpit.setInt(2, LocalDate.now().getMonthValue());
            ResultSet resultSet = sqlUpit.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                String jmbag = resultSet.getString("jmbag");
                LocalDate datumRodjenja = resultSet.getTimestamp("datum_rodjenja").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Student noviStudent = new Student(id, ime, prezime, jmbag, datumRodjenja);
                studenti.add(noviStudent);
            }
        } catch (Exception ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            try {
                throw new BazaPodatakaException(poruka, ex);
            } catch (BazaPodatakaException e) {
                throw new RuntimeException(e);
            }
        }

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        StringBuilder content = new StringBuilder("Čestitajte rodjendan sljedećim studentima:\n");
        studenti.forEach(student -> content.append(student.toString()).append("\n"));
        a.setContentText(content.toString());
        a.show();

    }
}
