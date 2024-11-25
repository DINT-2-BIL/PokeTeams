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
import com.fcm.pokeTeams.util.Utilidades;
import java.net.URL;
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
import javafx.stage.Stage;

public class controllerConfirmar implements Initializable{
    Stage entrada;
    Scene escena;
    Utilidades utils = new Utilidades();
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
        //System.out.println(((TextField)entrada.getScene().lookup("#txtEspecie")).getText());
        escena = entrada.getScene();
        cargarDatosPokemon();
        if ((int) a.getUserData() == 2) {
            editarPokemon();
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
        pokemon.setTipo1(((ComboBox<String>)escena.lookup("#cbTipo1")).getValue());
        pokemon.setTipo2(((ComboBox<String>)escena.lookup("#cbTipo2")).getValue());
        pokemon.setEstadisticas(leerStats());
        System.out.println(leerHabilidades());
        pokemon.setHabilidades(leerHabilidades());
    }
    
    private void insertarPokemon() {
        
    }
    
    private void editarPokemon() {
        System.out.println(pokemon.getEspecie());
    }
    
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
        
        return utils.escribirHabilidades(new HabilidadesEnvoltorio(habilidades));
    }

    public void enviaStage(Stage s) {
        entrada = s;
        Stage ventana = (Stage) this.btnCancelar.getScene().getWindow();
        ventana.setOnCloseRequest(event -> event.consume());
    }
}
