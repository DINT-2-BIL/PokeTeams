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
import com.fcm.pokeTeams.modelos.Miembro;
import com.fcm.pokeTeams.util.Conexion;
import com.fcm.pokeTeams.util.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class controllerEquipos implements Initializable {
    private controllerEquipo ce;
    private controllerCore cCore;
    Conexion conexion = null;
    Stage emergente;
    Utilidades util = new Utilidades();
    List<Miembro> participantes = new ArrayList<>();
    Equipo equipo;

    @FXML
    private ImageView imgPokemon1;

    @FXML
    private ImageView imgPokemon2;

    @FXML
    private ImageView imgPokemon3;

    @FXML
    private ImageView imgPokemon4;

    @FXML
    private ImageView imgPokemon5;

    @FXML
    private ImageView imgPokemon6;

    @FXML
    private Label txtFormatoEquipo;

    @FXML
    private Label txtNombreEquipo;

    @FXML
    void abrirEquipo(MouseEvent event) {
        if (!cCore.comprobarEquipoAbierto()) {
            if (event.getButton() == MouseButton.PRIMARY) {
                this.ce.enviaMiembros(equipo, conexion);
                this.emergente.setTitle(txtNombreEquipo.getText());
                this.emergente.getIcons().add(new Image("Maushold.png"));
                this.emergente.setOnCloseRequest(evento -> {
                    cCore.asignarEquipoAbierto(false);
                });
                cCore.asignarEquipoAbierto(true);
                // this.emergente.initModality(Modality.APPLICATION_MODAL);
                this.emergente.show();
            }
        }
        
        
    }
    
    @FXML
    void editar(ActionEvent event) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/popUp_editar_equipo.fxml"));
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(controllerTarjetaPokemon.class.getName()).log(Level.SEVERE, null, ex);
        }

        Stage miStage = new Stage();
        Scene inicio = new Scene(root);
        miStage.setScene(inicio);
        miStage.setTitle("Editar " + equipo.getNombre());
        miStage.getIcons().add(new Image("Smeargle.png"));
        miStage.showAndWait();
        List<String> datos = (List<String>) miStage.getUserData();
        if (!datos.isEmpty()) {
            
            try {
                String nombre;
                String formato;
                if (!datos.get(0).isEmpty()) {
                    nombre = datos.get(0);
                } else {
                    nombre = equipo.getNombre();
                }
                if (!datos.get(1).isEmpty()) {
                    formato = datos.get(1);
                } else {
                    formato = equipo.getFormato();
                }

                String query = "UPDATE equipo SET Nombre_Equipo = ?, Formato = ? WHERE ID_Equipo = ?";

                Connection c = conexion.getConexion();
                PreparedStatement sentencia = c.prepareStatement(query);

                sentencia.setString(1, nombre);
                sentencia.setString(2, formato);
                sentencia.setInt(3, equipo.getIdEquipo());

                int filasActualizadas;                
                filasActualizadas = sentencia.executeUpdate();
                
                if (filasActualizadas > 0) {
                    System.out.println("Actualización exitosa. Filas afectadas: " + filasActualizadas);
                } else {
                    System.out.println("No se encontró el Pokémon para actualizar.");
                }
                cCore.cargarGridEquipo();
            } catch (SQLException ex) {
                Logger.getLogger(controllerEquipos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void eliminar(ActionEvent event) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/popUp_eliminar.fxml"));
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(controllerTarjetaPokemon.class.getName()).log(Level.SEVERE, null, ex);
        }

        Stage miStage = new Stage();
        Scene inicio = new Scene(root);
        miStage.setScene(inicio);
        miStage.setTitle("Eliminar " + equipo.getNombre());
        miStage.getIcons().add(new Image("Trubbish.png"));
        miStage.setOnCloseRequest(evento -> {
            miStage.setUserData(false);
        });
        miStage.showAndWait();
        if ((boolean) miStage.getUserData()) {
            try {
                String query = "DELETE FROM equipo WHERE ID_Equipo = ?";
                Connection c = conexion.getConexion();
                PreparedStatement preparado = c.prepareStatement(query);
                preparado.setInt(1, equipo.getIdEquipo());
                if (preparado.executeUpdate() > 0) {
                    System.out.println("Borrado");
                } else {
                    System.out.println("No borrado");
                }
                cCore.cargarGridEquipo();
            } catch (SQLException e) {
                System.out.println("Error al conectar con la BD: " + e.getMessage());
            }
            
            System.out.println(equipo.getNombre() + " eliminado.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Parent rootEquipo = null;
        FXMLLoader loaderEquipo = new FXMLLoader(getClass().getResource("/fxml/emergente_añadir_equipo_v1.fxml"));
        try {
            rootEquipo = loaderEquipo.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        ce = loaderEquipo.getController();
        ce.setControladorEnlace(this);

        Scene sceneB = new Scene(rootEquipo);
        emergente = new Stage();
        emergente.setResizable(false);
        emergente.setScene(sceneB);
        emergente.setTitle("Ventana Emergente");
    }
    
    public void cargarMiembros() {
        try {
            participantes.clear();
            String query = "SELECT ID_Equipo, N_Pokedex, Sprite "
                    + "FROM equipo JOIN pokemon USING (N_Pokedex) WHERE ID_Equipo = '" + equipo.getIdEquipo() + "'";
            Statement statement = conexion.getConexion().createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Miembro tempMiembro = new Miembro();
                tempMiembro.setSprite(result.getString("Sprite"));
                participantes.add(tempMiembro);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(controllerEquipos.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<ImageView> listImagenes = new ArrayList<>();
        listImagenes.add(imgPokemon1);
        listImagenes.add(imgPokemon2);
        listImagenes.add(imgPokemon3);
        listImagenes.add(imgPokemon4);
        listImagenes.add(imgPokemon5);
        listImagenes.add(imgPokemon6);
        
        
        for (int i = 0; i < listImagenes.size(); i++) {
            if (i < participantes.size()) {
                listImagenes.get(i).setVisible(true);
                util.recuperarImagenBBDD(participantes.get(i).getSprite(), listImagenes.get(i));
            } else {
                listImagenes.get(i).setVisible(false);
            }
        }
    }

    public void asignarEquipo(Equipo e, Conexion c) {
        txtNombreEquipo.setText(e.getNombre());
        txtFormatoEquipo.setText(e.getFormato());
        equipo = e;
        conexion = c;
        cargarMiembros();
    }
    
    public void asignarControladorCore(controllerCore c) {
        this.cCore = c;
    }
}
