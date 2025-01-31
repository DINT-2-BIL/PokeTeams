/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author DFran49
 */
import com.fcm.pokeTeams.DAO.MiembroDAO;
import com.fcm.pokeTeams.DAO.PokemonDAO;
import com.fcm.pokeTeams.modelos.Miembro;
import com.fcm.pokeTeams.modelos.Pokemon;
import com.fcm.pokeTeams.util.Alertas;
import com.fcm.pokeTeams.util.CargadorFXML;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class controllerConfirmar implements Initializable {
    private controllerCore cCore = CargadorFXML.getInstance().getControllerCore();
    private controllerEquipo cEqu = CargadorFXML.getInstance().getControllerEquipo();
    Stage ventana;
    private Stage estaVentana;
    private int opcion;
    private Pokemon pokemon = new Pokemon();
    private Miembro miembro = new Miembro();
    private ArrayList<Object> datos = new ArrayList<>();
    
    
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
        a.close();
        ventana.close();
    }

    @FXML
    void guardar(ActionEvent event) {
        try {
            switch (opcion) {
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
                    insertarMiembro();
                }
                case 4 -> {
                    cargarDatosMiembro();
                    editarMiembro();
                }
            }
            estaVentana.close();
        } catch (Exception e) {
            new Alertas(Alert.AlertType.WARNING, "Algo falló", "Faltan datos",
                    "Debe rellenar todos los campos y asegurarse de que siguen el formato que puede ver en el iconito de X pequeño").mostrarAlerta();
            System.err.println(e.getMessage());
        }
        
        ventana.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            estaVentana = (Stage) btnCancelar.getScene().getWindow();
            datos = (ArrayList<Object>) estaVentana.getUserData();
            ventana = (Stage) datos.get(1);
            opcion = (int) datos.get(0);
        });
    }
    
    private void cargarDatosPokemon() {
        pokemon = (Pokemon) datos.get(2);
    }
    
    private void cargarDatosMiembro() {
        miembro = (Miembro) datos.get(2);
    }
    
    private void insertarPokemon() {
        PokemonDAO.getInstance().insert(pokemon);
        cCore.cargarGridPokemon();
    }
    
    private void editarPokemon() {
        PokemonDAO.getInstance().update(pokemon);
        cCore.cargarGridPokemon();
    }
    
    private void insertarMiembro() {
        MiembroDAO.getInstance().insert(miembro);
        cEqu.cargarMiembros();
        cCore.cargarGridEquipo();
    }
    
    private void editarMiembro() {
        MiembroDAO.getInstance().update(miembro);
        cEqu.cargarMiembros();
        cCore.cargarGridEquipo();
    }
}
