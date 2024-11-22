/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

import com.fcm.pokeTeams.util.Utilidades;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author DFran49
 */
public class controllerSignIn implements Initializable {
    Utilidades utils = new Utilidades();
    
    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnInicio;

    @FXML
    private ToggleGroup genero;

    @FXML
    private ImageView imgRegistro;

    @FXML
    private PasswordField pwConfContraseña;

    @FXML
    private PasswordField pwContraseña;

    @FXML
    private RadioButton rbFemenino;

    @FXML
    private RadioButton rbMasculino;

    @FXML
    private RadioButton rbOtro;

    @FXML
    private TextField txtNombre;

    @FXML
    void registrar() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/core_v1.fxml"));
            Scene scene=new Scene(root);
            Stage miStage = (Stage) this.txtNombre.getScene().getWindow();
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            miStage.setX((screenBounds.getWidth() - miStage.getWidth()) / 2 - miStage.getWidth() / 4);
            miStage.setY((screenBounds.getHeight() - miStage.getHeight()) / 2);
            miStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(controllerSignIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void inicio() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/logIn.fxml"));
            Scene scene=new Scene(root);
            Stage miStage = (Stage) this.txtNombre.getScene().getWindow();
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            miStage.setX((screenBounds.getWidth() - miStage.getWidth()) / 2);
            miStage.setY((screenBounds.getHeight() - miStage.getHeight() /1.3));
            miStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(controllerLogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    void subirImagen() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagen jpg", "*.jpg"),
                new FileChooser.ExtensionFilter("Imagen png", "*.png")
        );
        File archivoSeleccionado = fileChooser.showOpenDialog(null);
            if (archivoSeleccionado != null) {
                String rutaArchivo = archivoSeleccionado.toURI().toString();
                Image imagen = new Image(rutaArchivo);
                imgRegistro.setImage(imagen);
            }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        utils.crearTooltip("Selecciona tu imagen", imgRegistro);
    }
}
