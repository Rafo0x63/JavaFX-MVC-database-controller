module hr.vjezbe.pretrazivac {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;


    exports hr.vjezbe.controller;
    opens hr.vjezbe.controller to javafx.fxml;
    exports hr.vjezbe.entitet to javafx.base;
    opens hr.vjezbe.entitet to javafx.base;


}