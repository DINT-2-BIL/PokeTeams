/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author DFran49
 */

import com.fcm.pokeTeams.modelos.Entrenador;
import com.fcm.pokeTeams.util.Alertas;
import com.fcm.pokeTeams.util.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

public class controllerPopUpCambioContraseña implements Initializable{
    private Entrenador entrenador;
    private Conexion conexion;
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
            PreparedStatement preparado = null;
            try {
                String query = "UPDATE entrenador SET Contraseña = ? WHERE ID_Entrenador = ?;";
                Connection c = conexion.getConexion();
                preparado = c.prepareStatement(query);

                preparado.setString(1, txtContraseña.getText());
                preparado.setInt(2, entrenador.getIdEntrenador());

                if (preparado.executeUpdate() > 0) {
                    System.out.println("Inserción exitosa.");
                } else {
                    System.out.println("No se insertó el Equipo.");
                }
            } catch (SQLException e) {
                System.out.println("Error al editar: " + e.getMessage());
            } finally {
                try {
                    if (preparado != null) preparado.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
    
    public void pasoVariables(Conexion c, Entrenador e) {
        conexion = c;
        entrenador = e;
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
            for (ValidationSupport validationSupport : validadores) {
                validationSupport.initInitialDecoration();
            }
        });
    }
}
