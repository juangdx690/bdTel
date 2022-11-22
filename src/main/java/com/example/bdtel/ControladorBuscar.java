package com.example.bdtel;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

public class ControladorBuscar {
    @javafx.fxml.FXML
    private VBox pestañaBusqueda;
    @javafx.fxml.FXML
    private Button btnPagBuscar;
    @javafx.fxml.FXML
    private Button btnPagAnadir;
    @javafx.fxml.FXML
    private Button btnPagActualizar;
    @javafx.fxml.FXML
    private Button btnPagBorrar;
    @javafx.fxml.FXML
    private Label txtTitulo;
    @javafx.fxml.FXML
    private Label btnSalir;
    @javafx.fxml.FXML
    private GridPane pestanaBusqueda;
    @javafx.fxml.FXML
    private TableView tablaBusqueda;
    @javafx.fxml.FXML
    private TableColumn colID;
    @javafx.fxml.FXML
    private TableColumn colModelo;
    @javafx.fxml.FXML
    private TableColumn colMarca;
    @javafx.fxml.FXML
    private TableColumn colAlmacenamiento;
    @javafx.fxml.FXML
    private TableColumn colRAM;
    @javafx.fxml.FXML
    private TableColumn colSO;
    @javafx.fxml.FXML
    private TableColumn colCPU;
    @javafx.fxml.FXML
    private TableColumn colBateria;
    @javafx.fxml.FXML
    private TableColumn colPrecioS;
    @javafx.fxml.FXML
    private TableColumn colPrecioA;
    @javafx.fxml.FXML
    private TableColumn colFecha;
    @javafx.fxml.FXML
    private TextField txtSearch;

    private MovilesDAO movilesDAO = new MovilesDAO();
    private Moviles movilesAUX;

    private MarcasDAO marcasDAO = new MarcasDAO();
    private Marcas marcas;

    private ObservableList<Moviles> datos;
    private ObservableList<Marcas> datos2;

    private ObservableList<String> datos2String;

    private int x = 0, y = 0;

    public void initialize() {

        cargarDatosTabla();

    }

    @javafx.fxml.FXML
    public void pestanaClicks(ActionEvent event) {


        if (event.getSource() == btnPagAnadir) {


            FXMLLoader loader = new FXMLLoader(Main.class.getResource("insertar.fxml"));

            try {
                Parent root = loader.load();

                ControladorInsertar controladorIns = loader.getController();

                Scene scene = new Scene(root, 1400, 700);

                Stage stage = new Stage();

                stage.setScene(scene);

                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();

                Stage stagePrin = (Stage) this.txtTitulo.getScene().getWindow();

                stagePrin.close();

                scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {

                        x = (int) mouseEvent.getSceneX();
                        y = (int) mouseEvent.getSceneY();

                    }
                });

                scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        stage.setX(mouseEvent.getScreenX() - x);
                        stage.setY(mouseEvent.getScreenY() - y);
                    }
                });


            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }

        if (event.getSource() == btnPagActualizar) {

            Moviles m = (Moviles) tablaBusqueda.getSelectionModel().getSelectedItem();


            Alert alert;

            if (Objects.isNull(m)) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Tienes que seleccionar una id");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Actualización del producto");
                alert.setContentText("¿Estas seguro de que quieres actualizar los datos del movil: " + m.getModelo() + " ?");
                Optional<ButtonType> action = alert.showAndWait();

                if (action.get() == ButtonType.OK) {


                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("actualizar.fxml"));

                    try {
                        Parent root = loader.load();

                        ControladorActualizar controladorAct = loader.getController();

                        controladorAct.recogerDatos(m.getId(), m.getModelo(), m.getMarca(), m.getAlmacenamiento(), m.getRam(),
                                m.getSistemaOperativo(), m.getCpu(), m.getBateria(), m.getPrecioSalida(), m.getPrecio(),
                                m.getFecha());

                        Scene scene = new Scene(root, 1400, 700);

                        Stage stage = new Stage();

                        stage.setScene(scene);

                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.show();

                        Stage stagePrin = (Stage) this.txtTitulo.getScene().getWindow();

                        stagePrin.close();

                        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {

                                x = (int) mouseEvent.getSceneX();
                                y = (int) mouseEvent.getSceneY();

                            }
                        });

                        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                stage.setX(mouseEvent.getScreenX() - x);
                                stage.setY(mouseEvent.getScreenY() - y);
                            }
                        });


                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }
            }


        }


    }

    @javafx.fxml.FXML
    public void cargarDatosTablaMarca(ActionEvent event) {

        datos = movilesDAO.obtenerMovilesconMarca(txtSearch.getText());
        colID.setCellValueFactory(new PropertyValueFactory<Moviles, String>("id"));
        colModelo.setCellValueFactory(new PropertyValueFactory<Moviles, String>("Modelo"));
        colMarca.setCellValueFactory(new PropertyValueFactory<Moviles, String>("Marca"));
        colAlmacenamiento.setCellValueFactory(new PropertyValueFactory<Moviles, String>("Almacenamiento"));
        colRAM.setCellValueFactory(new PropertyValueFactory<Moviles, String>("ram"));
        colSO.setCellValueFactory(new PropertyValueFactory<Moviles, String>("sistemaOperativo"));
        colCPU.setCellValueFactory(new PropertyValueFactory<Moviles, String>("cpu"));
        colBateria.setCellValueFactory(new PropertyValueFactory<Moviles, String>("bateria"));
        colPrecioS.setCellValueFactory(new PropertyValueFactory<Moviles, String>("precioSalida"));
        colPrecioA.setCellValueFactory(new PropertyValueFactory<Moviles, String>("Precio"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Moviles, String>("Fecha"));

        tablaBusqueda.setItems(datos);

    }

    @javafx.fxml.FXML
    public void cerrarApp(Event event) {

        System.exit(0);

    }


    private void cargarDatosTabla() {
        datos = movilesDAO.obtenerMoviles();
        colID.setCellValueFactory(new PropertyValueFactory<Moviles, String>("id"));
        colModelo.setCellValueFactory(new PropertyValueFactory<Moviles, String>("Modelo"));
        colMarca.setCellValueFactory(new PropertyValueFactory<Moviles, String>("Marca"));
        colAlmacenamiento.setCellValueFactory(new PropertyValueFactory<Moviles, String>("Almacenamiento"));
        colRAM.setCellValueFactory(new PropertyValueFactory<Moviles, String>("ram"));
        colSO.setCellValueFactory(new PropertyValueFactory<Moviles, String>("sistemaOperativo"));
        colCPU.setCellValueFactory(new PropertyValueFactory<Moviles, String>("cpu"));
        colBateria.setCellValueFactory(new PropertyValueFactory<Moviles, String>("bateria"));
        colPrecioS.setCellValueFactory(new PropertyValueFactory<Moviles, String>("precioSalida"));
        colPrecioA.setCellValueFactory(new PropertyValueFactory<Moviles, String>("Precio"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Moviles, String>("Fecha"));

        tablaBusqueda.setItems(datos);

    }


}
