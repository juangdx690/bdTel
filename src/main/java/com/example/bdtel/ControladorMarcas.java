package com.example.bdtel;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ControladorMarcas {
    @javafx.fxml.FXML
    private Label txtTitulo;
    @javafx.fxml.FXML
    private Label btnSalir;
    @javafx.fxml.FXML
    private GridPane pestanaActualizar;
    @javafx.fxml.FXML
    private Label lblMarcaAct;
    @javafx.fxml.FXML
    private Label txtMarcaAct;
    @javafx.fxml.FXML
    private TextField txtMarca;
    @javafx.fxml.FXML
    private Button btnAnadir;
    @javafx.fxml.FXML
    private Button btnActualizar;
    @javafx.fxml.FXML
    private Button btnBorrar;
    @javafx.fxml.FXML
    private Button btnBuscar;
    @javafx.fxml.FXML
    private TableView tablaMarcas;
    @javafx.fxml.FXML
    private TableColumn colMarca;

    private MarcasDAO marcasDAO = new MarcasDAO();
    private Marcas marcas;
    private Marcas marcasAUX;


    private Executor exec;

    public void initialize() {

        marcasAUX = new Marcas("");


        exec = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });

        cargarGestorDobleCLick();

        realizarBinding();
        cargarDatosTabla();

        colMarca.setCellValueFactory(new PropertyValueFactory<Marcas, String>("Marca"));


    }


    private void cargarGestorDobleCLick() {
        tablaMarcas.setRowFactory(tv -> {
            TableRow<Marcas> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {

                    txtMarca.setText(row.getItem().getMarca());

                }
            });
            return row;
        });
    }

    public void realizarBinding() {

        txtMarca.textProperty().bindBidirectional(marcasAUX.marcaProperty());

    }

    public void cargarDatosTabla() {


        Task<List<Marcas>> task = new Task<List<Marcas>>() {
            @Override
            public ObservableList<Marcas> call() throws Exception {
                return marcasDAO.obtenerMarcas();
            }
        };
        task.setOnFailed(e -> task.getException().printStackTrace());
        task.setOnSucceeded(e -> tablaMarcas.setItems((ObservableList<Marcas>) task.getValue()));
        exec.execute(task);

    }

    @javafx.fxml.FXML
    public void cerrarApp(Event event) {

        Stage stage = (Stage) tablaMarcas.getScene().getWindow();

        stage.close();

    }

    @javafx.fxml.FXML
    public void insertar(ActionEvent event) {

        marcasDAO.anadirMarca(marcasAUX);
        cargarDatosTabla();

    }

    @javafx.fxml.FXML
    public void actualizar(ActionEvent event) {

        Marcas m = (Marcas) tablaMarcas.getSelectionModel().getSelectedItem();

        String marcavieja = m.getMarca();

        marcasDAO.actualizarMarca(marcasAUX, marcavieja);
        cargarDatosTabla();


    }

    @javafx.fxml.FXML
    public void borrar(ActionEvent event) {

        Marcas m = (Marcas) tablaMarcas.getSelectionModel().getSelectedItem();


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
            alert.setContentText("¿Estas seguro de que quieres borrar la marca: " + m.getMarca() + " ?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {


                marcasDAO.borrarMarca(m);
                cargarDatosTabla();

            }

        }




    }

}
