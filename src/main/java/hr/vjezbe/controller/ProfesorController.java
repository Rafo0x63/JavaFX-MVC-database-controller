package hr.vjezbe.controller;

import hr.vjezbe.baza.BazaPodataka;
import hr.vjezbe.entitet.Profesor;
import hr.vjezbe.entitet.Student;
import hr.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ProfesorController implements Initializable {


    @FXML Parent root;
    @FXML private TableView<Profesor> tableView;
    @FXML private TableColumn<Profesor, String> sifraTableColumn;
    @FXML private TableColumn<Profesor, String> prezimeTableColumn;
    @FXML private TableColumn<Profesor, String> imeTableColumn;
    @FXML private TableColumn<Profesor, String> titulaTableColumn;
    @FXML private TextField sifraTextField;
    @FXML private TextField prezimeTextField;
    @FXML private TextField imeTextField;
    @FXML private TextField titulaTextField;
    @FXML private Button pretraziButton;

    ArrayList<Profesor> profesori;

    {
        try {
            profesori = new ArrayList<>(BazaPodataka.dohvatiSveProfesore());
        } catch (BazaPodatakaException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sifraTableColumn.setCellValueFactory(new PropertyValueFactory<Profesor, String>("sifra"));
        prezimeTableColumn.setCellValueFactory(new PropertyValueFactory<Profesor, String>("prezime"));
        imeTableColumn.setCellValueFactory(new PropertyValueFactory<Profesor, String>("ime"));
        titulaTableColumn.setCellValueFactory(new PropertyValueFactory<Profesor, String>("titula"));

        tableView.setItems(FXCollections.observableArrayList(profesori));
    }

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

    @FXML
    public void pretrazi() {
        Set<Profesor> filtrirani = new HashSet<>();

        profesori.forEach(profesor -> {
            if(!sifraTextField.getText().isEmpty() && profesor.getSifra().toLowerCase().contains(sifraTextField.getText().toLowerCase())) filtrirani.add(profesor);
            if(!prezimeTextField.getText().isEmpty() && profesor.getPrezime().toLowerCase().contains(prezimeTextField.getText().toLowerCase())) filtrirani.add(profesor);
            if(!imeTextField.getText().isEmpty() && profesor.getIme().toLowerCase().contains(imeTextField.getText().toLowerCase())) filtrirani.add(profesor);
            if(!titulaTextField.getText().isEmpty() && profesor.getTitula().toLowerCase().contains(titulaTextField.getText().toLowerCase())) filtrirani.add(profesor);
        });
        if (sifraTextField.getText().isEmpty() && prezimeTextField.getText().isEmpty() && imeTextField.getText().isEmpty() && titulaTextField.getText().isEmpty()) tableView.setItems(FXCollections.observableArrayList(profesori));
        else tableView.setItems(FXCollections.observableArrayList(filtrirani));
    }
}
