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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class PredmetController implements Initializable {

    @FXML Parent root;
    @FXML TableView<Predmet> tableView;
    @FXML TableColumn<Predmet, String> sifraTableColumn;
    @FXML TableColumn<Predmet, String> nazivTableColumn;
    @FXML TableColumn<Predmet, Integer> ectsTableColumn;
    @FXML TableColumn<Predmet, Profesor> nositeljTableColumn;
    @FXML TextField sifraTextField;
    @FXML TextField nazivTextField;
    @FXML TextField ectsTextField;
    @FXML TextField nositeljTextField;


    ArrayList<Predmet> predmeti;

    {
        try {
            predmeti = new ArrayList<>(BazaPodataka.dohvatiSvePredmete());
        } catch (BazaPodatakaException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sifraTableColumn.setCellValueFactory(new PropertyValueFactory<Predmet, String>("sifra"));
        nazivTableColumn.setCellValueFactory(new PropertyValueFactory<Predmet, String>("naziv"));
        ectsTableColumn.setCellValueFactory(new PropertyValueFactory<Predmet, Integer>("brojEctsBodova"));
        nositeljTableColumn.setCellValueFactory(new PropertyValueFactory<Predmet, Profesor>("nositelj"));

        tableView.setItems(FXCollections.observableArrayList(predmeti));
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

    @FXML public void pretrazi() {
        Set<Predmet> filtrirani = new HashSet<>();

        predmeti.forEach(predmet -> {
            if(!sifraTextField.getText().isEmpty() && predmet.getSifra().toLowerCase().contains(sifraTextField.getText().toLowerCase())) filtrirani.add(predmet);
            if(!nazivTextField.getText().isEmpty() && predmet.getNaziv().toLowerCase().contains(nazivTextField.getText().toLowerCase())) filtrirani.add(predmet);
            if(!ectsTextField.getText().isEmpty() && predmet.getBrojEctsBodova().toString().contains(ectsTextField.getText())) filtrirani.add(predmet);
            if(!nositeljTextField.getText().isEmpty() && predmet.getNositelj().toString().toLowerCase().contains(nositeljTextField.getText().toLowerCase())) filtrirani.add(predmet);
        });

        if(sifraTextField.getText().isEmpty() && nazivTextField.getText().isEmpty() && ectsTextField.getText().isEmpty() && nositeljTextField.getText().isEmpty()) tableView.setItems(FXCollections.observableArrayList(predmeti));
        else tableView.setItems(FXCollections.observableArrayList(filtrirani));
    }

}
