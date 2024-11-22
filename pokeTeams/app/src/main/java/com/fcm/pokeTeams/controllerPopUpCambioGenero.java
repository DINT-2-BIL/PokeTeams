/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author DFran49
 */
import com.fcm.pokeTeams.util.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class controllerPopUpCambioGenero {

    @FXML
    private ToggleGroup genero;

    @FXML
    private RadioButton rbFemenino;

    @FXML
    private RadioButton rbMasculino;

    @FXML
    private RadioButton rbOtro;

    @FXML
    void cambiarGenero(ActionEvent event) {
        if (genero.getSelectedToggle() == null) {
            Alertas credencialesIncorrectas = new Alertas(Alert.AlertType.ERROR, "GÉNERO NO SELECCIONADO", 
                        "Debe seleccionar un género.", "Intentelo de nuevo.");
                credencialesIncorrectas.mostrarAlerta();
        } else {
            cerrar();
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        cerrar();
    }

    private void cerrar() {
        Stage ventana = (Stage) rbFemenino.getScene().getWindow();
        ventana.close();
    }
}
