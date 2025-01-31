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
import com.fcm.pokeTeams.modelos.Pokemon;
import com.fcm.pokeTeams.enums.Tipos;
import com.fcm.pokeTeams.enums.VistasControladores;
import com.fcm.pokeTeams.modelos.Stat;
import com.fcm.pokeTeams.util.Alertas;
import com.fcm.pokeTeams.util.CargadorFXML;
import com.fcm.pokeTeams.util.Utilidades;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class controllerAñadirPokemon implements Initializable {

    private List<Slider> listSliders = new ArrayList<>();
    private List<Label> listLabels = new ArrayList<>();
    private List<ValidationSupport> validadores;
    private List<TextField> listPaneles;
    private List<TextArea> listText;

    private ArrayList<Object> datos = new ArrayList<>();
    Utilidades utils = Utilidades.getInstance();
    Pokemon pokemon;
    private int tipoPaso;
    private int id;

    @FXML
    private TitledPane panelHabilidad1;

    @FXML
    private ComboBox<String> cbTipo1;

    @FXML
    private ComboBox<String> cbTipo2;

    @FXML
    private ImageView imgPokemon;

    @FXML
    private Slider sdAtk;

    @FXML
    private Slider sdDef;

    @FXML
    private Slider sdHp;

    @FXML
    private Slider sdSpA;

    @FXML
    private Slider sdSpD;

    @FXML
    private Slider sdSpe;

    @FXML
    private Label txtAtk;

    @FXML
    private Label txtDef;

    @FXML
    private TextField txtDenominacion;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtEspecie;

    @FXML
    private TextArea txtHabilidad1;

    @FXML
    private TextArea txtHabilidad2;

    @FXML
    private TextArea txtHabilidad3;

    @FXML
    private Label txtHp;

    @FXML
    private TextField txtNombreHabilidad1;

    @FXML
    private TextField txtNombreHabilidad2;

    @FXML
    private TextField txtNombreHabilidad3;

    @FXML
    private TextField txtPeso;

    @FXML
    private Label txtSpA;

    @FXML
    private Label txtSpD;

    @FXML
    private Label txtSpe;

    @FXML
    private TextField txtTamaño;

    @FXML
    void finalizar(ActionEvent event) {
        Stage ventana = (Stage) txtAtk.getScene().getWindow();
        ventana.fireEvent(new WindowEvent(ventana, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    void subirImagen(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagen jpg", "*.jpg"),
                new FileChooser.ExtensionFilter("Imagen png", "*.png")
        );
        File archivoSeleccionado = fileChooser.showOpenDialog(null);
        if (archivoSeleccionado != null) {
            String rutaArchivo = archivoSeleccionado.toURI().toString();
            Image imagen = new Image(rutaArchivo);
            imgPokemon.setImage(imagen);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datos = new ArrayList<>();
        listPaneles = List.of(txtNombreHabilidad1, txtNombreHabilidad2, txtNombreHabilidad3);
        listText = listText = List.of(txtHabilidad1, txtHabilidad2, txtHabilidad3);
        cbTipo1.getItems().addAll(Tipos.listaTipo1());
        cbTipo2.getItems().addAll(Tipos.listaTipo2());
        cbTipo2.getSelectionModel().select(Tipos.NINGUNO.getTipo());
        utils.crearTooltip("Seleccionar imagen", imgPokemon);
        inicializarSliders();

        ValidationSupport vEsp = new ValidationSupport();
        vEsp.registerValidator(txtEspecie, Validator.createPredicateValidator(
                texto -> {
                    if (texto == null || texto.toString().isEmpty()) {
                        return false;
                    }
                    try {
                        int numero = texto.toString().length();
                        return numero >= 3 && numero <= 20 && txtEspecie.getText().matches("^[\\p{L}0-9. ]{3,}$");
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                "La especie puede tener mínimo 3 caracteres y 20 de máximo y solo puede contener letras, números o puntos"
        ));
        ValidationSupport vDeno = new ValidationSupport();
        vDeno.registerValidator(txtDenominacion, Validator.createPredicateValidator(
                texto -> {
                    if (texto == null || texto.toString().isEmpty()) {
                        return false;
                    }
                    try {
                        int numero = texto.toString().length();
                        return numero >= 3 && numero <= 20 && txtDenominacion.getText().matches("^[\\p{L}0-9. ]{3,}$");
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                "La denominación puede tener mínimo 3 caracteres y 20 de máximo y solo puede contener letras, números o puntos"
        ));
        ValidationSupport vDes = new ValidationSupport();
        vDes.registerValidator(txtDescripcion, Validator.createPredicateValidator(
                texto -> {
                    if (texto == null || texto.toString().isEmpty()) {
                        return false;
                    } else {
                        return true;
                    }
                },
                "La descripción no puede estar vacía"
        ));
        ValidationSupport vTam = new ValidationSupport();
        vTam.registerValidator(txtTamaño, Validator.createPredicateValidator(
                texto -> {
                    if (texto == null || texto.toString().isEmpty()) {
                        return false;
                    }
                    try {
                        int numero = texto.toString().length();
                        return numero >= 3 && numero <= 20 && txtTamaño.getText().matches("^\\d{1,3}\\.\\d{1,2}$");
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                "El peso puede tener entre 1 y 3 dígitos enteros, un punto y 2 dígitos decimales"
        ));
        ValidationSupport vPes = new ValidationSupport();
        vPes.registerValidator(txtPeso, Validator.createPredicateValidator(
                texto -> {
                    if (texto == null || texto.toString().isEmpty()) {
                        return false;
                    }
                    try {
                        int numero = texto.toString().length();
                        return numero >= 3 && numero <= 20 && txtPeso.getText().matches("^\\d{1,3}\\.\\d{1,2}$");
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                "El peso puede tener entre 1 y 3 dígitos enteros, un punto y 2 dígitos decimales"
        ));
        ValidationSupport vHab = new ValidationSupport();
        vHab.registerValidator(txtNombreHabilidad1, Validator.createPredicateValidator(
                texto -> {
                    if (texto == null || texto.toString().isEmpty()) {
                        return false;
                    }
                    try {
                        int numero = texto.toString().length();
                        return numero >= 3 && numero <= 20 && txtNombreHabilidad1.getText().matches("^[\\p{L}0-9. ]{3,}$");
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                "El nombre de la habilidad puede tener mínimo 3 caracteres y 20 de máximo y solo puede contener letras, números o puntos y debe haber mínimo una"
        ));
        ValidationSupport vHabDes = new ValidationSupport();
        vHabDes.registerValidator(txtHabilidad1, Validator.createPredicateValidator(
                texto -> {
                    if (texto == null || texto.toString().isEmpty()) {
                        panelHabilidad1.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                        return false;
                    }
                    try {
                        int numero = texto.toString().length();
                        panelHabilidad1.setStyle("");
                        return numero >= 3 && numero <= 20 && txtHabilidad1.getText().matches("^[\\p{L}0-9. ]{3,}$");
                    } catch (NumberFormatException e) {
                        panelHabilidad1.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                        return false;
                    }
                },
                "La descripción de la habilidad puede tener mínimo 3 caracteres y 20 de máximo y solo puede contener letras, números o puntos y debe haber mínimo una"
        ));

        validadores = new ArrayList<>();
        validadores.addAll(Arrays.asList(vDeno, vDes, vEsp, vHab, vPes, vTam, vHab, vHabDes));

        Platform.runLater(() -> {
            for (ValidationSupport validationSupport : validadores) {
                validationSupport.initInitialDecoration();
            }
            Stage ventana = (Stage) this.txtAtk.getScene().getWindow();
            pokemon = (Pokemon) ventana.getUserData();
            enviaPokemon();

            ventana.setOnCloseRequest(evento -> {
                evento.consume();
                Stage ventanaConfirmar = new Stage();

                boolean todoOK = true;
                for (ValidationSupport validationSupport : validadores) {
                    todoOK = (todoOK && validationSupport.getValidationResult().getErrors().isEmpty());
                }
                if (todoOK) {
                    try {
                        datos = new ArrayList<>();
                        datos.add(tipoPaso);

                        datos.add(ventana);
                        cargarPokemon();
                        datos.add(pokemon);
                        ventanaConfirmar.setUserData(datos);
                        CargadorFXML.getInstance().cargar(VistasControladores.CONFIRMAR, ventanaConfirmar);
                        ventanaConfirmar.showAndWait();
                    } catch (Exception e) {
                        new Alertas(Alert.AlertType.WARNING, "Algo falló", "Incoherencia con las restricciones",
                                "Debe rellenar todos los campos y asegurarse de que siguen el formato que puede ver en el iconito de X pequeño").mostrarAlerta();
                    }
                } else {
                    new Alertas(Alert.AlertType.WARNING, "Algo falló", "Incoherencia con las restricciones",
                            "Debe rellenar todos los campos y asegurarse de que siguen el formato que puede ver en el iconito de X pequeño").mostrarAlerta();
                }
            });

        });
    }

    void enviaPokemon() {
        if (pokemon != null) {
            txtEspecie.setText(pokemon.getEspecie());
            txtDenominacion.setText(pokemon.getDenominacion());
            txtDescripcion.setText(pokemon.getDescripcion());
            txtTamaño.setText(pokemon.getTamaño() + "");
            txtPeso.setText(pokemon.getPeso() + "");
            cbTipo1.getSelectionModel().select(pokemon.getTipo1());
            cbTipo2.getSelectionModel().select(pokemon.getTipo2());
            utils.recuperarImagenBBDD(pokemon.getSprite(), imgPokemon);
            leerHabilidades(pokemon);
            leerStats(pokemon);
            tipoPaso = 2;
            id = pokemon.getnPokedex();
        } else {
            tipoPaso = 1;
        }
    }

    private void cargarPokemon() {
        pokemon = new Pokemon();
        pokemon.setnPokedex(id);
        pokemon.setEspecie(txtEspecie.getText());
        pokemon.setDenominacion(txtDenominacion.getText());
        pokemon.setDescripcion(txtDescripcion.getText());
        pokemon.setTamaño(Double.parseDouble(txtTamaño.getText()));
        pokemon.setPeso(Double.parseDouble(txtPeso.getText()));
        pokemon.setSprite(utils.codificarImagen(imgPokemon.getImage()));
        pokemon.setTipo1(cbTipo1.getValue());
        pokemon.setTipo2(cbTipo2.getValue());
        guardarStats();
        guardarHabilidades();
    }

    void leerHabilidades(Pokemon p) {
        Gson gson = new Gson();
        HabilidadesEnvoltorio listHabilidades = gson.fromJson(p.getHabilidades(), HabilidadesEnvoltorio.class);
        Habilidad habilidad;
        try {
            for (int i = 0; i < 3; i++) {
                if (!listHabilidades.vacia() && !listPaneles.isEmpty()) {
                    habilidad = listHabilidades.siguienteHabilidad();
                    listPaneles.get(i).setText(habilidad.getNombre());
                    listText.get(i).setText(habilidad.getDescripcion());
                }
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void guardarHabilidades() {
        List<Habilidad> habilidades = new ArrayList<>();
        for (int i = 0; i < listPaneles.size(); i++) {
            habilidades.add(new Habilidad(listPaneles.get(i).getText(), listText.get(i).getText()));
        }
        pokemon.setHabilidades(utils.escribirHabilidades(new HabilidadesEnvoltorio(habilidades)));
    }

    void leerStats(Pokemon p) {
        Gson gson = new Gson();
        EstadisticasEnvoltorio listStats = gson.fromJson(p.getEstadisticas(), EstadisticasEnvoltorio.class);

        try {
            for (int i = 0; i < 6; i++) {
                listSliders.get(i).setValue(listStats.getEstadistica(i).getValor());
                listLabels.get(i).setText(listStats.getEstadistica(i).getValor() + "/255");
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void guardarStats() {
        List<String> nombres = List.of("HP", "Atk", "Def", "SpA", "SpD", "SpE");
        List<Stat> stats = new ArrayList<>();
        for (int i = 0; i < listSliders.size(); i++) {
            stats.add(new Stat(nombres.get(i), (int) listSliders.get(i).getValue()));
        }

        EstadisticasEnvoltorio estadisticas = new EstadisticasEnvoltorio(stats);
        pokemon.setEstadisticas(utils.escribirStats(estadisticas));
    }

    private void inicializarSliders() {
        listSliders.clear();
        listLabels.clear();
        listSliders.addAll(List.of(sdHp, sdAtk, sdDef, sdSpA, sdSpD, sdSpe));
        listLabels.addAll(List.of(txtHp, txtAtk, txtDef, txtSpA, txtSpD, txtSpe));

        for (int i = 0; i < 6; i++) {
            Label tempLabel = listLabels.get(i);
            listSliders.get(i).valueProperty().addListener((observable, oldValue, newValue) -> {
                tempLabel.setText(String.valueOf(newValue.intValue() + "/255"));
            });
        }
    }
}
