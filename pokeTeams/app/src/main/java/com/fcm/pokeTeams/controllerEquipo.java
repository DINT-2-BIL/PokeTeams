/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

import com.fcm.pokeTeams.modelos.Miembro;
import com.fcm.pokeTeams.util.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author DFran49
 */
public class controllerEquipo implements Initializable {
    private controllerEquipos ces;
    private controllerTarjetaMiembro ctm;
    private List<Miembro> listaMiembros;
    private Utilidades utils = new Utilidades();

    @FXML
    private GridPane gridMiembros;

    @FXML
    private TextField txtNombreEquipo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    void enviaMiembros(List<Miembro> lm, String nombre) {
        listaMiembros = lm;
        txtNombreEquipo.setText(nombre);
        utils.crearTooltip("Equipo: " + nombre, txtNombreEquipo);
        
        try {
            int col = 0;
            int row = 0;
            for (int i = 0; i < lm.size(); i++) {
                FXMLLoader cargarTarjeta = new FXMLLoader(getClass().getResource("fxml/tarjeta_miembro_equipo_v1.fxml"));
                SplitPane tarjeta = cargarTarjeta.load();
                controllerTarjetaMiembro controladorTarjeta = cargarTarjeta.getController();
                
                controladorTarjeta.asignarMiembro(listaMiembros.get(i));
                ctm = cargarTarjeta.getController();
                ctm.setControladorEnlace(this);
                utils.crearTooltip(listaMiembros.get(i).getMote(), tarjeta);
                gridMiembros.add(tarjeta, col, row);
                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            }
            if (lm.size() < 6) {
                FXMLLoader cargarTarjeta = new FXMLLoader(getClass().getResource("fxml/tarjeta_añadir_miembro_v1.fxml"));
                SplitPane tarjeta = cargarTarjeta.load();
                utils.crearTooltip("Añadir miembro", tarjeta);
                gridMiembros.add(tarjeta, col, row);
            }
        } catch (IOException ex) {
            Logger.getLogger(controllerEquipo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void setControladorEnlace(controllerEquipos c) {
        ces = c;
    }
}
