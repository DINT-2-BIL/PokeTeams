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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controllerPopUpCambioNombre {

    @FXML
    private TextField txtNuevoNombre;

    @FXML
    void cambiarNombre(ActionEvent event) {
        if (txtNuevoNombre.getText().isEmpty()) {
            Alertas credencialesIncorrectas = new Alertas(Alert.AlertType.ERROR, "NOMBRE EN BLANCO", 
                        "No ha introducido un nombre.", "Intentelo de nuevo.");
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
        Stage ventana = (Stage) txtNuevoNombre.getScene().getWindow();
        ventana.close();
    }
}
