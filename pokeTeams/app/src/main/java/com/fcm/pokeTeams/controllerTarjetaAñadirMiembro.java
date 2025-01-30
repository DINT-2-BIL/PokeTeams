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
import com.fcm.pokeTeams.util.Conexion;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class controllerTarjetaAñadirMiembro implements Initializable {
    private controllerEquipo ce;
    private Equipo equipo;

    @FXML
    private ImageView imgPokemonMiembro;

    @FXML
    private SplitPane tarjeta;

    @FXML
    private Label txtNombreMiembro;

    @FXML
    void añadirMiembro(MouseEvent event) {
        Stage ventana = new Stage();
        CargadorFXML.getInstance().cargar(VistasControladores.ADDEDITMIEMBRO, ventana);
        ventana.setTitle("Añadir miembro");
        ventana.showAndWait();
        /*try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/emergente_añadir_pokemon_equipo_v1.fxml"));
            root = loader.load();
            miStage.setTitle("Añadir miembro");
            cam.asignarCerrado(conexion, equipo, 3);
            miStage.getIcons().add(new Image("/img/Plusle.png"));
            miStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(controllerCore.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/emergente_añadir_pokemon_equipo_v1.fxml"));
        try {
            root = loader.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        cam = loader.getController();
        cam.setControladorEnlaceAñadir(this);
        miStage = new Stage();
        Scene inicio = new Scene(root);
        miStage.setScene(inicio);*/
        Platform.runLater(() -> {
            equipo = (Equipo) tarjeta.getUserData();
        });
    }
    
    public void refrescar() {
        ce.refrescar();
    }
}
