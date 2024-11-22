/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.util;

import javafx.scene.control.Alert;

/**
 *
 * @author DFran49
 */
public class Alertas {
    Alert alerta;

    public Alertas(Alert.AlertType tipo, String titulo, String header, String contenido) {
        alerta=new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(header);
        alerta.setContentText(contenido);
    }
    
    public void mostrarAlerta() {
        alerta.showAndWait();
    }
}
