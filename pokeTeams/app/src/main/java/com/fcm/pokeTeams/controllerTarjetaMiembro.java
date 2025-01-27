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
import java.sql.SQLException;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class controllerTarjetaMiembro implements Initializable{
    controllerEquipo ce;
    controllerMiembro cm;
    private controllerConfirmar cc;
    controllerAñadirMiembro cam;
    private Conexion conexion = null;
    Stage emergenteEditar;
    Stage emergenteVer;
    Miembro miembro;
    Utilidades util = new Utilidades();
    Equipo equipo;

    @FXML
    private ImageView imgPokemonMiembro;

    @FXML
    private Label txtNombreMiembro;
    
    @FXML
    void editar(ActionEvent event) {
        cam.asignarCerrado(conexion, equipo, 4);
        this.cam.enviaMiembro(miembro);
        this.emergenteEditar.setTitle(miembro.getMote());
        
        emergenteEditar.getIcons().add(util.getImage(miembro.getSprite()));
        emergenteEditar.show();
    }

    @FXML
    void verMiembro(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            this.cm.enviaMiembro(miembro);
            this.emergenteVer.setTitle(miembro.getMote());
            emergenteVer.getIcons().add(util.getImage(miembro.getSprite()));
            emergenteVer.show();
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
        miStage.setTitle("Eliminar " + miembro.getMote());
        miStage.getIcons().add(new Image("Trubbish.png"));
        miStage.setOnCloseRequest(evento -> {
            miStage.setUserData(false);
        });
        miStage.showAndWait();
        if ((boolean) miStage.getUserData()) {
            try {
                String query = "DELETE FROM equipo WHERE Mote = ?";
                Connection c = conexion.getConexion();
                PreparedStatement preparado = c.prepareStatement(query);
                preparado.setString(1, miembro.getMote());
                if (preparado.executeUpdate() > 0) {
                    System.out.println("Borrado");
                } else {
                    System.out.println("No borrado");
                }
                ce.cargarMiembros();
            } catch (SQLException e) {
                System.out.println("Error al conectar con la BD: " + e.getMessage());
            }
            
            System.out.println(miembro.getEspecie() + " eliminado.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/emergente_añadir_pokemon_equipo_v1.fxml"));
        try {
            root = loader.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        cam = loader.getController();
        cam.setControladorEnlace(this);

        Scene sceneB = new Scene(root);
        emergenteEditar = new Stage();
        emergenteEditar.setResizable(false);
        emergenteEditar.setScene(sceneB);
        emergenteEditar.setTitle("Añadir/Editar miembro");
        
        FXMLLoader cargador = new FXMLLoader(getClass().getResource("/fxml/emergente_miembro.fxml"));
        Parent origen = null;
        try {
            origen = cargador.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        
        cm = cargador.getController();
        Scene sceneC = new Scene(origen);
        emergenteVer = new Stage();
        emergenteVer.setResizable(false);
        emergenteVer.setScene(sceneC);
        emergenteVer.setTitle("Ver miembro");
    }
    
    public void asignarMiembro(Miembro m, Conexion c, Equipo e) {
        txtNombreMiembro.setText(m.getMote());
        util.recuperarImagenBBDD(m.getSprite(), imgPokemonMiembro);
        miembro = m;
        conexion = c;
        equipo = e;
    }
    
    public void refrescar() {
        ce.refrescar();
    }
    
    void setControladorEnlace(controllerEquipo c) {
        ce = c;
    }
}
