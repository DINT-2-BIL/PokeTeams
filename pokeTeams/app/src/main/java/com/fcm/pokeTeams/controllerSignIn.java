/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

import com.fcm.pokeTeams.modelos.Generos;
import com.fcm.pokeTeams.util.Alertas;
import com.fcm.pokeTeams.util.Conexion;
import com.fcm.pokeTeams.util.Utilidades;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
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
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 *
 * @author DFran49
 */
public class controllerSignIn implements Initializable {
    Utilidades utils = new Utilidades();
    private Conexion conexion;
    List<ValidationSupport> validadores;
    
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
        boolean todoOK = true;
        for (ValidationSupport validationSupport : validadores) {
            todoOK = (todoOK && validationSupport.getValidationResult().getErrors().isEmpty());
        }
        if (todoOK) {
            if (!imgRegistro.getImage().getUrl().equals("/img/add.png")) {
                try {
                    Parent root = null;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/core_v1.fxml"));
                    root = loader.load();
                    insertar();
                    controllerCore cc = loader.getController();
                    cc.enviaLogIn(conexion, txtNombre.getText());
                    Scene scene=new Scene(root);
                    Stage miStage = (Stage) this.txtNombre.getScene().getWindow();
                    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                    miStage.setX((screenBounds.getWidth() - miStage.getWidth()) / 2 - miStage.getWidth() / 4);
                    miStage.setY((screenBounds.getHeight() - miStage.getHeight()) / 2);
                    miStage.setScene(scene);
                } catch (IOException ex) {
                    Logger.getLogger(controllerSignIn.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                new Alertas(Alert.AlertType.WARNING, "Falta algo", "Imagen no seleccionada", 
                        "Elija una imagen (pulsando en el icono de +)").mostrarAlerta();
            }
        } else {
            new Alertas(Alert.AlertType.WARNING, "Algo falló", "Incoherencia con las restricciones", 
                        "Debe rellenar todos los campos y asegurarse de que siguen el formato que puede ver en el iconito de X pequeño").mostrarAlerta();
        }
    }

    @FXML
    void inicio() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/logIn.fxml"));
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
        inicializarRButtons();
        
        ValidationSupport vSNombre = new ValidationSupport();
        vSNombre.registerValidator(txtNombre, Validator.createPredicateValidator(
            texto -> {
                if (texto == null || texto.toString().isEmpty()) {
                return false;
                }
                try {
                    int numero = texto.toString().length();
                    return numero >= 1 && numero <= 20 && txtNombre.getText().matches("^[A-Za-z0-9. ]+$");
                } catch (NumberFormatException e) {
                    return false;
                }
            },
            "Debe tener el nombre introducido entre 1 y 20 y solo puede contener letras, números o puntos"
        ));
       
        
        ValidationSupport vSContraseña = new ValidationSupport();
        vSContraseña.registerValidator(pwContraseña, Validator.createPredicateValidator(
            texto -> {
                String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
                if (texto == null || texto.toString().isEmpty()) {
                return false;
                }
                try {
                    int numero = texto.toString().length();
                    return numero >= 4 && pwContraseña.getText().matches(regex);
                } catch (NumberFormatException e) {
                    return false;
                }
            },
            "La contraseña debe tener mínimo 8 caracteres, 20 de máximo, tener una letra, un número y un carácter especial (@$!%*?&)"
        ));
        
        ValidationSupport vSContraseñaConf = new ValidationSupport();
        vSContraseñaConf.registerValidator(pwConfContraseña, Validator.createPredicateValidator(
            texto -> {
                if (texto == null || texto.toString().isEmpty()) {
                return false;
                }
                try {
                    return pwContraseña.getText().equals(pwConfContraseña.getText());
                } catch (NumberFormatException e) {
                    return false;
                }
            },
            "La confirmación debe ser igual a la contraseña"
        ));
        
        validadores = new ArrayList<>();
        validadores.addAll(Arrays.asList(vSNombre, vSContraseña, vSContraseñaConf));

        Platform.runLater(() -> {
            for (ValidationSupport validationSupport : validadores) {
                validationSupport.initInitialDecoration();
            }
        });
    }
    
    private void insertar() {
        PreparedStatement preparado = null;
        try {
            String query = "INSERT INTO entrenador (Nombre, Genero, Sprite, Contraseña) "
                    + "VALUES (?, ?, ?, ?)";
            Connection c = conexion.getConexion();
            preparado = c.prepareStatement(query);

            preparado.setString(1, txtNombre.getText());
            preparado.setString(2, genero.getSelectedToggle().getUserData().toString());
            preparado.setString(3, utils.codificarImagen(imgRegistro.getImage()));
            preparado.setString(4, pwContraseña.getText());

            if (preparado.executeUpdate() > 0) {
                System.out.println("Inserción exitosa.");
            } else {
                System.out.println("No se insertó el Equipo.");
            }
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
    
    private void inicializarRButtons() {
        rbFemenino.setUserData(Generos.F.getSigla());
        rbMasculino.setUserData(Generos.M.getSigla());
        rbOtro.setUserData(Generos.O.getSigla());
    }
    
    public void asignarConexion(Conexion c) {
        conexion = c;
    }
}
