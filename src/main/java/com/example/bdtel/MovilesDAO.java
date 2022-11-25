package com.example.bdtel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public ObservableList<Moviles> obtenerMovilesconMarca(String marca, String modelo, String fecha) {

        ObservableList<Moviles> datosResultadoConsulta = FXCollections.observableArrayList();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String fechaActual = dtf.format(LocalDateTime.now());


        try {
            conexion = DriverManager.getConnection(servidor, usuario, passwd);
            String SQL = "select * from telefonos where marca like '%" + marca + "%' and modelo like '%" + modelo + "%' and" +
                    " fechalanzamiento between '" + fecha + "' and '" + fechaActual + "'";

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


    public Boolean anadirMovil(Moviles moviles, String ruta) {
        int registrosAfectadosConsulta = 0;
        boolean comprobacion;

        if (ruta.length() == 0) {


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
                        + "Fechalanzamiento)"
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

        } else {

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
                        + "Fechalanzamiento,"
                        + " Imagen)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

                File file = new File(ruta);


                FileInputStream fis = new FileInputStream(file);

                st.setBinaryStream(11, fis, fis.available());


                registrosAfectadosConsulta = st.executeUpdate();
                st.close();
                conexion.close();

                if (registrosAfectadosConsulta == 1) {
                    comprobacion = true;
                } else {

                    comprobacion = false;
                }

            } catch (SQLException | FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Error:" + e.toString());
                comprobacion = false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

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


    public Boolean actualizarMovilConRuta(Moviles moviles, String ruta) {
        boolean comp;

        File file = new File(ruta);


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
                    + " Fechalanzamiento = ? ,"
                    + " Imagen = ?"
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

            FileInputStream fis = new FileInputStream(file);
            st.setBinaryStream(11, fis, fis.available());
            st.setInt(12, moviles.getId());





            int registros = st.executeUpdate();

            st.close();

            conexion.close();

            System.out.println(registros);

            if (registros == 1) {
                comp = true;
            } else {

                comp = false;

            }

        } catch (SQLException | FileNotFoundException e) {
            comp = false;
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
            comp = false;
        }

        return comp;
    }

    public Boolean borrarProducto(Moviles movil) {
        boolean comp;
        try {
            conexion = DriverManager.getConnection(servidor, usuario, passwd);
            String SQL = "DELETE FROM telefonos "
                    + " WHERE ID = ?";

            PreparedStatement st = conexion.prepareStatement(SQL);

            st.setInt(1, movil.getId());

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

    public InputStream obtenerImagen(int id) {

        InputStream is = null;
        try {
            conexion = DriverManager.getConnection(servidor, usuario, passwd);

            String SQL = "select Imagen from telefonos where id = " + id;

            ResultSet resultadoConsulta = conexion.createStatement().executeQuery(SQL);

            byte [] arr  = null;

            while (resultadoConsulta.next()) {

                arr =  resultadoConsulta.getBytes("Imagen");
            }


            if (arr != null){

                is = new ByteArrayInputStream(arr);


            }

            resultadoConsulta.close();
            conexion.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return is;

    }



}
