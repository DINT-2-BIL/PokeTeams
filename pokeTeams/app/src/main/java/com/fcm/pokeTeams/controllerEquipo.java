/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author DFran49
 */
public class controllerEquipo implements Initializable {
    private controllerEquipos ces;
    private Conexion conexion = null;
    private controllerTarjetaMiembro ctm;
    private controllerTarjetaAñadirMiembro ctam;
    private List<Miembro> listaMiembros = new ArrayList<>();
    private Utilidades utils = new Utilidades();
    private Equipo equipo;

    @FXML
    private GridPane gridMiembros;

    @FXML
    private TextField txtNombreEquipo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    void cargarMiembros() {
        try {
            crearLista();
            this.gridMiembros.getChildren().clear();
            int col = 0;
            int row = 0;
            for (int i = 0; i < listaMiembros.size(); i++) {
                FXMLLoader cargarTarjeta = new FXMLLoader(getClass().getResource("fxml/tarjeta_miembro_equipo_v1.fxml"));
                SplitPane tarjeta = cargarTarjeta.load();
                controllerTarjetaMiembro controladorTarjeta = cargarTarjeta.getController();
                
                controladorTarjeta.asignarMiembro(listaMiembros.get(i), conexion, equipo);
                ctm = cargarTarjeta.getController();
                ctm.setControladorEnlace(this);
                utils.crearTooltip(listaMiembros.get(i).getMote(), tarjeta);
                gridMiembros.add(tarjeta, col, row);
                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            }
            if (listaMiembros.size() < 6) {
                FXMLLoader cargarTarjeta = new FXMLLoader(getClass().getResource("fxml/tarjeta_añadir_miembro_v1.fxml"));
                SplitPane tarjeta = cargarTarjeta.load();
                controllerTarjetaAñadirMiembro controladorTarjeta = cargarTarjeta.getController();
                controladorTarjeta.asignarConexion(conexion, equipo);
                utils.crearTooltip("Añadir miembro", tarjeta);
                gridMiembros.add(tarjeta, col, row);
            }
            ces.cargarMiembros();
        } catch (IOException ex) {
            Logger.getLogger(controllerEquipo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void crearLista() {
        try {
            listaMiembros.clear();
            String query = "SELECT Especie, N_Pokedex, Mote, Genero, Nivel, Habilidad, Naturaleza, Objeto, "
                    + "Tipo_1, Tipo_2, Habilidades, Movimientos, Estadisticas, EVs, IVs, Sprite "
                    + "FROM equipo JOIN pokemon USING (N_Pokedex) WHERE ID_Equipo = " + equipo.getIdEquipo();
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
                listaMiembros.add(tempMiembro);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(controllerEquipos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void enviaMiembros(Equipo e, Conexion c) {
        conexion = c;
        txtNombreEquipo.setText(e.getNombre());
        equipo = e;
        utils.crearTooltip("Equipo: " + e.getNombre(), txtNombreEquipo);
        cargarMiembros();
    }
    
    void setControladorEnlace(controllerEquipos c) {
        ces = c;
    }
}
