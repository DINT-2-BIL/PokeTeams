/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

import com.fcm.pokeTeams.DAO.EquipoDAO;
import com.fcm.pokeTeams.DAO.MiembroDAO;
import com.fcm.pokeTeams.modelos.Equipo;
import com.fcm.pokeTeams.modelos.Miembro;
import com.fcm.pokeTeams.util.CargadorFXML;
import com.fcm.pokeTeams.util.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author DFran49
 */
public class controllerEquipo implements Initializable {

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
            equipo = (Equipo) this.txtNombreEquipo.getScene().getWindow().getUserData();
            utils.crearTooltip("Equipo: " + equipo.getNombre(), txtNombreEquipo);

            cargarMiembros();
        });
    }

    void cargarMiembros() {
        equipo = EquipoDAO.getInstance().getPorID(equipo);
        txtNombreEquipo.setText(equipo.getNombre());
        this.gridMiembros.getChildren().clear();
        listaMiembros = MiembroDAO.getInstance().getMiembros(equipo);
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
}
