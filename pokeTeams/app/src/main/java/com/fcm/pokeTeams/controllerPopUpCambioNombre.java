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
import java.sql.Statement;
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

public class controllerPopUpCambioNombre implements Initializable{
    private Entrenador entrenador;
    private Conexion conexion;
    private controllerCore cc;
    List<ValidationSupport> validadores;

    @FXML
    private TextField txtNuevoNombre;

    @FXML
    void cambiarNombre(ActionEvent event) {
        boolean todoOK = true;
        for (ValidationSupport validationSupport : validadores) {
            todoOK = (todoOK && validationSupport.getValidationResult().getErrors().isEmpty());
        }

        if (todoOK) {
            PreparedStatement preparado = null;
            try {
                String query = "UPDATE entrenador SET Nombre = ? WHERE ID_Entrenador = ?;";
                Connection c = conexion.getConexion();
                preparado = c.prepareStatement(query);

                preparado.setString(1, txtNuevoNombre.getText());
                preparado.setInt(2, entrenador.getIdEntrenador());

                if (preparado.executeUpdate() > 0) {
                    System.out.println("Inserción exitosa.");
                } else {
                    System.out.println("No se insertó el Equipo.");
                }
                cc.refrescarUser();
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
        Stage ventana = (Stage) txtNuevoNombre.getScene().getWindow();
        ventana.close();
    }
    
    public void pasoVariables(Conexion c, Entrenador e, controllerCore cCore) {
        conexion = c;
        entrenador = e;
        cc = cCore;
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
                    return numero >= 1 && numero <= 20 && txtNuevoNombre.getText().matches("^[A-Za-z0-9. ]+$");
                } catch (NumberFormatException e) {
                    return false;
                }
            },
            "Debe tener el nombre introducido entre 1 y 20 y solo puede contener letras, números o puntos"
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
