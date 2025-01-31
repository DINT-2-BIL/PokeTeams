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

public class controllerPopUpCambioContraseña implements Initializable {

    private Entrenador entrenador;
    List<ValidationSupport> validadores;

    @FXML
    private TextField txtConfContraseña;

    @FXML
    private TextField txtContraseña;

    @FXML
    void cambiarContraseña(ActionEvent event) {
        boolean todoOK = true;
        for (ValidationSupport validationSupport : validadores) {
            todoOK = (todoOK && validationSupport.getValidationResult().getErrors().isEmpty());
        }

        if (todoOK) {
            entrenador.setContraseña(txtContraseña.getText());
            EntrenadorDAO.getInstance().update(entrenador);
            CargadorFXML.getInstance().getControllerCore().refrescarUser();
            cerrar();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ValidationSupport vSContraseña = new ValidationSupport();
        vSContraseña.registerValidator(txtContraseña, Validator.createPredicateValidator(
                texto -> {
                    String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
                    if (texto == null || texto.toString().isEmpty()) {
                        return false;
                    }
                    try {
                        int numero = texto.toString().length();
                        return numero >= 4 && txtContraseña.getText().matches(regex);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                "La contraseña debe tener mínimo 8 caracteres, 20 de máximo, tener una letra, un número y un carácter especial (@$!%*?&)"
        ));

        ValidationSupport vSContraseñaConf = new ValidationSupport();
        vSContraseñaConf.registerValidator(txtConfContraseña, Validator.createPredicateValidator(
                texto -> {
                    if (texto == null || texto.toString().isEmpty()) {
                        return false;
                    }
                    try {
                        return txtConfContraseña.getText().equals(txtContraseña.getText());
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                "La confirmación debe ser igual a la contraseña"
        ));

        validadores = new ArrayList<>();
        validadores.addAll(Arrays.asList(vSContraseña, vSContraseñaConf));

        Platform.runLater(() -> {
            entrenador = (Entrenador) this.txtContraseña.getScene().getWindow().getUserData();
            for (ValidationSupport validationSupport : validadores) {
                validationSupport.initInitialDecoration();
            }
        });
    }
}
