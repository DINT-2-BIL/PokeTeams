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
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controllerPopUpCambioNombre {
    private Entrenador entrenador;
    private Conexion conexion;
    private controllerCore cc;

    @FXML
    private TextField txtNuevoNombre;

    @FXML
    void cambiarNombre(ActionEvent event) {
        if (txtNuevoNombre.getText().isEmpty()) {
            Alertas credencialesIncorrectas = new Alertas(Alert.AlertType.ERROR, "NOMBRE EN BLANCO", 
                        "No ha introducido un nombre.", "Intentelo de nuevo.");
                credencialesIncorrectas.mostrarAlerta();
        } else {
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
}
