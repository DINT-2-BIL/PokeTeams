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
import java.util.List;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author DFran49
 */
public class controllerLogIn implements Initializable {
    private controllerCore cc;
    Scene inicio;
    Conexion conexion;
    
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
        try {
            String query = "SELECT * FROM entrenador WHERE Nombre = '" + txtNombre.getText() + "' && Contraseña = '" + pwContraseña.getText() + "'";
            
            Statement statement = conexion.getConexion().createStatement();
            ResultSet result = statement.executeQuery(query);  
            result.next();
            result.getString("Nombre");
            
            Parent root = FXMLLoader.load(getClass().getResource("fxml/core_v1.fxml"));
            this.cc.enviaLogIn(this.conexion, txtNombre.getText());
   
            Stage miStage = (Stage) this.txtNombre.getScene().getWindow();
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            miStage.setX((screenBounds.getWidth() - miStage.getWidth()) / 2 - miStage.getWidth() / 4);
            miStage.setY((screenBounds.getHeight() - miStage.getHeight()) / 2 - miStage.getHeight() / 2.3);
            miStage.setTitle("PokeTeams");
            miStage.setScene(inicio);
        } catch (IOException ex) {
            Logger.getLogger(controllerLogIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Alertas credencialesIncorrectas = new Alertas(Alert.AlertType.ERROR, "CREDENCIALES INCORRECTAS", 
                        "Ha introducido el nombre o la contraseña incorrecta!", "Intentelo de nuevo.");
                credencialesIncorrectas.mostrarAlerta();
        }
    }

    @FXML
    void registro() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/signIn.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/core_v1.fxml"));
        try {
            root = loader.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        cc = loader.getController();
        cc.setControladorEnlace(this);

        inicio = new Scene(root);
    }
}
