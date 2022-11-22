package com.example.bdtel;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class MovilesDAO {

    private final String servidor = "jdbc:mariadb://localhost:5555/moviles?useSSL=false";
    private final String usuario = "root";
    private final String passwd = "adminer";

    private Connection conexion;

    public ObservableList<Moviles> obtenerMoviles() {

        ObservableList<Moviles> datosResultadoConsulta = FXCollections.observableArrayList();

        try {
            conexion = DriverManager.getConnection(servidor, usuario, passwd);
            String SQL = "select * from telefonos order by id";

            ResultSet resultadoConsulta = conexion.createStatement().executeQuery(SQL);

            while (resultadoConsulta.next()) {

                datosResultadoConsulta.add(new Moviles(

                        resultadoConsulta.getInt("ID"),
                        resultadoConsulta.getString("Modelo"),
                        resultadoConsulta.getString("Marca"),
                        resultadoConsulta.getInt("Almacenamiento"),
                        resultadoConsulta.getInt("RAM"),
                        resultadoConsulta.getString("Sistemaoperativo"),
                        resultadoConsulta.getString("Procesador"),
                        resultadoConsulta.getInt("Bateria"),
                        resultadoConsulta.getDouble("Preciosalida"),
                        resultadoConsulta.getDouble("Precioactual"),
                        resultadoConsulta.getString("Fechalanzamiento"))

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

    public ObservableList<Moviles> obtenerMovilesconMarca(String marca) {

        ObservableList<Moviles> datosResultadoConsulta = FXCollections.observableArrayList();

        try {
            conexion = DriverManager.getConnection(servidor, usuario, passwd);
            String SQL = "select * from telefonos where marca like '" + marca + "%'";

            ResultSet resultadoConsulta = conexion.createStatement().executeQuery(SQL);

            while (resultadoConsulta.next()) {

                datosResultadoConsulta.add(new Moviles(

                        resultadoConsulta.getInt("ID"),
                        resultadoConsulta.getString("Modelo"),
                        resultadoConsulta.getString("Marca"),
                        resultadoConsulta.getInt("Almacenamiento"),
                        resultadoConsulta.getInt("RAM"),
                        resultadoConsulta.getString("Sistemaoperativo"),
                        resultadoConsulta.getString("Procesador"),
                        resultadoConsulta.getInt("Bateria"),
                        resultadoConsulta.getDouble("Preciosalida"),
                        resultadoConsulta.getDouble("Precioactual"),
                        resultadoConsulta.getString("Fechalanzamiento"))

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


    public Boolean anadirMovil(Moviles moviles) {
        int registrosAfectadosConsulta = 0;
        boolean comprobacion;
        try {
            conexion = DriverManager.getConnection(servidor, usuario, passwd);
            String SQL = "INSERT INTO telefonos("
                    + "Modelo ,"
                    + "Marca ,"
                    + "Almacenamiento ,"
                    + "RAM ,"
                    + "Sistemaoperativo ,"
                    + "Procesador ,"
                    + "Bateria ,"
                    + "Preciosalida ,"
                    + "Precioactual ,"
                    + "Fechalanzamiento) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement st = conexion.prepareStatement(SQL);
            st.setString(1, moviles.getModelo());
            st.setString(2, moviles.getMarca());
            st.setInt(3, moviles.getAlmacenamiento());
            st.setInt(4, moviles.getRam());
            st.setString(5, moviles.getSistemaOperativo());
            st.setString(6, moviles.getCpu());
            st.setInt(7, moviles.getBateria());
            st.setDouble(8, moviles.getPrecioSalida());
            st.setDouble(9, moviles.getPrecio());
            st.setDate(10, Date.valueOf(moviles.getDate()));

            registrosAfectadosConsulta = st.executeUpdate();
            st.close();
            conexion.close();

            if (registrosAfectadosConsulta == 1) {
                comprobacion = true;
            } else {

                comprobacion = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error:" + e.toString());
            comprobacion = false;
        }

        return comprobacion;
    }


    public Boolean actualizarMovil(Moviles moviles) {
        boolean comp;
        try {
            conexion = DriverManager.getConnection(servidor, usuario, passwd);
            String SQL = "UPDATE telefonos "
                    + "SET "
                    + " Modelo = ? ,"
                    + " Marca = ? ,"
                    + " Almacenamiento = ? ,"
                    + " RAM = ? ,"
                    + " Sistemaoperativo = ? , "
                    + " Procesador = ? ,"
                    + " Bateria = ? ,"
                    + " Preciosalida = ? ,"
                    + " Precioactual = ? ,"
                    + " Fechalanzamiento = ? "
                    + " WHERE ID = ?";

            PreparedStatement st = conexion.prepareStatement(SQL);

            st.setString(1, moviles.getModelo());
            st.setString(2, moviles.getMarca());
            st.setInt(3, moviles.getAlmacenamiento());
            st.setInt(4, moviles.getRam());
            st.setString(5, moviles.getSistemaOperativo());
            st.setString(6, moviles.getCpu());
            st.setInt(7, moviles.getBateria());
            st.setDouble(8, moviles.getPrecioSalida());
            st.setDouble(9, moviles.getPrecio());
            st.setDate(10, Date.valueOf(moviles.getDate()));

            st.setInt(11, moviles.getId());

            int registros = st.executeUpdate();

            st.close();

            conexion.close();

            if (registros == 1) {
                comp = true;
            } else {

                comp = false;

            }

        } catch (SQLException e) {
            comp = false;
            System.out.println(e.toString());
        }

        return comp;
    }

}
