/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author DFran49
 */

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controllerAñadirEquipo {

    @FXML
    private TextField txtFormato;

    @FXML
    private TextField txtNombre;
    
    @FXML
    void cancelar(ActionEvent event) {
        Stage ventana = (Stage) txtFormato.getScene().getWindow();
        ventana.setUserData(null);
        ventana.close();
    }

    @FXML
    void crearEquipo(ActionEvent event) {
        Stage ventana = (Stage) txtFormato.getScene().getWindow();
        List<String> datos = new ArrayList<>();
        datos.add(txtFormato.getText());
        datos.add(txtNombre.getText());
        ventana.setUserData(datos);
        ventana.close();
    }

}
