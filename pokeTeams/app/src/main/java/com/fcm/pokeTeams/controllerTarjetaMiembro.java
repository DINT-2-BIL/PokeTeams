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
import com.fcm.pokeTeams.enums.VistasControladores;
import com.fcm.pokeTeams.modelos.Miembro;
import com.fcm.pokeTeams.util.CargadorFXML;
import com.fcm.pokeTeams.util.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class controllerTarjetaMiembro implements Initializable {

    Utilidades util = Utilidades.getInstance();
    private Miembro miembro;

    @FXML
    private ImageView imgPokemonMiembro;

    @FXML
    private ContextMenu menu;

    @FXML
    private SplitPane tarjeta;

    @FXML
    private Label txtNombreMiembro;

    @FXML
    void editar(ActionEvent event) {
        Stage ventana = new Stage();
        CargadorFXML.getInstance().cargar(VistasControladores.ADDEDITMIEMBRO, ventana);

        ventana.setUserData(miembro);
        ventana.setTitle("Editar ".concat(miembro.getMote()));
        ventana.getIcons().add(Utilidades.getInstance().getImage(miembro.getSprite()));
        ventana.show();
    }

    @FXML
    void verMiembro(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            Stage ventana = new Stage();
            CargadorFXML.getInstance().cargar(VistasControladores.MIEMBRO, ventana);
            ventana.setUserData(miembro);
            ventana.setTitle("Datos de ".concat(miembro.getMote()));
            ventana.getIcons().add(Utilidades.getInstance().getImage(miembro.getSprite()));
            ventana.show();
        }
    }

    @FXML
    void eliminar(ActionEvent event) {
        Stage ventana = new Stage();
        CargadorFXML.getInstance().cargar(ventana, "Eliminar " + miembro.getMote());
        ventana.showAndWait();
        if ((boolean) ventana.getUserData()) {
            MiembroDAO.getInstance().delete(miembro);
            CargadorFXML.getInstance().getControllerCore().cargarGridEquipo();
            CargadorFXML.getInstance().getControllerEquipo().cargarMiembros();
            System.out.println(miembro.getEspecie() + " eliminado.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            miembro = (Miembro) tarjeta.getUserData();
            System.out.println(miembro.getNaturaleza());
            txtNombreMiembro.setText(miembro.getMote());
            util.recuperarImagenBBDD(miembro.getSprite(), imgPokemonMiembro);
        });
    }
}
