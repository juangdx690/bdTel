module com.example.bdtel {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mariadb.jdbc;
    requires java.sql;

    opens com.example.bdtel to javafx.fxml;
    exports com.example.bdtel;
}