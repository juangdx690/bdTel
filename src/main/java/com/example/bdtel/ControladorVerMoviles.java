package com.example.bdtel;

import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.io.InputStream;

public class ControladorVerMoviles {
    @javafx.fxml.FXML
    private Label txtTitulo;
    @javafx.fxml.FXML
    private ImageView img;
    @javafx.fxml.FXML
    private GridPane pestanaActualizar;
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

    private MovilesDAO movilesDAO = new MovilesDAO();
    private Moviles movilesAUX;
    @javafx.fxml.FXML
    private Label txtMarcaAct;
    @javafx.fxml.FXML
    private Label txtAlmacenamientoAct;
    @javafx.fxml.FXML
    private Label txtRamAct;
    @javafx.fxml.FXML
    private Label txtSOAct;
    @javafx.fxml.FXML
    private Label txtCPUAct;
    @javafx.fxml.FXML
    private Label txtBateriaAct;
    @javafx.fxml.FXML
    private Label txtPSalidaAct;
    @javafx.fxml.FXML
    private Label txtPActualAct;
    @javafx.fxml.FXML
    private Label txtFechaSalidaAct;
    @javafx.fxml.FXML
    private Label btnSalir;

    public void initialize() {


        movilesAUX = new Moviles(0, "", "", 0, 0,
                "", "", 0, 0, 0, null);





    }

    public void cargarDatos(Moviles m, InputStream is) {

        txtTitulo.setText(m.getModelo());
        txtMarcaAct.setText(m.getMarca());
        txtAlmacenamientoAct.setText(String.valueOf(m.getAlmacenamiento())+" GB");
        txtRamAct.setText(String.valueOf(m.getRam())+" GB");
        txtSOAct.setText(m.getSistemaOperativo());
        txtCPUAct.setText(m.getCpu());
        txtBateriaAct.setText(String.valueOf(m.getBateria())+" mAh");
        txtPSalidaAct.setText(String.valueOf(m.getPrecioSalida()+" €"));
        txtPActualAct.setText(String.valueOf(m.getPrecio())+" €");
        txtFechaSalidaAct.setText(m.getFecha());

        Image ima;
        if (is == null){

            ima = new Image(String.valueOf(getClass().getResource("/img/no_image.jpg").toExternalForm()));

        }else{

            ima = new Image(is);

        }



        img.setImage(ima);

    }

    @javafx.fxml.FXML
    public void cerrarApp(Event event) {

        try {


            Stage stagePrin = (Stage) this.txtTitulo.getScene().getWindow();

            stagePrin.close();

        }catch (Exception e){


        }

    }



}
