/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author DFran49
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class controllerConfirmar implements Initializable{
    Stage entrada;
    
    @FXML
    private Button btnCancelar;

    @FXML
    void cancelar(ActionEvent event) {
        Stage a = (Stage) btnCancelar.getScene().getWindow();
        a.close();
    }

    @FXML
    void cerrar(ActionEvent event) {
        Stage a = (Stage) btnCancelar.getScene().getWindow();
        a.setUserData(false);
        a.close();
        entrada.close();
    }

    @FXML
    void guardar(ActionEvent event) {
        Stage a = (Stage) btnCancelar.getScene().getWindow();
        a.setUserData(true);
        a.close();
        entrada.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void enviaStage(Stage s) {
        entrada = s;
        Stage ventana = (Stage) this.btnCancelar.getScene().getWindow();
        ventana.setOnCloseRequest(event -> event.consume());
    }
}
