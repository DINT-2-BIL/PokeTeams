/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author DFran49
 */
import com.fcm.pokeTeams.DAO.EquipoDAO;
import com.fcm.pokeTeams.modelos.Equipo;
import com.fcm.pokeTeams.util.Alertas;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class controllerAñadirEquipo implements Initializable {

    private List<ValidationSupport> validadores;
    private int id;

    @FXML
    private TextField txtFormato;

    @FXML
    private TextField txtNombre;

    @FXML
    void cancelar(ActionEvent event) {
        Stage ventana = (Stage) txtFormato.getScene().getWindow();
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
            Equipo equipo = new Equipo();
            equipo.setIdEntrenador(CargadorFXML.getInstance().getControllerCore().entrenador.getIdEntrenador());
            equipo.setNombre(txtNombre.getText());
            equipo.setFormato(txtFormato.getText());
            EquipoDAO.getInstance().insert(equipo);
            CargadorFXML.getInstance().getControllerCore().cargarGridEquipo();
            ventana.close();
        } else {
            new Alertas(Alert.AlertType.WARNING, "Algo falló", "Incoherencia con las restricciones",
                    "Debe rellenar todos los campos y asegurarse de que siguen el formato que puede ver en el iconito de X pequeño").mostrarAlerta();
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
