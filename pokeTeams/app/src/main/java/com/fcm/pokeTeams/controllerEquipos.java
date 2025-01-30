/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author DFran49
 */
import com.fcm.pokeTeams.DAO.EquipoDAO;
import com.fcm.pokeTeams.DAO.MiembroDAO;
import com.fcm.pokeTeams.enums.VistasControladores;
import com.fcm.pokeTeams.modelos.Equipo;
import com.fcm.pokeTeams.modelos.Miembro;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class controllerEquipos implements Initializable {
    private controllerCore cCore;
    Utilidades util = Utilidades.getInstance();
    private ObservableList<Miembro> listaMiembros = FXCollections.observableArrayList();
    private Equipo equipo;

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
    private ContextMenu menu;

    @FXML
    private SplitPane tarjeta;

    @FXML
    private Label txtFormatoEquipo;

    @FXML
    private Label txtNombreEquipo;

    @FXML
    void abrirEquipo(MouseEvent event) {
        if (cCore.equipoAbierto.isEmpty()) {
            if (event.getButton() == MouseButton.PRIMARY) {
                Stage ventana = new Stage();
                
                CargadorFXML.getInstance().cargar(VistasControladores.EQUIPO, ventana);
                ventana.setTitle(txtNombreEquipo.getText());
                ArrayList<Object> listaTemp = new ArrayList<>() {{add(listaMiembros); add(equipo);}};
                ventana.setUserData(listaTemp);
                ventana.setOnCloseRequest(evento -> {
                    cCore.asignarEquipoAbierto("");
                });
                cCore.asignarEquipoAbierto(equipo.getNombre());
                ventana.show();
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
        miStage.getIcons().add(new Image("/img/Smeargle.png"));
        miStage.showAndWait();
        List<String> datos = (List<String>) miStage.getUserData();
        if (!datos.isEmpty()) {
            if (!datos.get(0).isEmpty()) {
                equipo.setNombre(datos.get(0));
            }
            if (!datos.get(1).isEmpty()) {
                equipo.setFormato(datos.get(1));
            }
            EquipoDAO.getInstance().update(equipo);
            cCore.cargarGridEquipo();
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
        miStage.getIcons().add(new Image("/img/Trubbish.png"));
        miStage.setOnCloseRequest(evento -> {
            miStage.setUserData(false);
        });
        miStage.showAndWait();
        if ((boolean) miStage.getUserData()) {
            EquipoDAO.getInstance().delete(equipo.getIdEquipo());
            cCore.cargarGridEquipo();
            
            System.out.println(equipo.getNombre() + " eliminado.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cCore = CargadorFXML.getInstance().getControllerCore();
        
        /*Parent rootEquipo = null;
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
        emergente.setTitle("Ventana Emergente");*/
        Platform.runLater(() -> {
            equipo = (Equipo) tarjeta.getUserData();
            txtNombreEquipo.setText(equipo.getNombre());
            txtFormatoEquipo.setText(equipo.getFormato());
            cargarMiembros();
        });
    }
    
    public void cargarMiembros() {
        listaMiembros = MiembroDAO.getInstance().getMiembros(equipo);
        
        List<ImageView> listImagenes = new ArrayList<>();
        listImagenes.add(imgPokemon1);
        listImagenes.add(imgPokemon2);
        listImagenes.add(imgPokemon3);
        listImagenes.add(imgPokemon4);
        listImagenes.add(imgPokemon5);
        listImagenes.add(imgPokemon6);
        
        
        for (int i = 0; i < listImagenes.size(); i++) {
            if (i < listaMiembros.size()) {
                listImagenes.get(i).setVisible(true);
                util.recuperarImagenBBDD(listaMiembros.get(i).getSprite(), listImagenes.get(i));
            } else {
                listImagenes.get(i).setVisible(false);
            }
        }
    }
}
