package com.example.bdtel;

import com.example.bdtel.DAO.MarcasDAO;
import com.example.bdtel.Modelo.Marcas;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class VentanaMarcas extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private int xx = 0, yy = 0;
    private Executor exec;
    private MarcasDAO marcasDAO = new MarcasDAO();
    private Marcas marcas;
    private Marcas marcasAUX;

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
            String SQL = "INSERT INTO Marcas(" + "Marca )" + "VALUES (?)";

            PreparedStatement st = conexion.prepareStatement(SQL);
            st.setString(1, marca.getMarca());

            registrosAfectadosConsulta = st.executeUpdate();
            st.close();
            conexion.close();

            if (registrosAfectadosConsulta == 1) {

                comprobacion = true;

            } else {

                comprobacion = false;
            }

        } catch (Exception e) {

        }


        return comprobacion;
    }

    public void actualizarTabla(TableView tabla) {

        Task<List<Marcas>> task = new Task<List<Marcas>>() {
            @Override
            public ObservableList<Marcas> call() throws Exception {
                System.out.println("act");
                return marcasDAO.obtenerMarcas();
            }
        };
        task.setOnFailed(e -> task.getException().printStackTrace());
        task.setOnSucceeded(e -> tabla.setItems((ObservableList<Marcas>) task.getValue()));
        exec.execute(task);

    }

    public void insertarDatos(Marcas marca) {

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                System.out.println("inserta: " + marca.getMarca());
                return marcasDAO.anadirMarca(marca);
            }


        };
        exec.execute(task);
    }

    public void borrarDatos(Marcas marca) {

        marcasDAO.borrarMarca(marca);

    }

    public void actualizarDatos(Marcas marca, String vieja) {

        marcasDAO.actualizarMarca(marca, vieja);

    }

    @Override
    public void start(Stage newWindow) {

        marcasAUX = new Marcas("");


        exec = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });

        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        Pane pane = new Pane();
        pane.setStyle("-fx-background-color:  #013E88;");
        pane.setPrefWidth(698);
        pane.setPrefHeight(81);

        Label lblTitulo = new Label("MARCAS DE TELÉFONOS");
        lblTitulo.setStyle("-fx-text-fill: #ffff; " + "-fx-font-family: Comic Sans MS Bold;" + "-fx-font-size: 24px;" + "-fx-font-weight: bold");

        lblTitulo.setLayoutX(33);
        lblTitulo.setLayoutY(25);

        Label x = new Label("❌");
        x.setId("cierre");

        x.setPrefWidth(22);
        x.setPrefHeight(27);

        x.setLayoutX(662);
        x.setLayoutY(12);

        EventHandler<MouseEvent> eventoCierre = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) x.getScene().getWindow();
                stage.close();
            }
        };

        x.addEventHandler(MouseEvent.MOUSE_CLICKED, eventoCierre);

        pane.getChildren().addAll(lblTitulo, x);


        gridPane.add(pane, 0, 0);


        GridPane gridPane2 = new GridPane();
        Label lblMarca = new Label("Marca");
        lblMarca.setStyle("-fx-font-family: Arial;" + "-fx-text-fill: #000000;" + "-fx-font-size:14;" + "-fx-font-weight:bold;" + "-fx-alignment:center;");

        HBox contlblMarca = new HBox(lblMarca);
        gridPane2.add(lblMarca, 0, 0);

        TextField txtMarca = new TextField();
        txtMarca.setPromptText("Escribe la marca.");

        txtMarca.setFocusTraversable(false);

        HBox contMarca = new HBox(txtMarca);

        contMarca.setPadding(new Insets(10, 0, 0, 30));


        gridPane2.add(contMarca, 1, 0);

        gridPane2.setPadding(new Insets(20, 0, 20, 20));

        GridPane gridPaneBotones = new GridPane();

        Button btnAlta = new Button("✔");

        btnAlta.setStyle("-fx-text-fill: #00f70c;" + "-fx-font-size:24");


        btnAlta.setFocusTraversable(false);

        gridPaneBotones.add(btnAlta, 0, 0);

        Button btnAct = new Button("✏");

        btnAct.setStyle("-fx-text-fill: #f59e00;" + "-fx-font-size:24");

        HBox contAct = new HBox(btnAct);

        contAct.setPadding(new Insets(0, 30, 0, 30));

        btnAct.setFocusTraversable(false);

        gridPaneBotones.add(contAct, 1, 0);

        Button btnBorrar = new Button("❌");

        btnBorrar.setStyle("-fx-text-fill:#f50000;" + "-fx-font-size:24");

        btnBorrar.setFocusTraversable(false);


        gridPaneBotones.add(btnBorrar, 2, 0);

        gridPane2.add(gridPaneBotones, 2, 0);


        gridPaneBotones.setPadding(new Insets(0, 0, 0, 200));


        gridPane.add(gridPane2, 0, 1);


        TableView tablaMarca = new TableView();
        TableColumn colMarcas = new TableColumn("Marca");

        tablaMarca.setEditable(false);
        tablaMarca.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colMarcas.setStyle("-fx-alignment: CENTER;");
        tablaMarca.getColumns().addAll(colMarcas);

        tablaMarca.setFocusTraversable(false);


        gridPane.add(tablaMarca, 0, 2);


        Scene scene = new Scene(gridPane, 698, 575);
        scene.getStylesheets().add(this.getClass().getResource("/css/ventanaEjecucion.css").toExternalForm());

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                xx = (int) mouseEvent.getSceneX();
                yy = (int) mouseEvent.getSceneY();

            }
        });

        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                newWindow.setX(mouseEvent.getScreenX() - xx);
                newWindow.setY(mouseEvent.getScreenY() - yy);
            }
        });

        newWindow.setScene(scene);

        colMarcas.setCellValueFactory(new PropertyValueFactory<Marcas, String>("Marca"));

        actualizarTabla(tablaMarca);

        tablaMarca.setRowFactory(tv -> {
            TableRow<Marcas> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {

                    txtMarca.setText(row.getItem().getMarca());

                }
            });
            return row;
        });

        EventHandler<MouseEvent> eventoAlta = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (anadirMarca(marcasAUX)) {

                    Alert alert;

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Dato insertado");
                    alert.setContentText("Dato insertado correctamente");
                    alert.showAndWait();


                } else {

                    Alert alert;

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("Error al insertar.");
                    alert.showAndWait();


                }

                actualizarTabla(tablaMarca);

            }


        };


        btnAlta.addEventHandler(MouseEvent.MOUSE_CLICKED, eventoAlta);

        EventHandler<MouseEvent> eventoBorrar = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                Marcas m = (Marcas) tablaMarca.getSelectionModel().getSelectedItem();
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


                        if (marcasDAO.borrarMarca(m)) {

                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setTitle("Insertado");
                            alert.setContentText("Marca borrada correctamente");
                            alert.showAndWait();

                        } else {

                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText(null);
                            alert.setTitle("Error");
                            alert.setContentText("Marca no borrado.");
                            alert.showAndWait();

                        }


                    }

                }

                actualizarTabla(tablaMarca);
            }


        };

        btnBorrar.addEventHandler(MouseEvent.MOUSE_CLICKED, eventoBorrar);

        EventHandler<MouseEvent> eventoActualizar = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                Marcas m = (Marcas) tablaMarca.getSelectionModel().getSelectedItem();

                String vieja = m.getMarca();


                actualizarDatos(marcasAUX, vieja);


                actualizarTabla(tablaMarca);

            }


        };

        btnAct.addEventHandler(MouseEvent.MOUSE_CLICKED, eventoActualizar);
        txtMarca.textProperty().bindBidirectional(marcasAUX.marcaProperty());

        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.show();


    }
}
