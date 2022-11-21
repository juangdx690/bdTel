package com.example.bdtel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class MarcasDAO {

    private final String servidor = "jdbc:mariadb://localhost:5555/moviles?useSSL=false";
    private final String usuario = "root";
    private final String passwd = "adminer";

    private Connection conexion;

    public ObservableList<Marcas> obtenerMarcas() {

        ObservableList<Marcas> datosResultadoConsulta = FXCollections.observableArrayList();

        try {
            conexion = DriverManager.getConnection(servidor, usuario, passwd);
            String SQL = "select Marca from Marcas order by Marca";

            ResultSet resultadoConsulta = conexion.createStatement().executeQuery(SQL);

            while (resultadoConsulta.next()) {

                datosResultadoConsulta.add(new Marcas(

                        resultadoConsulta.getString("Marca"))

                );


            }

            conexion.close();

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Error:" + e.toString());
            conexion.close();
        } finally {
            return datosResultadoConsulta;
        }


    }


}
