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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class controllerEliminar implements Initializable {

    List<ValidationSupport> validadores;

    @FXML
    private TextField txtEliminar;

    @FXML
    void cancelar(ActionEvent event) {
        cerrar();
    }

    @FXML
    void eliminar(ActionEvent event) {
        Stage a = (Stage) txtEliminar.getScene().getWindow();
        boolean todoOK = true;
        for (ValidationSupport validationSupport : validadores) {
            todoOK = (todoOK && validationSupport.getValidationResult().getErrors().isEmpty());
        }

        if (!todoOK) {
            Alertas credencialesIncorrectas = new Alertas(Alert.AlertType.ERROR, "NO CONFIRMADO",
                    "Debe escribir \"ELIMINAR\" para poder eliminar el elemento.", "Intentelo de nuevo.");
            credencialesIncorrectas.mostrarAlerta();

            a.setUserData(false);
        } else {
            a.setUserData(true);
            cerrar();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ValidationSupport vSNombre = new ValidationSupport();
        vSNombre.registerValidator(txtEliminar, Validator.createPredicateValidator(
                texto -> {
                    try {
                        return texto.equals("ELIMINAR");
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                "Debe escribir esáctamente \"ELIMINAR\" para poder eliminar el elemento"
        ));

        validadores = new ArrayList<>();
        validadores.addAll(Arrays.asList(vSNombre));

        Platform.runLater(() -> {
            for (ValidationSupport validationSupport : validadores) {
                validationSupport.initInitialDecoration();
            }
        });
    }

    private void cerrar() {
        Stage ventana = (Stage) txtEliminar.getScene().getWindow();
        ventana.close();
    }
}
