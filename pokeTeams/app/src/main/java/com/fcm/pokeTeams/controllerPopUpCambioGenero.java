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
import com.fcm.pokeTeams.enums.Generos;
import com.fcm.pokeTeams.util.Alertas;
import com.fcm.pokeTeams.util.CargadorFXML;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class controllerPopUpCambioGenero implements Initializable {

    private controllerCore cCore = CargadorFXML.getInstance().getControllerCore();

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
            Entrenador entrenador = (Entrenador) this.rbFemenino.getScene().getWindow().getUserData();
            entrenador.setGenero((char) genero.getSelectedToggle().getUserData());
            EntrenadorDAO.getInstance().update(entrenador);
            cCore.entrenador = entrenador;
            cCore.refrescarUser();
            cerrar();
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        cerrar();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rbFemenino.setUserData(Generos.F.getSigla());
        rbMasculino.setUserData(Generos.M.getSigla());
        rbOtro.setUserData(Generos.O.getSigla());
    }

    private void cerrar() {
        Stage ventana = (Stage) rbFemenino.getScene().getWindow();
        ventana.close();
    }
}
