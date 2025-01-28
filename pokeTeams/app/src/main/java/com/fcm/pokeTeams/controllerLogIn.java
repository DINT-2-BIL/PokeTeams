/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

import com.fcm.pokeTeams.util.Alertas;
import com.fcm.pokeTeams.util.Conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 *
 * @author DFran49
 */
public class controllerLogIn implements Initializable {
    private controllerCore cc;
    Scene inicio;
    Conexion conexion;
    List<ValidationSupport> validadores;
    
    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnRegistro;

    @FXML
    private PasswordField pwContraseña;

    @FXML
    private TextField txtNombre;

    @FXML
    void iniciarSesion() {
        boolean todoOK = true;
        for (ValidationSupport validationSupport : validadores) {
            todoOK = (todoOK && validationSupport.getValidationResult().getErrors().isEmpty());
        }

        if (todoOK) {
            try {
                String query = "SELECT * FROM entrenador WHERE Nombre = '" + txtNombre.getText() + "' && Contraseña = '" + pwContraseña.getText() + "'";

                Statement statement = conexion.getConexion().createStatement();
                ResultSet result = statement.executeQuery(query);  
                result.next();
                result.getString("Nombre");

                //Parent root = FXMLLoader.load(getClass().getResource("/fxml/core_v1.fxml"));
                this.cc.enviaLogIn(this.conexion, txtNombre.getText());

                Stage miStage = (Stage) this.txtNombre.getScene().getWindow();
                miStage.setTitle("PokeTeams");
                miStage.setScene(inicio);
                miStage.centerOnScreen();
            }  catch (SQLException ex) {
                Alertas credencialesIncorrectas = new Alertas(Alert.AlertType.ERROR, "CREDENCIALES INCORRECTAS", 
                            "Ha introducido el nombre o la contraseña incorrecta!", "Intentelo de nuevo.");
                    credencialesIncorrectas.mostrarAlerta();
            }
        } else {
            new Alertas(Alert.AlertType.WARNING, "Algo falló", "Incoherencia con las restricciones", 
                        "Debe rellenar todos los campos y asegurarse de que siguen el formato que puede ver en el iconito de X pequeño").mostrarAlerta();
        }
    }

    @FXML
    void registro() {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signIn.fxml"));
            root = loader.load();
            controllerSignIn csi = loader.getController();
            csi.asignarConexion(conexion);
            Scene scene=new Scene(root);
            Stage miStage = (Stage) this.txtNombre.getScene().getWindow();
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            miStage.setX((screenBounds.getWidth() - miStage.getWidth()) / 2);
            miStage.setY((screenBounds.getHeight() - miStage.getHeight()) / 2 - miStage.getHeight() / 2);
            miStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(controllerLogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conexion = new Conexion();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/core_v1.fxml"));
        try {
            root = loader.load();
            cc = loader.getController();
            cc.setControladorEnlace(this);
            inicio = new Scene(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        
        ValidationSupport vSNombre = new ValidationSupport();
        vSNombre.registerValidator(txtNombre, Validator.createPredicateValidator(
            texto -> {
                if (texto == null || texto.toString().isEmpty()) {
                return false;
                }
                try {
                    int numero = texto.toString().length();
                    return numero >= 3 && numero <= 20 && txtNombre.getText().matches("^[A-Za-z0-9. ]+$");
                } catch (NumberFormatException e) {
                    return false;
                }
            },
            "El nombre puede tener mínimo 3 caracteres y 20 de máximo y solo puede contener letras, números o puntos"
        ));
        
        ValidationSupport vSContraseña = new ValidationSupport();
        vSContraseña.registerValidator(pwContraseña, Validator.createPredicateValidator(
            texto -> {
                if (texto == null || texto.toString().isEmpty()) {
                return false;
                }
                try {
                    int numero = texto.toString().length();
                    return numero >= 3 && numero <= 20;
                } catch (NumberFormatException e) {
                    return false;
                }
            },
            "La contraseña puede tener mínimo 3 caracteres, 20 de máximo"
        ));
        
        validadores = new ArrayList<>();
        validadores.addAll(Arrays.asList(vSNombre, vSContraseña));

        Platform.runLater(() -> {
            for (ValidationSupport validationSupport : validadores) {
                validationSupport.initInitialDecoration();
            }
        });
    }
    
    public void asignarConexion(Conexion c) {
        conexion = c;
    }
}
