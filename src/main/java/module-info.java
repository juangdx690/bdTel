module com.example.bdtel {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mariadb.jdbc;
    requires java.sql;

    opens com.example.bdtel to javafx.fxml;
    exports com.example.bdtel;
    exports Controladores;
    opens Controladores to javafx.fxml;
    exports DAO;
    opens DAO to javafx.fxml;
    exports Modelo;
    opens Modelo to javafx.fxml;
    exports Main;
    opens Main to javafx.fxml;
}