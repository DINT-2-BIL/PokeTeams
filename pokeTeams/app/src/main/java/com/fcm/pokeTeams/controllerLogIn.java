/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

import com.fcm.pokeTeams.DAO.EntrenadorDAO;
import com.fcm.pokeTeams.enums.VistasControladores;
import com.fcm.pokeTeams.modelos.Entrenador;
import com.fcm.pokeTeams.util.CargadorFXML;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 *
 * @author DFran49
 */
public class controllerLogIn implements Initializable {
    List<ValidationSupport> validadores;
    
    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnRegistro;

    @FXML
    private PasswordField pwContraseña;

    @FXML
    private TextField txtNombre;

    @FXML
    void iniciarSesion() {
        boolean todoOK = true;
        for (ValidationSupport validationSupport : validadores) {
            todoOK = (todoOK && validationSupport.getValidationResult().getErrors().isEmpty());
        }

        //if (todoOK) {
            Entrenador entrenador = EntrenadorDAO.getInstance().selectEntrenador(txtNombre.getText(), pwContraseña.getText());
            if (entrenador.getNombre() != null) {
                CargadorFXML.getInstance().cargar(VistasControladores.INICIO, (Stage) this.txtNombre.getScene().getWindow());
                CargadorFXML.getInstance().getControllerCore().entrenador = entrenador;
            }
        /*} else {
            new Alertas(Alert.AlertType.WARNING, "Algo falló", "Incoherencia con las restricciones", 
                        "Debe rellenar todos los campos y asegurarse de que siguen el formato que puede ver en el iconito de X pequeño").mostrarAlerta();
        }*/
    }

    @FXML
    void registro() {
        CargadorFXML.getInstance().cargar(VistasControladores.SIGNIN, (Stage) this.txtNombre.getScene().getWindow());
        
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
                    return numero >= 3 && numero <= 20 && txtNombre.getText().matches("^[A-Za-z0-9. ]{3,}$");
                } catch (NumberFormatException e) {
                    return false;
                }
            },
            "El nombre puede tener mínimo 3 caracteres y 20 de máximo y solo puede contener letras, números o puntos"
        ));
        
        ValidationSupport vSContraseña = new ValidationSupport();
        vSContraseña.registerValidator(pwContraseña, Validator.createPredicateValidator(
            texto -> {
                if (texto == null || texto.toString().isEmpty()) {
                return false;
                }
                try {
                    int numero = texto.toString().length();
                    return numero >= 3 && numero <= 20;
                } catch (NumberFormatException e) {
                    return false;
                }
            },
            "La contraseña puede tener mínimo 3 caracteres, 20 de máximo"
        ));
        
        validadores = new ArrayList<>();
        validadores.addAll(Arrays.asList(vSNombre, vSContraseña));

        Platform.runLater(() -> {
            for (ValidationSupport validationSupport : validadores) {
                validationSupport.initInitialDecoration();
            }
        });
    }
}
