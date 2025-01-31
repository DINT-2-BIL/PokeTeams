/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author DFran49
 */
import com.fcm.pokeTeams.DAO.EntrenadorDAO;
import com.fcm.pokeTeams.modelos.Entrenador;
import com.fcm.pokeTeams.util.CargadorFXML;
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

public class controllerPopUpCambioNombre implements Initializable {

    private controllerCore cCore = CargadorFXML.getInstance().getControllerCore();
    private List<ValidationSupport> validadores;

    @FXML
    private TextField txtNuevoNombre;

    @FXML
    void cambiarNombre(ActionEvent event) {
        boolean todoOK = true;
        for (ValidationSupport validationSupport : validadores) {
            todoOK = (todoOK && validationSupport.getValidationResult().getErrors().isEmpty());
        }

        if (todoOK) {
            Entrenador entrenador = (Entrenador) this.txtNuevoNombre.getScene().getWindow().getUserData();
            entrenador.setNombre(txtNuevoNombre.getText());
            EntrenadorDAO.getInstance().update(entrenador);
            cCore.entrenador = entrenador;
            cCore.refrescarUser();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ValidationSupport vSNombre = new ValidationSupport();
        vSNombre.registerValidator(txtNuevoNombre, Validator.createPredicateValidator(
                texto -> {
                    if (texto == null || texto.toString().isEmpty()) {
                        return false;
                    }
                    try {
                        int numero = texto.toString().length();
                        return numero >= 1 && numero <= 20 && txtNuevoNombre.getText().matches("^[\\p{L}0-9. ]{3,}$");
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                "El nombre puede tener mínimo 3 caracteres y 20 de máximo y solo puede contener letras, números o puntos"
        ));

        validadores = new ArrayList<>();
        validadores.addAll(Arrays.asList(vSNombre));

        Platform.runLater(() -> {
            for (ValidationSupport validationSupport : validadores) {
                validationSupport.initInitialDecoration();
            }
        });
    }
}
