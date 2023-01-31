package hr.vjezbe.controller;

import hr.vjezbe.baza.BazaPodataka;
import hr.vjezbe.entitet.Ispit;
import hr.vjezbe.entitet.Ocjena;
import hr.vjezbe.entitet.Predmet;
import hr.vjezbe.entitet.Student;
import hr.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.OptionalLong;
import java.util.ResourceBundle;

public class DodajIspitController implements Initializable {
    @FXML
    Parent root;
    @FXML
    ComboBox<Predmet> predmetComboBox;
    @FXML ComboBox<Student> studentComboBox;
    @FXML ComboBox<Ocjena> ocjenaComboBox;
    @FXML
    TextField datumVrijemeTextField;


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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
            LocalDateTime datumVrijeme = LocalDateTime.parse(datumVrijemeTextField.getText(), formatter);
        OptionalLong maxId;
        try {
            maxId = BazaPodataka.dohvatiSveIspite().stream().mapToLong(i -> i.getId()).max();
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }
        Ispit noviIspit = new Ispit(maxId.getAsLong(), predmetComboBox.getValue().getId(), studentComboBox.getValue().getId(), ocjenaComboBox.getValue(), datumVrijeme);
        try {
            BazaPodataka.spremiNoviIspit(noviIspit);
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            predmetComboBox.setItems(FXCollections.observableArrayList(BazaPodataka.dohvatiSvePredmete()));
        } catch (BazaPodatakaException e) {
            e.printStackTrace();
        }
        try {
            studentComboBox.setItems(FXCollections.observableArrayList(BazaPodataka.dohvatiSveStudente()));
        } catch (BazaPodatakaException e) {
            e.printStackTrace();
        }
        ocjenaComboBox.setItems(FXCollections.observableArrayList(Ocjena.NEDOVOLJAN, Ocjena.DOVOLJAN, Ocjena.DOBAR, Ocjena.VRLO_DOBAR, Ocjena.ODLICAN));
    }
}
