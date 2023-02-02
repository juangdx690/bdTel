package com.example.bdtel.Controladores;


import com.example.bdtel.DAO.MarcasDAO;
import com.example.bdtel.DAO.MovilesDAO;
import com.example.bdtel.Main;
import com.example.bdtel.VentanaMarcas;
import com.example.bdtel.Modelo.Marcas;
import com.example.bdtel.Modelo.Moviles;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
    @javafx.fxml.FXML
    private TextField txtmod;
    @javafx.fxml.FXML
    private TextField txtfechasal;
    @javafx.fxml.FXML
    private Pane paneltitulo;
    @javafx.fxml.FXML
    private AnchorPane contenedor;
    @javafx.fxml.FXML
    private Label pestanaMarca;

    private Executor exec;


    private Marcas marcasAUX;
    @FXML
    private Button reporte;
    @FXML
    private Button showSubreport;

    public void initialize() {


        cargarGestorDobleCLick();

        exec = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });

        cargarDatosTabla();

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
                alert.setContentText("Tienes que seleccionar una fila de la tabla.");
                alert.showAndWait();

            } else {

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

        if (event.getSource() == btnPagBorrar) {

            Moviles m = (Moviles) tablaBusqueda.getSelectionModel().getSelectedItem();

            Alert alert;

            if (Objects.isNull(m)) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Tienes que seleccionar una fila de la tabla.");
                alert.showAndWait();

            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("¿Estas seguro de que quieres borrar el móvil: " + m.getModelo() + " ?");
                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {


                    if (movilesDAO.borrarMovil(m)) {

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Insertado");
                        alert.setContentText("Móvil borrado correctamente");
                        alert.showAndWait();

                    } else {

                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Error");
                        alert.setContentText("Móvil no borrado.");
                        alert.showAndWait();

                    }

                    cargarDatosTabla();

                }

            }


        }

    }


    @Deprecated
    public void cargarDatosTablaMarca(ActionEvent event) {


        Task<List<Moviles>> task = new Task<List<Moviles>>() {
            @Override
            public ObservableList<Moviles> call() throws Exception {
                return movilesDAO.obtenerMovilesconMarca(txtSearch.getText(), txtmod.getText(), txtfechasal.getText());
            }
        };
        task.setOnFailed(e -> task.getException().printStackTrace());
        task.setOnSucceeded(e -> tablaBusqueda.setItems((ObservableList<Moviles>) task.getValue()));
        exec.execute(task);

    }

    @javafx.fxml.FXML
    public void desTabla() {

        tablaBusqueda.getSelectionModel().select(null);


    }

    private void cargarGestorDobleCLick() {
        tablaBusqueda.setRowFactory(tv -> {
            TableRow<Moviles> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {

                    Moviles m = (Moviles) tablaBusqueda.getSelectionModel().getSelectedItem();

                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("verMovil.fxml"));

                    try {
                        Parent root = loader.load();

                        ControladorVerMoviles controladorVM = loader.getController();


                        controladorVM.cargarDatos(m, movilesDAO.obtenerImagen(m.getId()));


                        Scene scene = new Scene(root, 698, 575);

                        Stage stage = new Stage();

                        stage.setScene(scene);

                        Stage stagePrin = (Stage) this.txtTitulo.getScene().getWindow();
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.show();


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
            });
            return row;
        });
    }

    @javafx.fxml.FXML
    public void cerrarApp(Event event) {

        System.exit(0);

    }


    private void cargarDatosTabla() {
        Task<List<Moviles>> task = new Task<List<Moviles>>() {
            @Override
            public ObservableList<Moviles> call() throws Exception {
                return movilesDAO.obtenerMoviles();
            }
        };
        task.setOnFailed(e -> task.getException().printStackTrace());
        task.setOnSucceeded(e -> tablaBusqueda.setItems((ObservableList<Moviles>) task.getValue()));
        exec.execute(task);

    }


    @FXML
    public void abrirPaginaMarcas(Event event) {

        Stage marca = new Stage();
        VentanaMarcas vtnMarcas = new VentanaMarcas();

        vtnMarcas.start(marca);


    }

    @FXML
    public void showReport(ActionEvent actionEvent) {
        try {
            // --- Show Jasper Report on click-----
            new Report().showReportSimple();
        } catch (ClassNotFoundException | JRException | SQLException e1) {
            e1.printStackTrace();
        }

    }

    @FXML
    public void showSubreport(ActionEvent actionEvent) {


        TextField textField = new TextField();

        Label label = new Label("Escribe la marca para el subreporte");

        HBox hbox = new HBox(label, textField);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Marca");
        alert.setHeaderText(null);
        alert.setGraphic(hbox);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            String enteredText = textField.getText();
            try {
                // --- Show Jasper Report on click-----
                new Report().showReportConSubreport(enteredText);
            } catch (ClassNotFoundException | JRException | SQLException e1) {
                e1.printStackTrace();
            }
        }


    }
}
