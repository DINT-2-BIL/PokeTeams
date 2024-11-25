/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author DFran49
 */
import com.fcm.pokeTeams.modelos.EstadisticasEnvoltorio;
import com.fcm.pokeTeams.modelos.Habilidad;
import com.fcm.pokeTeams.modelos.HabilidadesEnvoltorio;
import com.fcm.pokeTeams.modelos.Miembro;
import com.fcm.pokeTeams.modelos.Pokemon;
import com.fcm.pokeTeams.modelos.Stat;
import com.fcm.pokeTeams.util.Conexion;
import com.fcm.pokeTeams.util.Utilidades;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class controllerConfirmar implements Initializable {
    private controllerCore cCore;
    Stage entrada;
    Scene escena;
    Utilidades utils = new Utilidades();
    Conexion conexion = null;
    private boolean admin;
    private Pokemon pokemon = new Pokemon();
    private Miembro miembro = new Miembro();
    
    @FXML
    private Button btnCancelar;

    @FXML
    void cancelar(ActionEvent event) {
        Stage a = (Stage) btnCancelar.getScene().getWindow();
        a.close();
    }

    @FXML
    void cerrar(ActionEvent event) {
        Stage a = (Stage) btnCancelar.getScene().getWindow();
        a.setUserData(false);
        a.close();
        entrada.close();
    }

    @FXML
    void guardar(ActionEvent event) {
        Stage a = (Stage) btnCancelar.getScene().getWindow();
        a.close();
        escena = entrada.getScene();
        
        switch ((int) a.getUserData()) {
            case 1 -> {
                cargarDatosPokemon();
                insertarPokemon();
            }
            case 2 -> {
                cargarDatosPokemon();
                editarPokemon();
            }
            case 3 -> {
                cargarDatosMiembro();
                //insertarMiembro();
            }
            case 4 -> {
                cargarDatosMiembro();
                //editarMiembro();
            }
        }
        entrada.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    private void cargarDatosPokemon() {
        pokemon.setEspecie(((TextField)escena.lookup("#txtEspecie")).getText());
        pokemon.setDenominacion(((TextArea)escena.lookup("#txtDenominacion")).getText());
        pokemon.setDescripcion(((TextArea)escena.lookup("#txtDescripcion")).getText());
        String a = ((TextArea)escena.lookup("#txtTamaño")).getText();
        a = a.replace(" metros", "");
        pokemon.setTamaño(Double.parseDouble(a));
        a = ((TextArea)escena.lookup("#txtPeso")).getText();
        a = a.replace(" kilogramos", "");
        pokemon.setPeso(Double.parseDouble(a));
        pokemon.setSprite(utils.codificarImagen(((ImageView)escena.lookup("#imgPokemon")).getImage()));
        pokemon.setTipo1(((ComboBox<String>)escena.lookup("#cbTipo1")).getValue());
        pokemon.setTipo2(((ComboBox<String>)escena.lookup("#cbTipo2")).getValue());
        pokemon.setEstadisticas(leerStats());
        pokemon.setHabilidades(leerHabilidades());
    }
    
    private void insertarPokemon() {
        PreparedStatement preparado = null;
        try {
            String query = "INSERT INTO pokemon (Especie, Denominacion, Descripcion, Sprite, Tipo_1, Tipo_2, Tamaño, Peso, Habilidades, Estadisticas) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Connection c = conexion.getConexion();
            preparado = c.prepareStatement(query);

            preparado.setString(1, pokemon.getEspecie());
            preparado.setString(2, pokemon.getDenominacion());
            preparado.setString(3, pokemon.getDescripcion());
            preparado.setString(4, pokemon.getSprite());
            preparado.setString(5, pokemon.getTipo1());
            preparado.setString(6, pokemon.getTipo2());
            preparado.setDouble(7, pokemon.getTamaño());
            preparado.setDouble(8, pokemon.getPeso());
            preparado.setString(9, pokemon.getHabilidades());
            preparado.setString(10, pokemon.getEstadisticas());

            if (preparado.executeUpdate() > 0) {
                System.out.println("Inserción exitosa.");
            } else {
                System.out.println("No se insertó el Equipo.");
            }
            cCore.cargarGridPokemon(admin);
        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        } finally {
            try {
                if (preparado != null) preparado.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void editarPokemon() {
        PreparedStatement preparado = null;
        try {
            String query = "UPDATE pokemon SET Especie = ?, Denominacion = ?, Descripcion = ?, Sprite = ?, Tipo_1 = ?, Tipo_2 = ?, "
                    + "Tamaño = ?, Peso = ?, Habilidades = ?, Estadisticas = ? WHERE N_Pokedex = ?;";
            Connection c = conexion.getConexion();
            preparado = c.prepareStatement(query);

            preparado.setString(1, pokemon.getEspecie());
            preparado.setString(2, pokemon.getDenominacion());
            preparado.setString(3, pokemon.getDescripcion());
            preparado.setString(4, pokemon.getSprite());
            preparado.setString(5, pokemon.getTipo1());
            preparado.setString(6, pokemon.getTipo2());
            preparado.setDouble(7, pokemon.getTamaño());
            preparado.setDouble(8, pokemon.getPeso());
            preparado.setString(9, pokemon.getHabilidades());
            preparado.setString(10, pokemon.getEstadisticas());
            preparado.setInt(11, Integer.parseInt(pokemon.getnPokedex()));

            if (preparado.executeUpdate() > 0) {
                System.out.println("Inserción exitosa.");
            } else {
                System.out.println("No se insertó el Equipo.");
            }
            cCore.cargarGridPokemon(admin);
        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        } finally {
            try {
                if (preparado != null) preparado.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
     private void cargarDatosMiembro() {}
    
    private void insertarMiembro() {
        
    }
    
    private void editarMiembro() {
        
    }
    
    private String leerStats() {
        List<Stat> stats = new ArrayList<>();
        stats.add(new Stat("HP", (int)((Slider) escena.lookup("#sdHp")).getValue()));
        stats.add(new Stat("Atk", (int)((Slider) escena.lookup("#sdAtk")).getValue()));
        stats.add(new Stat("Def", (int)((Slider) escena.lookup("#sdDef")).getValue()));
        stats.add(new Stat("SpA", (int)((Slider) escena.lookup("#sdSpA")).getValue()));
        stats.add(new Stat("SpD", (int)((Slider) escena.lookup("#sdSpD")).getValue()));
        stats.add(new Stat("SpE", (int)((Slider) escena.lookup("#sdSpe")).getValue()));
        
        EstadisticasEnvoltorio estadisticas = new EstadisticasEnvoltorio(stats);
        return utils.escribirStats(estadisticas);
    }
    
    private String leerHabilidades() {
        List<Habilidad> habilidades = new ArrayList<>();
        habilidades.add(new Habilidad(((TextField)escena.lookup("#txtNombreHabilidad1")).getText(),
                ((TextArea)escena.lookup("#txtHabilidad1")).getText()));
        if (!((TextField)escena.lookup("#txtNombreHabilidad2")).getText().isEmpty() && !((TextArea)escena.lookup("#txtHabilidad2")).getText().isEmpty()) {
            habilidades.add(new Habilidad(((TextField)escena.lookup("#txtNombreHabilidad2")).getText(),
                ((TextArea)escena.lookup("#txtHabilidad2")).getText()));
        }
        if (!((TextField)escena.lookup("#txtNombreHabilidad3")).getText().isEmpty() && !((TextArea)escena.lookup("#txtHabilidad3")).getText().isEmpty()) {
            habilidades.add(new Habilidad(((TextField)escena.lookup("#txtNombreHabilidad3")).getText(),
                ((TextArea)escena.lookup("#txtHabilidad3")).getText()));
        }
        return utils.escribirHabilidades(new HabilidadesEnvoltorio(habilidades));
    }

    public void enviarAPokemon(Stage s, Conexion c, controllerCore controlador, boolean admin) {
        entrada = s;
        Stage ventana = (Stage) this.btnCancelar.getScene().getWindow();
        ventana.setOnCloseRequest(event -> event.consume());
        conexion = c;
        this.admin = admin;
        cCore = (controllerCore) controlador;
    }
    
    public void enviarEditarPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }
    
    public void enviaStage(Stage s, Conexion c, Object controlador, boolean admin) {
        entrada = s;
        Stage ventana = (Stage) this.btnCancelar.getScene().getWindow();
        ventana.setOnCloseRequest(event -> event.consume());
        conexion = c;
        this.admin = admin;
        if (controlador instanceof controllerCore) {
            cCore = (controllerCore) controlador;
        }
    }
}
