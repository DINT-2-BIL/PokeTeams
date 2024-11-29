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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class controllerPopUpCambioGenero {
    private Entrenador entrenador;
    private Conexion conexion;
    private controllerCore cc;

    @FXML
    private ToggleGroup genero;

    @FXML
    private RadioButton rbFemenino;

    @FXML
    private RadioButton rbMasculino;

    @FXML
    private RadioButton rbOtro;

    @FXML
    void cambiarGenero(ActionEvent event) {
        if (genero.getSelectedToggle() == null) {
            Alertas credencialesIncorrectas = new Alertas(Alert.AlertType.ERROR, "GÉNERO NO SELECCIONADO", 
                        "Debe seleccionar un género.", "Intentelo de nuevo.");
                credencialesIncorrectas.mostrarAlerta();
        } else {
            PreparedStatement preparado = null;
            try {
                String query = "UPDATE entrenador SET Genero = ? WHERE ID_Entrenador = ?;";
                Connection c = conexion.getConexion();
                preparado = c.prepareStatement(query);

                if (genero.getSelectedToggle() == rbFemenino) {
                    preparado.setString(1, "F");
                } else if (genero.getSelectedToggle() == rbMasculino) {
                    preparado.setString(1, "M");
                } else {
                    preparado.setString(1, "O");
                }
                
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
        Stage ventana = (Stage) rbFemenino.getScene().getWindow();
        ventana.close();
    }
    
    public void pasoVariables(Conexion c, Entrenador e, controllerCore cCore) {
        conexion = c;
        entrenador = e;
        cc = cCore;
    }
}
