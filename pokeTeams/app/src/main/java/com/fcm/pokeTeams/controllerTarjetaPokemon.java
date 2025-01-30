/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author DFran49
 */
import com.fcm.pokeTeams.DAO.PokemonDAO;
import com.fcm.pokeTeams.enums.VistasControladores;
import com.fcm.pokeTeams.modelos.Equipo;
import com.fcm.pokeTeams.modelos.Pokemon;
import com.fcm.pokeTeams.util.CargadorFXML;
import com.fcm.pokeTeams.util.Conexion;
import com.fcm.pokeTeams.util.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class controllerTarjetaPokemon implements Initializable {
    private controllerAñadirPokemon cap;
    private controllerConfirmar cc;
    private controllerCore cCore;
    Conexion conexion = null;
    private boolean admin;
    Stage emergente;
    Stage editar;
    Pokemon pokemon;
    Utilidades util = Utilidades.getInstance();
    
    @FXML
    private ImageView imgPokemon;

    @FXML
    private ContextMenu menu;

    @FXML
    private SplitPane tarjeta;

    @FXML
    private Label txtEspecie;

    @FXML
    private Label txtId;

    @FXML
    void abrirMenu(ContextMenuEvent event) {
        menu.show(imgPokemon.getParent().getParent(), event.getScreenX(), event.getScreenY());
    }

    @FXML
    void abrirPokemon(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            Stage ventana = new Stage();
            CargadorFXML.getInstance().cargar(VistasControladores.POKEMON, ventana);
            ventana.getIcons().add(Utilidades.getInstance().getImage(pokemon.getSprite()));
            ventana.setTitle("Ver datos de ".concat(pokemon.getEspecie()));
            ventana.setUserData(pokemon);
            ventana.show();
        }
    }
    
    @FXML
    void editar(ActionEvent event) {
        Stage ventana = new Stage();
        CargadorFXML.getInstance().cargar(VistasControladores.ADDEDITPOKEMON, ventana);
        ventana.getIcons().add(Utilidades.getInstance().getImage(pokemon.getSprite()));
        ventana.setTitle("Editar ".concat(pokemon.getEspecie()));
        ventana.setUserData(pokemon);
        ventana.show();
    }

    @FXML
    void eliminar(ActionEvent event) {
        Stage ventanaConfirmar = new Stage();
        CargadorFXML.getInstance().cargar(ventanaConfirmar, pokemon.getEspecie());
        ventanaConfirmar.showAndWait();
        if ((boolean) ventanaConfirmar.getUserData()) {
            PokemonDAO.getInstance().delete(pokemon.getnPokedex());
            System.out.println(pokemon.getEspecie() + " eliminado.");
            cCore.cargarGridPokemon();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cCore = CargadorFXML.getInstance().getControllerCore();
        
        Platform.runLater(() -> {
            pokemon = (Pokemon) tarjeta.getUserData();
            asignarPokemon();
        });
    }

    public void asignarPokemon() {
        txtEspecie.setText(pokemon.getEspecie());
        txtId.setText(Utilidades.definirIdPokemon(pokemon.getnPokedex()));
        util.recuperarImagenBBDD(pokemon.getSprite(), imgPokemon);
        if (!cCore.entrenador.isEsAdmin()) {
            menu.getItems().clear();
        }
    }
}
