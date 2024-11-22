/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author DFran49
 */
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class controllerTarjetaAñadirMiembro implements Initializable {
    private controllerConfirmar cc;

    @FXML
    private ImageView imgPokemonMiembro;

    @FXML
    private Label txtNombreMiembro;

    @FXML
    void añadirMiembro(MouseEvent event) {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/emergente_añadir_pokemon_equipo_v1.fxml"));
            root = loader.load();

            Stage miStage = new Stage();
            Scene inicio = new Scene(root);
            miStage.setScene(inicio);
            miStage.setTitle("Añadir miembro");
            miStage.setOnCloseRequest(evento -> {
                evento.consume();
                Parent raiz = null;
                FXMLLoader cargador = new FXMLLoader(getClass().getResource("fxml/popUp_confirmar_cambios.fxml"));
                try {
                    raiz = cargador.load();
                } catch (IOException ex) {
                    Logger.getLogger(controllerTarjetaPokemon.class.getName()).log(Level.SEVERE, null, ex);
                }
                cc = cargador.getController();

                Stage confirmar = new Stage();
                Scene scene = new Scene(raiz);
                confirmar.setScene(scene);
                confirmar.getIcons().add(new Image("Victini.png"));
                confirmar.setTitle("Confirmar");
                cc.enviaStage(miStage);
                confirmar.showAndWait();
            });
            miStage.getIcons().add(new Image("Plusle.png"));
            miStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(controllerCore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
