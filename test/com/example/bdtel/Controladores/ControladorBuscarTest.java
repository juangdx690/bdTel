package com.example.bdtel.Controladores;

import com.example.bdtel.DAO.MovilesDAO;
import com.example.bdtel.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.ComboBoxMatchers;
import org.testfx.matcher.control.TableViewMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;



import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;



@ExtendWith(ApplicationExtension.class)
class ControladorBuscarTest {

    Pane mainroot;
    Stage mainstage;

    @Start
    public void start(Stage stage) throws IOException {
        mainroot = FXMLLoader.load(Main.class.getResource("buscar.fxml"));
        mainstage = stage;
        stage.setScene(new Scene(mainroot));
        stage.show();
        stage.toFront();
    }



    @Test
    void falloAlta(FxRobot robot){

        robot.clickOn("#paganiadir");



        robot.clickOn("#marca").clickOn("Samsung");
        FxAssert.verifyThat("#marca", ComboBoxMatchers.hasSelectedItem("Samsung"));

        robot.clickOn("#memoria");
        robot.type(KeyCode.BACK_SPACE);
        robot.write("0");
        FxAssert.verifyThat("#memoria", TextInputControlMatchers.hasText("0"));

        robot.clickOn("#ram");
        robot.type(KeyCode.BACK_SPACE);
        robot.write("2");
        FxAssert.verifyThat("#ram", TextInputControlMatchers.hasText("2"));

        robot.clickOn("#sos");
        robot.write("android el ejido");
        FxAssert.verifyThat("#sos", TextInputControlMatchers.hasText("android el ejido"));

        robot.clickOn("#procesador");
        robot.write("el ejido");
        FxAssert.verifyThat("#procesador", TextInputControlMatchers.hasText("el ejido"));

        robot.clickOn("#bateria");
        robot.type(KeyCode.BACK_SPACE);
        robot.write("3");
        FxAssert.verifyThat("#bateria", TextInputControlMatchers.hasText("3"));

        robot.clickOn("#psalida");
        robot.type(KeyCode.BACK_SPACE);
        robot.write("4");
        FxAssert.verifyThat("#psalida", TextInputControlMatchers.hasText("4"));

        robot.clickOn("#pactual");
        robot.type(KeyCode.BACK_SPACE);
        robot.write("5");
        FxAssert.verifyThat("#pactual", TextInputControlMatchers.hasText("5"));



        robot.clickOn("#enviarDatosABD");

        FxAssert.verifyThat(robot.window("Error"), WindowMatchers.isShowing());





    }

    @Test
    void insertardato(FxRobot robot) {


        robot.clickOn("#paganiadir");

        robot.clickOn("#modelo");
        robot.write("Samsung-Juan");
        FxAssert.verifyThat("#modelo", TextInputControlMatchers.hasText("Samsung-Juan"));

        robot.clickOn("#marca").clickOn("Samsung");
        FxAssert.verifyThat("#marca", ComboBoxMatchers.hasSelectedItem("Samsung"));

        robot.clickOn("#memoria");
        robot.type(KeyCode.BACK_SPACE);
        robot.write("0");
        FxAssert.verifyThat("#memoria", TextInputControlMatchers.hasText("0"));

        robot.clickOn("#ram");
        robot.type(KeyCode.BACK_SPACE);
        robot.write("2");
        FxAssert.verifyThat("#ram", TextInputControlMatchers.hasText("2"));

        robot.clickOn("#sos");
        robot.write("android el ejido");
        FxAssert.verifyThat("#sos", TextInputControlMatchers.hasText("android el ejido"));

        robot.clickOn("#procesador");
        robot.write("el ejido");
        FxAssert.verifyThat("#procesador", TextInputControlMatchers.hasText("el ejido"));

        robot.clickOn("#bateria");
        robot.type(KeyCode.BACK_SPACE);
        robot.write("3");
        FxAssert.verifyThat("#bateria", TextInputControlMatchers.hasText("3"));

        robot.clickOn("#psalida");
        robot.type(KeyCode.BACK_SPACE);
        robot.write("4");
        FxAssert.verifyThat("#psalida", TextInputControlMatchers.hasText("4"));

        robot.clickOn("#pactual");
        robot.type(KeyCode.BACK_SPACE);
        robot.write("5");
        FxAssert.verifyThat("#pactual", TextInputControlMatchers.hasText("5"));



        robot.clickOn("#enviarDatosABD");




        robot.clickOn("Aceptar");
        robot.clickOn("#pagBuscar");
        robot.sleep(1000);


        FxAssert.verifyThat("#tabla", TableViewMatchers.hasNumRows(1));



        FxAssert.verifyThat("#tabla", TableViewMatchers.containsRowAtIndex( 0, 1, "Samsung-Juan", "Samsung", 0, 2, "android el ejido", "el ejido", 3, 4.0, 5.0, null));

        robot.sleep(1000);




    }

}