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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controllerEditarEquipo implements Initializable{
    List<String> datos = new ArrayList<>();

    @FXML
    private TextField txtNuevoFormato;

    @FXML
    private TextField txtNuevoNombre;
    
    @FXML
    void cancelar(ActionEvent event) {
        ((Stage) this.txtNuevoFormato.getScene().getWindow()).setUserData(datos);
        cerrar();
    }

    @FXML
    void editarEquipo(ActionEvent event) {
        if (txtNuevoNombre.getText().isEmpty() && txtNuevoFormato.getText().isEmpty()) {
            Alertas credencialesIncorrectas = new Alertas(Alert.AlertType.ERROR, "CAMPOS EN BLANCO", 
                        "Debe rellenar al menos uno de los campos.", "Intentelo de nuevo.");
                credencialesIncorrectas.mostrarAlerta();
        } else {
            datos = new ArrayList<>();
            datos.add(txtNuevoNombre.getText());
            datos.add(txtNuevoFormato.getText());
            ((Stage) this.txtNuevoFormato.getScene().getWindow()).setUserData(datos);
            cerrar();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private void cerrar() {
        Stage a = (Stage) this.txtNuevoFormato.getScene().getWindow();
        a.close();
    }
}
