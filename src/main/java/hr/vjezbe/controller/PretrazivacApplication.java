package hr.vjezbe.controller;

import hr.vjezbe.baza.BazaPodataka;
import hr.vjezbe.entitet.Predmet;
import hr.vjezbe.iznimke.BazaPodatakaException;
import hr.vjezbe.niti.DatumRodjenjaNit;
import hr.vjezbe.niti.NajboljiStudentNit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class PretrazivacApplication extends Application {

    static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException, BazaPodatakaException {
        mainStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("izbornik-view.fxml"));
        Scene scene = new Scene(root, 800, 763);
        mainStage.setTitle("Pretraživač");
        mainStage.setScene(scene);
        mainStage.show();

        Timeline prikazSlavljenika = new Timeline(
                new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Platform.runLater(new DatumRodjenjaNit());
                    }
                }));
        prikazSlavljenika.setCycleCount(Timeline.INDEFINITE);
        prikazSlavljenika.play();

        Timeline prikazNajStudenta = new Timeline(
                new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Platform.runLater(new NajboljiStudentNit(mainStage));
                    }
                }));
        prikazNajStudenta.setCycleCount(Timeline.INDEFINITE);
        prikazNajStudenta.play();

    }

    public static Stage getMainStage(){
        return mainStage;
    }

    public static void main(String[] args) {
        launch();
    }




}