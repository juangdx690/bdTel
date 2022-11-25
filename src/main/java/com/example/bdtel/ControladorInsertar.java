package com.example.bdtel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ControladorInsertar {


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
    private GridPane pestanaActualizar;
    @javafx.fxml.FXML
    private Label lblModeloAct;
    @javafx.fxml.FXML
    private Label lblBateriaAct;
    @javafx.fxml.FXML
    private Label lblProcesadorAct;
    @javafx.fxml.FXML
    private Label lblSOAct;
    @javafx.fxml.FXML
    private Label lblRAMAct;
    @javafx.fxml.FXML
    private Label lblAlmacenamientoAct;
    @javafx.fxml.FXML
    private Label lblMarcaAct;
    @javafx.fxml.FXML
    private Label lblPrecioSAct;
    @javafx.fxml.FXML
    private Label lblPrecioActualAct;
    @javafx.fxml.FXML
    private Label lblFechaAct;
    @javafx.fxml.FXML
    private TextField txtModeloAct;
    @javafx.fxml.FXML
    private TextField txtAlmacenamientoAct;
    @javafx.fxml.FXML
    private TextField txtRAMAct;
    @javafx.fxml.FXML
    private TextField txtSOAct;
    @javafx.fxml.FXML
    private TextField txtProcesadorAct;
    @javafx.fxml.FXML
    private TextField txtBateriaAct;
    @javafx.fxml.FXML
    private TextField txtPrecioSalidaAct;
    @javafx.fxml.FXML
    private TextField txtPrecioActualAct;
    @javafx.fxml.FXML
    private Button btnCancelarAct;
    @javafx.fxml.FXML
    private Button btnAnadirAct;
    @javafx.fxml.FXML
    private ComboBox listaMarcaAct;
    @javafx.fxml.FXML
    private DatePicker txtfechaAct;

    private MovilesDAO movilesDAO = new MovilesDAO();
    private Moviles movilesAUX;

    private MarcasDAO marcasDAO = new MarcasDAO();
    private Marcas marcas;

    private ObservableList<Moviles> datos;
    private ObservableList<Marcas> datos2;


    private int x = 0;
    private int y = 0;
    @javafx.fxml.FXML
    private Label lblImgAct;
    @javafx.fxml.FXML
    private TextField txtRutaFichero;
    @javafx.fxml.FXML
    private Button btnBuscarImg;

    public void initialize() {


        cargarDatosComboBox();
        movilesAUX = new Moviles(0, "", "", 0, 0,
                "", "", 0, 0, 0, null);
        realizarBindingsMoviles(movilesAUX);
        formatoFecha();



        quitar0campos();
    }

    private void cargarDatosComboBox() {

        datos2 = marcasDAO.obtenerMarcas();
        int i = 0;


        while (i < datos2.size()) {


            listaMarcaAct.getItems().add(datos2.get(i).getMarca());
            System.out.println(datos2.get(i).getMarca());
            i++;
        }
    }

    public void quitar0campos() {

        if (txtAlmacenamientoAct.getText() == "0") {

            txtAlmacenamientoAct.setText("");

        }

    }

    @javafx.fxml.FXML
    public void pestanaClicks(ActionEvent event) {


        if (event.getSource() == btnPagBuscar) {


            FXMLLoader loader = new FXMLLoader(Main.class.getResource("buscar.fxml"));

            try {
                Parent root = loader.load();

                ControladorBuscar controladorBus = loader.getController();

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

    public void realizarBindingsMoviles(Moviles moviles) {

        txtModeloAct.textProperty().bindBidirectional(movilesAUX.modeloProperty());

        listaMarcaAct.valueProperty().bindBidirectional(movilesAUX.marcaProperty());

        txtAlmacenamientoAct.textProperty().bindBidirectional(movilesAUX.almacenamientoProperty(), new NumberStringConverter());
        txtRAMAct.textProperty().bindBidirectional(movilesAUX.ramProperty(), new NumberStringConverter());
        txtSOAct.textProperty().bindBidirectional(movilesAUX.sistemaOperativoProperty());


        txtProcesadorAct.textProperty().bindBidirectional(movilesAUX.cpuProperty());
        txtBateriaAct.textProperty().bindBidirectional(movilesAUX.bateriaProperty(), new NumberStringConverter());
        txtPrecioSalidaAct.textProperty().bindBidirectional(movilesAUX.precioSalidaProperty(), new NumberStringConverter());
        txtPrecioActualAct.textProperty().bindBidirectional(movilesAUX.precioProperty(), new NumberStringConverter());

        txtfechaAct.valueProperty().bindBidirectional(movilesAUX.dateProperty());


    }

    @javafx.fxml.FXML
    public void onAltaClicked(ActionEvent actionEvent) {



        movilesDAO.anadirMovil(movilesAUX, txtRutaFichero.getText());


    }


    public void formatoFecha() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        txtfechaAct.setConverter(new LocalDateStringConverter(formatter, null));

    }

    @javafx.fxml.FXML
    public void cerrarApp(Event event) {

        System.exit(0);

    }





    @javafx.fxml.FXML
    public void cerrarInsertar(Event event) {

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("buscar.fxml"));

        try {
            Parent root = loader.load();

            ControladorBuscar controladorBus = loader.getController();

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


    @FXML
    public void buscarRutaImg(ActionEvent actionEvent) {

        final FileChooser fileChooser = new FileChooser();

        File archivo =  fileChooser.showOpenDialog(null);

        Stage stage = (Stage) listaMarcaAct.getScene().getWindow();


        if (archivo != null){

            txtRutaFichero.setText(archivo.getAbsolutePath());

        }

    }

    @FXML
    public void soloNumeros(KeyEvent event) {

        txtAlmacenamientoAct.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtAlmacenamientoAct.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }
}
