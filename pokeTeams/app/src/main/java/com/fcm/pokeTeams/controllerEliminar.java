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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controllerEliminar implements Initializable {

    @FXML
    private TextField txtEliminar;
    
    @FXML
    void cancelar(ActionEvent event) {
        cerrar();
    }

    @FXML
    void eliminar(ActionEvent event) {
        Stage a = (Stage) txtEliminar.getScene().getWindow();
        if (!txtEliminar.getText().equals("ELIMINAR")) {
            Alertas credencialesIncorrectas = new Alertas(Alert.AlertType.ERROR, "NO CONFIRMADO", 
                        "Debe escribir \"ELIMINAR\" para poder eliminar el elemento.", "Intentelo de nuevo.");
                credencialesIncorrectas.mostrarAlerta();
                
                a.setUserData(false);
        } else {
            a.setUserData(true);
            cerrar();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private void cerrar() {
        Stage ventana = (Stage) txtEliminar.getScene().getWindow();
        ventana.close();
    }
}
