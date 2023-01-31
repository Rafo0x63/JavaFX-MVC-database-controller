package hr.vjezbe.controller;

import hr.vjezbe.baza.BazaPodataka;
import hr.vjezbe.entitet.Ispit;
import hr.vjezbe.entitet.Ocjena;
import hr.vjezbe.entitet.Predmet;
import hr.vjezbe.entitet.Student;
import hr.vjezbe.iznimke.BazaPodatakaException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class IspitController implements Initializable {

    @FXML Parent root;
    @FXML TableView<Ispit> tableView;
    @FXML TableColumn<Ispit, Predmet> predmetTableColumn;
    @FXML TableColumn<Ispit, Student> studentTableColumn;
    @FXML
    TableColumn<Ispit, Ocjena> ocjenaTableColumn;
    @FXML TableColumn<Ispit, String> datumIVrijemeTableColumn;
    @FXML TextField predmetTextField;
    @FXML TextField studentTextField;
    @FXML TextField ocjenaTextField;
    @FXML DatePicker datumIVrijemeDatePicker;

    ArrayList<Ispit> ispiti;

    {
        try {
            ispiti = new ArrayList<>(BazaPodataka.dohvatiSveIspite());
        } catch (BazaPodatakaException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        predmetTableColumn.setCellValueFactory(new PropertyValueFactory<Ispit, Predmet>("predmet"));
        studentTableColumn.setCellValueFactory(new PropertyValueFactory<Ispit, Student>("student"));
        ocjenaTableColumn.setCellValueFactory(new PropertyValueFactory<Ispit, Ocjena>("ocjena"));
        datumIVrijemeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ispit, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ispit, String> ispit) {
                return new SimpleStringProperty(ispit.getValue().getDatumIVrijeme().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm")));
            }
        });

        tableView.setItems(FXCollections.observableArrayList(ispiti));
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
        Set<Ispit> filtrirani = new HashSet<>();

        ispiti.forEach(ispit -> {
            if(!predmetTextField.getText().isEmpty() && ispit.getPredmet().getNaziv().toLowerCase().contains(predmetTextField.getText().toLowerCase())) filtrirani.add(ispit);
            if(!studentTextField.getText().isEmpty() && ispit.getStudent().toString().toLowerCase().contains(studentTextField.getText().toLowerCase())) filtrirani.add(ispit);
            if(!ocjenaTextField.getText().isEmpty() && ispit.getOcjena() == Integer.parseInt(ocjenaTextField.getText())) filtrirani.add(ispit);
            if(datumIVrijemeDatePicker.getValue() != null && ispit.getDatumIVrijeme().toString().contains(datumIVrijemeDatePicker.getValue().toString())) filtrirani.add(ispit);

        });

        if(predmetTextField.getText().isEmpty() && studentTextField.getText().isEmpty() && ocjenaTextField.getText().isEmpty() && datumIVrijemeDatePicker.getValue() == null) tableView.setItems(FXCollections.observableArrayList(ispiti));
        else tableView.setItems(FXCollections.observableArrayList(filtrirani));
    }

}
