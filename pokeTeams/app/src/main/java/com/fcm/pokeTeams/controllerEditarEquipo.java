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

public class controllerEditarEquipo implements Initializable {

    List<ValidationSupport> validadores;
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
        boolean todoOK = true;
        todoOK = validadores.get(0).getValidationResult().getErrors().isEmpty() || validadores.get(1).getValidationResult().getErrors().isEmpty();

        if (todoOK) {
            datos = new ArrayList<>();
            datos.add(txtNuevoNombre.getText());
            datos.add(txtNuevoFormato.getText());
            ((Stage) this.txtNuevoFormato.getScene().getWindow()).setUserData(datos);
            cerrar();
        } else {
            new Alertas(Alert.AlertType.WARNING, "Algo falló", "Faltan datos",
                    "Debe rellenar todos los campos y asegurarse de que siguen el formato que puede ver en el iconito de X pequeño").mostrarAlerta();
        }
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
                        return numero >= 3 && numero <= 20 && txtNuevoNombre.getText().matches("^[A-Za-z0-9. ]+$");
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                "El nombre debe tener mínimo 3 caracteres y 20 de máximo y solo puede contener letras, números o puntos"
        ));

        ValidationSupport vSFormato = new ValidationSupport();
        vSFormato.registerValidator(txtNuevoFormato, Validator.createPredicateValidator(
                texto -> {
                    if (texto == null || texto.toString().isEmpty()) {
                        return false;
                    }
                    try {
                        int numero = texto.toString().length();
                        return numero >= 3 && numero <= 20 && txtNuevoFormato.getText().matches("^[A-Za-z0-9. ]+$");
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

    private void cerrar() {
        Stage a = (Stage) this.txtNuevoFormato.getScene().getWindow();
        a.close();
    }
}
