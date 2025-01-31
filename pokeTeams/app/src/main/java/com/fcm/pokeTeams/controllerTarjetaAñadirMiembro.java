/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author DFran49
 */
import com.fcm.pokeTeams.enums.VistasControladores;
import com.fcm.pokeTeams.modelos.Equipo;
import com.fcm.pokeTeams.modelos.Miembro;
import com.fcm.pokeTeams.util.CargadorFXML;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class controllerTarjetaAñadirMiembro implements Initializable {

    private Equipo equipo;
    private boolean abierto = false;

    @FXML
    private ImageView imgPokemonMiembro;

    @FXML
    private SplitPane tarjeta;

    @FXML
    private Label txtNombreMiembro;

    @FXML
    void añadirMiembro(MouseEvent event) {
        if (!abierto) {
            Stage ventana = new Stage();
            CargadorFXML.getInstance().cargar(VistasControladores.ADDEDITMIEMBRO, ventana);
            ventana.setTitle("Añadir miembro");
            Miembro m = new Miembro();
            m.setEquipo(equipo);
            ventana.setUserData(m);
            ventana.show();
            abierto = true;
        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            equipo = (Equipo) tarjeta.getUserData();
        });
    }
}
