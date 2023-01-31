package hr.vjezbe.controller;

import hr.vjezbe.baza.BazaPodataka;
import hr.vjezbe.entitet.Student;
import hr.vjezbe.iznimke.BazaPodatakaException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class StudentController implements Initializable {

   @FXML Parent root;
   @FXML private TableView<Student> tableView;
   @FXML private TableColumn<Student, String> jmbagTableColumn;
   @FXML private TableColumn<Student, String> prezimeTableColumn;
   @FXML private TableColumn<Student, String> imeTableColumn;
   @FXML private TableColumn<Student, String> datumRodjenjaTableColumn;
   @FXML private TextField jmbagTextField;
   @FXML private TextField prezimeTextField;
   @FXML private TextField imeTextField;
   @FXML private DatePicker datumRodjenjaDatePicker;
   @FXML private Button pretraziButton;

   private static ArrayList<Student> studenti;

   static {
      try {
         studenti = new ArrayList<Student>(BazaPodataka.dohvatiSveStudente());
      } catch (BazaPodatakaException e) {
         e.printStackTrace();
      }
   }

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
      jmbagTableColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("jmbag"));
      prezimeTableColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("prezime"));
      imeTableColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("ime"));
      datumRodjenjaTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
         @Override
         public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> student) {
            return new SimpleStringProperty(student.getValue().getDatumRodjenja().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
         }
      });

      try {
         tableView.setItems(FXCollections.observableArrayList(BazaPodataka.dohvatiSveStudente()));
      } catch (BazaPodatakaException e) {
         throw new RuntimeException(e);
      }
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

   @FXML public void pretrazi(){
      Set<Student> filtrirani = new HashSet<>();

      studenti.forEach(student -> {
         if(!imeTextField.getText().isEmpty() && student.getIme().toLowerCase().contains(imeTextField.getText().toLowerCase())) filtrirani.add(student);
         if(!prezimeTextField.getText().isEmpty() && student.getPrezime().toLowerCase().contains(prezimeTextField.getText().toLowerCase())) filtrirani.add(student);
         if(!jmbagTextField.getText().isEmpty() && student.getJmbag().contains(jmbagTextField.getText().toLowerCase())) filtrirani.add(student);
         if(datumRodjenjaDatePicker.getValue() != null && !datumRodjenjaDatePicker.getValue().toString().isEmpty() && student.getDatumRodjenja().toString().contains(datumRodjenjaDatePicker.getValue().toString())) filtrirani.add(student);

      });


      if (imeTextField.getText().isEmpty() && prezimeTextField.getText().isEmpty() && jmbagTextField.getText().isEmpty() && datumRodjenjaDatePicker.getValue() == null) tableView.setItems(FXCollections.observableArrayList(studenti));
      else tableView.setItems(FXCollections.observableArrayList(filtrirani));
   }

}