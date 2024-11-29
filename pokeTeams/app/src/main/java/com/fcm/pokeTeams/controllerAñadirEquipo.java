/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author DFran49
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class controllerAñadirEquipo implements Initializable {
    List<ValidationSupport> validadores;

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
        boolean todoOK = true;
        for (ValidationSupport validationSupport : validadores) {
            todoOK = (todoOK && validationSupport.getValidationResult().getErrors().isEmpty());
        }

        if (todoOK) {
            Stage ventana = (Stage) txtFormato.getScene().getWindow();
            List<String> datos = new ArrayList<>();
            datos.add(txtNombre.getText());
            datos.add(txtFormato.getText());
            ventana.setUserData(datos);
            ventana.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ValidationSupport vSNombre = new ValidationSupport();
        vSNombre.registerValidator(txtNombre, Validator.createPredicateValidator(
            texto -> {
                if (texto == null || texto.toString().isEmpty()) {
                return false;
                }
                try {
                    int numero = texto.toString().length();
                    return numero >= 3 && numero <= 20 && txtNombre.getText().matches("^[A-Za-z0-9. ]+$");
                } catch (NumberFormatException e) {
                    return false;
                }
            },
            "El nombre debe tener mínimo 3 caracteres y 20 de máximo y solo puede contener letras, números o puntos"
        ));
        
        ValidationSupport vSFormato = new ValidationSupport();
        vSFormato.registerValidator(txtFormato, Validator.createPredicateValidator(
            texto -> {
                if (texto == null || texto.toString().isEmpty()) {
                return false;
                }
                try {
                    int numero = texto.toString().length();
                    return numero >= 3 && numero <= 20 && txtFormato.getText().matches("^[A-Za-z0-9. ]+$");
                } catch (NumberFormatException e) {
                    return false;
                }
            },
            "El formato debe tener mínimo 3 caracteres y 20 de máximo y solo puede contener letras, números o puntos"
        ));
        
        validadores = new ArrayList<>();
        validadores.addAll(Arrays.asList(vSNombre, vSFormato));

        Platform.runLater(() -> {
            for (ValidationSupport validationSupport : validadores) {
                validationSupport.initInitialDecoration();
            }
        });
    }

}
