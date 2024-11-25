/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author DFran49
 */
import com.fcm.pokeTeams.modelos.Equipo;
import com.fcm.pokeTeams.modelos.Pokemon;
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
    private controllerPokedex cp;
    private controllerAñadirPokemon cap;
    private controllerConfirmar cc;
    private controllerCore cCore;
    Conexion conexion = null;
    private boolean admin;
    Stage emergente;
    Stage editar;
    Pokemon pokemon;
    Utilidades util = new Utilidades();
    
    @FXML
    private ImageView imgPokemon;

    @FXML
    private ContextMenu menu;

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
            this.cp.enviaPokemon(pokemon);
            emergente.setTitle(pokemon.getEspecie());
            emergente.getIcons().add(util.getImage(pokemon.getSprite()));
            this.emergente.show();
        }
    }
    
    @FXML
    void editar(ActionEvent event) {
        this.cap.enviaPokemon(pokemon);
        editar.setTitle("Editar " + pokemon.getEspecie());
        editar.getIcons().add(util.getImage(pokemon.getSprite()));
        editar.setOnCloseRequest(evento -> {
            evento.consume();
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/popUp_confirmar_cambios.fxml"));
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(controllerTarjetaPokemon.class.getName()).log(Level.SEVERE, null, ex);
            }
            cc = loader.getController();

            Stage miStage = new Stage();
            Scene inicio = new Scene(root);
            miStage.setScene(inicio);
            miStage.setTitle("Confirmar");
            cc.enviaStage(editar);
            miStage.getIcons().add(new Image("Victini.png"));
            miStage.showAndWait();
        });
        this.editar.show();
    }

    @FXML
    void eliminar(ActionEvent event) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/popUp_eliminar.fxml"));
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(controllerTarjetaPokemon.class.getName()).log(Level.SEVERE, null, ex);
        }

        Stage miStage = new Stage();
        Scene inicio = new Scene(root);
        miStage.setScene(inicio);
        miStage.setTitle("Eliminar " + pokemon.getEspecie());
        miStage.getIcons().add(new Image("Trubbish.png"));
        miStage.setOnCloseRequest(evento -> {
            miStage.setUserData(false);
        });
        miStage.showAndWait();
        if ((boolean) miStage.getUserData()) {
            try {
                
                String query = "DELETE FROM pokemon WHERE N_Pokedex = ?";
                Connection c = conexion.getConexion();
                PreparedStatement preparado = c.prepareStatement(query);
                preparado.setInt(1, Integer.parseInt(pokemon.getnPokedex()));
                if (preparado.executeUpdate() > 0) {
                    System.out.println("Borrado");
                } else {
                    System.out.println("No borrado");
                }
                cCore.cargarGridPokemon(admin);
                cCore.cargarGridEquipo();
            } catch (SQLException e) {
                System.out.println("Error al conectar con la BD: " + e.getMessage());
            }
            System.out.println(pokemon.getEspecie() + " eliminado.");
        } else {
            System.out.println("Raro");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/emergente_pokemon_v1.fxml"));
        try {
            root = loader.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        cp = loader.getController();
        cp.setControladorEnlace(this);

        Scene sceneB = new Scene(root);
        emergente = new Stage();
        emergente.setResizable(false);
        emergente.setScene(sceneB);
        
        loader = new FXMLLoader(getClass().getResource("fxml/emergente_añadir_pokemon_v1.fxml"));
        try {
            root = loader.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        cap = loader.getController();

        sceneB = new Scene(root);
        editar = new Stage();
        editar.setResizable(false);
        editar.setScene(sceneB);
    }
    
    public void asignarControladorCore(controllerCore c) {
        this.cCore = c;
    }

    public void asignarPokemon(Pokemon p, boolean admin, Conexion c) {
        this.admin = admin;
        txtEspecie.setText(p.getEspecie());
        
        txtId.setText(p.getnPokedex());
        util.recuperarImagenBBDD(p.getSprite(), imgPokemon);
        pokemon = p;
        if (!admin) {
            menu.getItems().clear();
        }
    }
}
