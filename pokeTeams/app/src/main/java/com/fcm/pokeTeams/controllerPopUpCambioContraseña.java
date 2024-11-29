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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controllerPopUpCambioContraseña {
    private Entrenador entrenador;
    private Conexion conexion;

    @FXML
    private TextField txtConfContraseña;

    @FXML
    private TextField txtContraseña;

    @FXML
    void cambiarContraseña(ActionEvent event) {
        if (txtContraseña.getText().isEmpty() || txtConfContraseña.getText().isEmpty()) {
            Alertas credencialesIncorrectas = new Alertas(Alert.AlertType.ERROR, "CAMPOS EN BLANCO", 
                        "Alguno de los campos está en blanco", "Intentelo de nuevo.");
                credencialesIncorrectas.mostrarAlerta();
        } else {
            if (!txtContraseña.getText().equals(txtConfContraseña.getText())) {
                Alertas credencialesIncorrectas = new Alertas(Alert.AlertType.ERROR, "CONTRASEÑAS DIFERENTES", 
                        "Las contraseñas no coinciden.", "Intentelo de nuevo.");
                credencialesIncorrectas.mostrarAlerta();
            } else {
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
}
