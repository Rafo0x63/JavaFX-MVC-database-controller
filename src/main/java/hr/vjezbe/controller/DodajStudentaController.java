package hr.vjezbe.controller;

import hr.vjezbe.baza.BazaPodataka;
import hr.vjezbe.entitet.Predmet;
import hr.vjezbe.entitet.Profesor;
import hr.vjezbe.entitet.Student;
import hr.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;
import java.util.ResourceBundle;

public class DodajStudentaController {
    @FXML
    Parent root;
    @FXML TextField imeTextfield;
    @FXML TextField prezimeTextField;
    @FXML DatePicker datumRodenjaDatePicker;
    @FXML TextField jmbagTextField;
    @FXML Button dodajButton;





    @FXML
    public void pretraziStudente() throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("student-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 763);
        stage.setScene(scene);
        stage.show();
    }
    @FXML public void unesiStudenta() throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dodaj-studenta-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 763);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void pretraziProfesore() throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profesor-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 763);
        stage.setScene(scene);
        stage.show();
    }
    @FXML public void unesiProfesora() throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dodaj-profesora-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 763);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void pretraziIspite() throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ispit-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 763);
        stage.setScene(scene);
        stage.show();
    }
    @FXML public void unesiIspit() throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dodaj-ispit-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 763);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void pretraziPredmete() throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("predmet-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 763);
        stage.setScene(scene);
        stage.show();
    }
    @FXML public void unesiPredmet() throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dodaj-predmet-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 763);
        stage.setScene(scene);
        stage.show();
    }

    @FXML public void dodaj() throws IOException {
        List<Student> studenti = new ArrayList<>();
        try {
            studenti = BazaPodataka.dohvatiSveStudente();
        } catch (BazaPodatakaException e) {
            e.printStackTrace();
        }
        LocalDate datum = datumRodenjaDatePicker.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        String text = datum.format(formatter);
        LocalDate parsedDatum = LocalDate.parse(text, formatter);
        OptionalLong maxId = studenti.stream().mapToLong(student -> student.getId()).max();
        Student noviStudent = new Student(maxId.getAsLong(), imeTextfield.getText(), prezimeTextField.getText(), jmbagTextField.getText(), parsedDatum);
        try {
            BazaPodataka.spremiNovogStudenta(noviStudent);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Student dodan!");
            alert.show();
        } catch (BazaPodatakaException e) {
            e.printStackTrace();
        }
    }


}
