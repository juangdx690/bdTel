package com.example.bdtel;

import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

public class Controlador {


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
    private GridPane pestanaAnadir;
    @javafx.fxml.FXML
    private Label lblModelo;
    @javafx.fxml.FXML
    private Label lblBateria;
    @javafx.fxml.FXML
    private Label lblProcesador;
    @javafx.fxml.FXML
    private Label lblSO;
    @javafx.fxml.FXML
    private Label lblRAM;
    @javafx.fxml.FXML
    private Label lblAlmacenamiento;
    @javafx.fxml.FXML
    private Label lblMarca;
    @javafx.fxml.FXML
    private Label lblPrecioS;
    @javafx.fxml.FXML
    private Label lblPrecioActual;
    @javafx.fxml.FXML
    private Label lblFecha;
    @javafx.fxml.FXML
    private TextField txtModelo;
    @javafx.fxml.FXML
    private TextField txtAlmacenamiento;
    @javafx.fxml.FXML
    private TextField txtRAM;
    @javafx.fxml.FXML
    private TextField txtSO;
    @javafx.fxml.FXML
    private TextField txtProcesador;
    @javafx.fxml.FXML
    private TextField txtBateria;
    @javafx.fxml.FXML
    private TextField txtPrecioSalida;
    @javafx.fxml.FXML
    private TextField txtPrecioActual;
    @javafx.fxml.FXML
    private Button btnCancelar;
    @javafx.fxml.FXML
    private Button btnAnadir;
    @javafx.fxml.FXML
    private VBox pestañaBusqueda;
    @javafx.fxml.FXML
    private GridPane pestanaBusqueda;


    private MovilesDAO movilesDAO = new MovilesDAO();
    private Moviles movilesAUX;

    private MarcasDAO marcasDAO = new MarcasDAO();
    private Marcas marcas;

    private ObservableList<Moviles> datos;
    private ObservableList<Marcas> datos2;

    private ObservableList<String> datos2String;
    @javafx.fxml.FXML
    private ComboBox listaMarca;
    @javafx.fxml.FXML
    private TextField txtSearch;
    @javafx.fxml.FXML
    private DatePicker txtfecha;
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


    public void initialize() {

        cargarDatosTabla();
        cargarDatosComboBox();
        movilesAUX = new Moviles(0, "", "", 0, 0,
                "", "", 0, 0, 0, null);
        realizarBindingsMoviles(movilesAUX);

        formatoFecha();


    }




    private void cargarDatosComboBox() {

        datos2 = marcasDAO.obtenerMarcas();
        int i = 0;


        while (i < datos2.size()) {

            listaMarca.getItems().add(datos2.get(i).getMarca());
            listaMarcaAct.getItems().add(datos2.get(i).getMarca());
            System.out.println(datos2.get(i).getMarca());
            i++;
        }


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

    public void realizarBindingsMoviles(Moviles moviles) {

        txtModelo.textProperty().bindBidirectional(movilesAUX.modeloProperty());

        listaMarca.valueProperty().bindBidirectional(movilesAUX.marcaProperty());

        txtAlmacenamiento.textProperty().bindBidirectional(movilesAUX.almacenamientoProperty(), new NumberStringConverter());
        txtRAM.textProperty().bindBidirectional(movilesAUX.ramProperty(), new NumberStringConverter());
        txtSO.textProperty().bindBidirectional(movilesAUX.sistemaOperativoProperty());


        txtProcesador.textProperty().bindBidirectional(movilesAUX.cpuProperty());
        txtBateria.textProperty().bindBidirectional(movilesAUX.bateriaProperty(), new NumberStringConverter());
        txtPrecioSalida.textProperty().bindBidirectional(movilesAUX.precioSalidaProperty(), new NumberStringConverter());
        txtPrecioActual.textProperty().bindBidirectional(movilesAUX.precioProperty(), new NumberStringConverter());

        txtfecha.valueProperty().bindBidirectional(movilesAUX.dateProperty());


    }


    @javafx.fxml.FXML
    public void pestanaClicks(ActionEvent event) {

        if (event.getSource() == btnPagBuscar) {

            txtTitulo.setText("BÚSQUEDA DE TELÉFONOS");
            pestanaBusqueda.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
            pestanaBusqueda.toFront();

        } else if (event.getSource() == btnPagAnadir) {



                txtTitulo.setText("AÑADIR TELÉFONOS");
                pestanaAnadir.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
                pestanaAnadir.toFront();



        }else if (event.getSource() == btnPagActualizar) {

            cargarPaginaActualizar();

        }

    }


    @javafx.fxml.FXML
    public void cerrarApp(Event event) {

        System.exit(0);

    }

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
    public void onAltaClicked(ActionEvent actionEvent) {
        movilesDAO.anadirMovil(movilesAUX);

        cargarDatosTabla();

    }

    public void formatoFecha() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        txtfecha.setConverter(new LocalDateStringConverter(formatter, null));
        txtfechaAct.setConverter(new LocalDateStringConverter(formatter, null));

    }


    public void cargarPaginaActualizar() {

        Moviles m = (Moviles) tablaBusqueda.getSelectionModel().getSelectedItem();


        Alert alert;

        if (Objects.isNull(m)){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Tienes que seleccionar una id");
            alert.showAndWait();
        }else{

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Actualización del producto");
            alert.setContentText("¿Estas seguro de que quieres actualizar los datos del movil: "+ m.getModelo()+" ?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {

                txtModeloAct.setText(m.getModelo());
                listaMarcaAct.getSelectionModel().select(m.getMarca());
                txtAlmacenamientoAct.setText(String.valueOf(m.getAlmacenamiento()));
                txtRAMAct.setText(String.valueOf(m.getRam()));
                txtSOAct.setText(m.getSistemaOperativo());
                txtProcesadorAct.setText(m.getCpu());
                txtBateriaAct.setText(String.valueOf(m.getBateria()));
                txtPrecioSalidaAct.setText(String.valueOf(m.getPrecioSalida()));
                txtPrecioActualAct.setText(String.valueOf(m.getPrecio()));

                txtTitulo.setText("ACTUALIZACIÓN MÓVIL: "+ (m.getModelo()).toUpperCase());
                pestanaActualizar.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
                pestanaActualizar.toFront();

            }

        }




    }

    @javafx.fxml.FXML
    public void a(Event event) {
    }
}
