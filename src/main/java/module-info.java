module com.example.bdtel {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mariadb.jdbc;
    requires java.sql;
    requires jasperreports;
    requires java.desktop;

    opens com.example.bdtel.Controladores to javafx.fxml;
    exports com.example.bdtel.Controladores;

    opens com.example.bdtel.DAO to javafx.fxml;
    exports com.example.bdtel.DAO;

    opens com.example.bdtel.Modelo to javafx.fxml;
    exports com.example.bdtel.Modelo;

    exports com.example.bdtel;
    opens com.example.bdtel to javafx.fxml;

}