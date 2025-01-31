/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

import com.fcm.pokeTeams.DAO.EntrenadorDAO;
import com.fcm.pokeTeams.modelos.Entrenador;
import com.fcm.pokeTeams.enums.Generos;
import com.fcm.pokeTeams.enums.VistasControladores;
import com.fcm.pokeTeams.util.Alertas;
import com.fcm.pokeTeams.util.CargadorFXML;
import com.fcm.pokeTeams.util.Utilidades;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 *
 * @author DFran49
 */
public class controllerSignIn implements Initializable {

    private Entrenador entrenador;
    private Utilidades utils = Utilidades.getInstance();
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
            if (!imgRegistro.getImage().getUrl().equals(new Image("/img/add.png").getUrl())) {
                insertar();
                CargadorFXML.getInstance().cargar(VistasControladores.INICIO, (Stage) this.txtNombre.getScene().getWindow());
                CargadorFXML.getInstance().getControllerCore().entrenador = entrenador;
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
        CargadorFXML.getInstance().cargar(VistasControladores.LOGIN, (Stage) this.txtNombre.getScene().getWindow());
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
                        return numero >= 1 && numero <= 20 && txtNombre.getText().matches("^[\\p{L}0-9. ]{3,}$");
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                "El nombre puede tener mínimo 3 caracteres y 20 de máximo y solo puede contener letras, números o puntos"
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
        entrenador = new Entrenador();
        entrenador.setNombre(txtNombre.getText());
        entrenador.setGenero((char) genero.getSelectedToggle().getUserData());
        entrenador.setSprite(utils.codificarImagen(imgRegistro.getImage()));
        entrenador.setContraseña(pwContraseña.getText());
        entrenador.setEsAdmin(false);
        EntrenadorDAO.getInstance().insert(entrenador);
    }

    private void inicializarRButtons() {
        rbFemenino.setUserData(Generos.F.getSigla());
        rbMasculino.setUserData(Generos.M.getSigla());
        rbOtro.setUserData(Generos.O.getSigla());
    }
}
