package hr.vjezbe.controller;

import hr.vjezbe.baza.BazaPodataka;
import hr.vjezbe.entitet.Predmet;
import hr.vjezbe.entitet.Profesor;
import hr.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;
import java.util.ResourceBundle;

public class DodajProfesoraController {

    @FXML
    Parent root;
    @FXML TextField sifraTextField;
    @FXML TextField imeTextField;
    @FXML TextField prezimeTextField;
    @FXML TextField titulaTextField;
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
        List<Profesor> profesori = new ArrayList<>();
        try {
            profesori = BazaPodataka.dohvatiSveProfesore();
        } catch (BazaPodatakaException e) {
            e.printStackTrace();
        }
        OptionalLong maxId = profesori.stream().mapToLong(p->p.getId()).max();
        Profesor noviProfesor = new Profesor(maxId.getAsLong(), sifraTextField.getText(), imeTextField.getText(), prezimeTextField.getText(), titulaTextField.getText());
        try {
            BazaPodataka.spremiNovogProfesora(noviProfesor);
        } catch (BazaPodatakaException e) {
            e.printStackTrace();
        }
    }




}
