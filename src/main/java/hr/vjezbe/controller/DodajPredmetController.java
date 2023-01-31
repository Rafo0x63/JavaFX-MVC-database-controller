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

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;
import java.util.ResourceBundle;

public class DodajPredmetController implements Initializable {

    @FXML
    Parent root;
    @FXML TextField sifraTextField;
    @FXML TextField nazivTextField;
    @FXML TextField ectsTextField;
    @FXML ComboBox<Profesor> profesorComboBox;
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
        List<Predmet> predmeti = new ArrayList<>();
        try {
            predmeti = BazaPodataka.dohvatiSvePredmete();
        } catch (BazaPodatakaException e) {
            e.printStackTrace();
        }
        OptionalLong maxId = predmeti.stream().mapToLong(p->p.getId()).max();
        Predmet noviPredmet = new Predmet(maxId.getAsLong(), sifraTextField.getText(), nazivTextField.getText(), Integer.parseInt(ectsTextField.getText()), profesorComboBox.getValue().getId());
        FileWriter file = new FileWriter("dat/predmetinput.txt", true);
        file.write(noviPredmet.getSifra() + "\n" +  noviPredmet.getNaziv() + "\n" + noviPredmet.getBrojEctsBodova().toString() + "\n" + noviPredmet.getNositelj().toString() + "\n");
        file.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            profesorComboBox.setItems(FXCollections.observableArrayList(BazaPodataka.dohvatiSveProfesore()));
        } catch (BazaPodatakaException e) {
            e.printStackTrace();
        }
    }


}
