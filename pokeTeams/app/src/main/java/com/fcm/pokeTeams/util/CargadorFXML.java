/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.util;

import com.fcm.pokeTeams.controllerCore;
import com.fcm.pokeTeams.controllerEquipo;
import com.fcm.pokeTeams.enums.VistasControladores;
import com.fcm.pokeTeams.modelos.Equipo;
import com.fcm.pokeTeams.modelos.Miembro;
import com.fcm.pokeTeams.modelos.Pokemon;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author DFran49
 */
public class CargadorFXML {

    private static final CargadorFXML instance = new CargadorFXML();
    private controllerCore cCore;
    private controllerEquipo cEqu;
    private boolean ayudaMostrada = false;

    private CargadorFXML() {
    }

    public static CargadorFXML getInstance() {
        return instance;
    }

    public <T> void cargar(VistasControladores vc, Stage destino) {
        String vista = String.format("/fxml/%s.fxml", vc.getVista());
        String icono = String.format("/img/%s", vc.getRutaIcon());

        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
        try {
            root = loader.load();
        } catch (IOException ex) {
            System.err.println("No se ha podido cargar el archivo: " + vc.getVista());
        }
        Scene escena = new Scene(root);
        destino.setTitle(vc.getTitulo());
        destino.getIcons().add(new Image(icono));
        destino.setResizable(false);
        destino.centerOnScreen();
        asignarAyuda(destino);
        
        destino.setScene(escena);
        if (vc.equals(vc.INICIO)) {
            cCore = loader.getController();
        }
        if (vc.equals(VistasControladores.EQUIPO)) {
            cEqu = loader.getController();
        }
    }

    public controllerCore getControllerCore() {
        return cCore;
    }

    public void cerrarSesion() {
        cCore = null;
    }

    public controllerEquipo getControllerEquipo() {
        return cEqu;
    }

    public void cerrarEquipo() {
        cEqu = null;
    }

    public void cargar(GridPane grid, int columna, int fila, Equipo equipo) {
        VistasControladores vc = VistasControladores.ENTRADAEQUIPO;
        try {
            String vista = String.format("/fxml/%s.fxml", vc.getVista());
            FXMLLoader cargar = new FXMLLoader(getClass().getResource(vista));
            SplitPane tarjeta = cargar.load();
            tarjeta.setUserData(equipo);
            Utilidades.getInstance().crearTooltip(equipo.getNombre(), tarjeta);
            grid.add(tarjeta, columna, fila);
        } catch (IOException ex) {
            System.err.println("No se ha podido cargar el archivo: " + vc.getVista());
        }
    }

    public void cargar(GridPane grid, int columna, int fila, Pokemon pokemon) {
        VistasControladores vc = VistasControladores.ENTRADAPOKEMON;
        try {
            String vista = String.format("/fxml/%s.fxml", vc.getVista());
            FXMLLoader cargar = new FXMLLoader(getClass().getResource(vista));
            SplitPane tarjeta = cargar.load();
            tarjeta.setUserData(pokemon);
            Utilidades.getInstance().crearTooltip(pokemon.getEspecie(), tarjeta);
            grid.add(tarjeta, columna, fila);
        } catch (IOException ex) {
            System.err.println("No se ha podido cargar el archivo: " + vc.getVista());
        }
    }

    public void cargar(GridPane grid, int columna, int fila, Miembro miembro) {
        VistasControladores vc = VistasControladores.ENTRADAMIEMBRO;
        if (miembro == null) {
            vc = VistasControladores.ENTRADAADDMIEMBRO;
        }
        try {
            String vista = String.format("/fxml/%s.fxml", vc.getVista());
            FXMLLoader cargar = new FXMLLoader(getClass().getResource(vista));
            SplitPane tarjeta = cargar.load();
            tarjeta.setUserData(miembro);
            Utilidades.getInstance().crearTooltip("Desplegar ".concat(miembro.getMote()), tarjeta);
            grid.add(tarjeta, columna, fila);
        } catch (IOException ex) {
            System.err.println("No se ha podido cargar el archivo: " + vc.getVista());
        }
    }

    public void cargarAñadirMiembro(GridPane grid, int columna, int fila, Equipo e) {
        VistasControladores vc = VistasControladores.ENTRADAADDMIEMBRO;
        try {
            String vista = String.format("/fxml/%s.fxml", vc.getVista());
            FXMLLoader cargar = new FXMLLoader(getClass().getResource(vista));
            SplitPane tarjeta = cargar.load();
            tarjeta.setUserData(e);
            Utilidades.getInstance().crearTooltip("Añadir miembro al equipo", tarjeta);
            grid.add(tarjeta, columna, fila);
        } catch (IOException ex) {
            System.err.println("No se ha podido cargar el archivo: " + vc.getVista());
        }
    }

    public void cargar(Stage v, String t) {
        VistasControladores vc = VistasControladores.ELIMINAR;

        String vista = String.format("/fxml/%s.fxml", vc.getVista());
        String icono = String.format("/img/%s", vc.getRutaIcon());
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
        try {
            root = loader.load();
        } catch (IOException ex) {
            System.err.println("No se ha podido cargar el archivo: " + vc.getVista());
        }
        Scene inicio = new Scene(root);
        v.setScene(inicio);
        v.setOnCloseRequest(evento -> {
            evento.consume();
        });
        v.setTitle(vc.getTitulo() + t);
        v.getIcons().add(new Image(icono));
        v.setAlwaysOnTop(true);
        v.setResizable(false);
        asignarAyuda(v);
    }
    
    private void asignarAyuda(Stage escena) {
        escena.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.F1 && !ayudaMostrada) {
                WebView wvnuevo = new WebView();
                wvnuevo.getEngine().load(new File("manualUser.html").toURI().toString());
                StackPane stackPane = new StackPane(wvnuevo);
                Scene scene = new Scene(stackPane, 900, 800);
                Stage stage = new Stage();
                stage.setTitle("Informe en HTML");
                stage.getIcons().add(new Image("/img/pokedex.png"));
                stage.setResizable(false);
                stage.setScene(scene);
                stage.setOnCloseRequest(evento -> {
                    ayudaMostrada = false;
                });
                stage.show();
                ayudaMostrada = true;
            }
        });
    }
}
