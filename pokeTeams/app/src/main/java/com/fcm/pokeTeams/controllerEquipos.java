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
import javafx.stage.Stage;

public class controllerEquipos implements Initializable {
    private controllerEquipo ce;
    Stage emergente;
    Conexion conexion = null;
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
        if (event.getButton() == MouseButton.PRIMARY) {
            this.ce.enviaMiembros(participantes, txtNombreEquipo.getText(), conexion);
            this.emergente.setTitle(txtNombreEquipo.getText());
            this.emergente.getIcons().add(new Image("Maushold.png"));
            this.emergente.show();
        }
        
    }
    
    @FXML
    void editar(ActionEvent event) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/popUp_editar_equipo.fxml"));
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
        miStage.setTitle("Eliminar " + equipo.getNombre());
        miStage.getIcons().add(new Image("Trubbish.png"));
        miStage.setOnCloseRequest(evento -> {
            miStage.setUserData(false);
        });
        miStage.showAndWait();
        if ((boolean) miStage.getUserData()) {
            System.out.println(equipo.getNombre() + " eliminado.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Parent rootEquipo = null;
        FXMLLoader loaderEquipo = new FXMLLoader(getClass().getResource("fxml/emergente_añadir_equipo_v1.fxml"));
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

    public void asignarEquipo(Equipo e, Conexion c) {
        txtNombreEquipo.setText(e.getNombre());
        txtFormatoEquipo.setText(e.getFormato());
        
        try {
            String query = "SELECT Especie, N_Pokedex, Mote, Genero, Nivel, Habilidad, Naturaleza, Objeto, "
                    + "Tipo_1, Tipo_2, Habilidades, Movimientos, Estadisticas, EVs, IVs, Sprite "
                    + "FROM equipo JOIN pokemon USING (N_Pokedex) WHERE ID_Equipo = " + e.getIdEquipo();
            Statement statement = conexion.getConexion().createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Miembro tempMiembro = new Miembro();
                tempMiembro.setnPokedex(result.getInt("N_Pokedex"));
                tempMiembro.setEspecie(result.getString("Especie"));
                tempMiembro.setMote(result.getString("Mote"));
                tempMiembro.setGenero(result.getString("Genero").charAt(0));
                tempMiembro.setNivel(result.getInt("Nivel"));
                tempMiembro.setHabilidad(result.getString("Habilidad"));
                tempMiembro.setNaturaleza(result.getString("Naturaleza"));
                tempMiembro.setObjeto(result.getString("Objeto"));
                tempMiembro.setTipo1(result.getString("Tipo_1"));
                tempMiembro.setTipo2(result.getString("Tipo_2"));
                tempMiembro.setHabilidades(result.getString("Habilidades"));
                tempMiembro.setMovimientos(result.getString("Movimientos"));
                tempMiembro.setStats(result.getString("Estadisticas"));
                tempMiembro.setEvs(result.getString("EVs"));
                tempMiembro.setIvs(result.getString("IVs"));
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
        
        for (int i = 0; i < participantes.size(); i++) {
            util.recuperarImagenBBDD(participantes.get(i).getSprite(), listImagenes.get(i));
        }
        equipo = e;
        conexion = c;
    }
}
