/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

import com.fcm.pokeTeams.modelos.Equipo;
import com.fcm.pokeTeams.modelos.Miembro;
import com.fcm.pokeTeams.util.CargadorFXML;
import com.fcm.pokeTeams.util.Conexion;
import com.fcm.pokeTeams.util.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ObservableList<Miembro> listaMiembros = FXCollections.observableArrayList();
    private Utilidades utils = Utilidades.getInstance();
    private Equipo equipo;

    @FXML
    private GridPane gridMiembros;

    @FXML
    private TextField txtNombreEquipo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            ArrayList<Object> listTemp = (ArrayList<Object>) this.txtNombreEquipo.getScene().getWindow().getUserData();
            listaMiembros = (ObservableList<Miembro>) listTemp.get(0);
            equipo = (Equipo) listTemp.get(1);
            utils.crearTooltip("Equipo: " + equipo.getNombre(), txtNombreEquipo);
            txtNombreEquipo.setText(equipo.getNombre());
            cargarMiembros();
        });
    }
    
    void cargarMiembros() {

            
            this.gridMiembros.getChildren().clear();
            int col = 0;
            int row = 0;
            for (int i = 0; i < listaMiembros.size(); i++) {
                CargadorFXML.getInstance().cargar(gridMiembros, col, row, listaMiembros.get(i));
                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            }
            if (listaMiembros.size() < 6) {
                CargadorFXML.getInstance().cargarAñadirMiembro(gridMiembros, col, row, equipo);
            }
    }
    
    
    
    
    public void refrescar() {
        ces.cargarMiembros();
        cargarMiembros();
    }
}
