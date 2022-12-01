package DAO;

import Modelo.Marcas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

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

    public Boolean anadirMarca(Marcas marca) {
        boolean comprobacion = false;

        int registrosAfectadosConsulta = 0;

        try {

            conexion = DriverManager.getConnection(servidor, usuario, passwd);
            String SQL = "INSERT INTO Marcas("
                    +"Marca )"
                    +"VALUES (?)";

            PreparedStatement st = conexion.prepareStatement(SQL);
            st.setString(1, marca.getMarca());

            registrosAfectadosConsulta = st.executeUpdate();
            st.close();
            conexion.close();

            if (registrosAfectadosConsulta == 1){

                comprobacion = true;

            }else {

                comprobacion = false;
            }

        }catch (Exception e){

        }


        return comprobacion;
    }

    public Boolean actualizarMarca( Marcas marcaNueva, String marcavieja){
        int registros = 0;
        boolean comp = false;

        try {

            conexion = DriverManager.getConnection(servidor, usuario, passwd);
            String SQL = "UPDATE marcas "
                    +"SET "
                    +"Marca = ?"
                    +"WHERE Marca = ?";

            PreparedStatement st = conexion.prepareStatement(SQL);

            st.setString(1, marcaNueva.getMarca());
            st.setString(2, marcavieja);
            registros = st.executeUpdate();

            st.close();

            conexion.close();

            if (registros == 1) {
                comp = true;
            } else {

                comp = false;

            }

        }catch (Exception e){

            System.out.println(e.toString());

        }

        return  comp;

    }

    public Boolean borrarMarca(Marcas marca) {
        boolean comp;
        try {
            conexion = DriverManager.getConnection(servidor, usuario, passwd);
            String SQL = "DELETE FROM Marcas "
                    + " WHERE Marca = ?";

            PreparedStatement st = conexion.prepareStatement(SQL);

            st.setString(1, marca.getMarca());

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
