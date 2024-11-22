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

public class controllerPopUpCambioContraseña {

    @FXML
    private TextField txtConfContraseña;

    @FXML
    private TextField txtContraseña;

    @FXML
    void cambiarContraseña(ActionEvent event) {
        if (txtContraseña.getText().isEmpty() || txtConfContraseña.getText().isEmpty()) {
            Alertas credencialesIncorrectas = new Alertas(Alert.AlertType.ERROR, "CAMPOS EN BLANCO", 
                        "Alguno de los campos está en blanco", "Intentelo de nuevo.");
                credencialesIncorrectas.mostrarAlerta();
        } else {
            if (!txtContraseña.getText().equals(txtConfContraseña.getText())) {
                Alertas credencialesIncorrectas = new Alertas(Alert.AlertType.ERROR, "CONTRASEÑAS DIFERENTES", 
                        "Las contraseñas no coinciden.", "Intentelo de nuevo.");
                credencialesIncorrectas.mostrarAlerta();
            } else {
                cerrar();
            }
        }
    }
    
    @FXML
    void cancelar(ActionEvent event) {
        cerrar();
    }

    private void cerrar() {
        Stage ventana = (Stage) txtConfContraseña.getScene().getWindow();
        ventana.close();
    }
}
