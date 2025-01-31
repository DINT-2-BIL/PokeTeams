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
import com.fcm.pokeTeams.util.CargadorFXML;
import com.fcm.pokeTeams.util.Utilidades;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class controllerAñadirPokemon implements Initializable {

    private List<Slider> listSliders = new ArrayList<>();
    private List<Label> listLabels = new ArrayList<>();
    private List<TextField> listPaneles;
    private List<TextArea> listText;

    private ArrayList<Object> datos;
    Utilidades utils = Utilidades.getInstance();
    Pokemon pokemon;
    private int tipoPaso;

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
    private TextArea txtDenominacion;

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
    private TextArea txtPeso;

    @FXML
    private Label txtSpA;

    @FXML
    private Label txtSpD;

    @FXML
    private Label txtSpe;

    @FXML
    private TextArea txtTamaño;

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

        Platform.runLater(() -> {
            Stage ventana = (Stage) this.txtAtk.getScene().getWindow();
            pokemon = (Pokemon) ventana.getUserData();
            enviaPokemon();

            ventana.setOnCloseRequest(evento -> {
                evento.consume();
                Stage ventanaConfirmar = new Stage();
                CargadorFXML.getInstance().cargar(VistasControladores.CONFIRMAR, ventanaConfirmar);
                datos.add(tipoPaso);
                datos.add(ventana);
                cargarPokemon();
                datos.add(pokemon);
                ventanaConfirmar.setUserData(datos);
                ventanaConfirmar.showAndWait();

            });
        });
    }

    void enviaPokemon() {
        if (pokemon != null) {
            txtEspecie.setText(pokemon.getEspecie());
            txtDenominacion.setText(pokemon.getDenominacion());
            txtDescripcion.setText(pokemon.getDescripcion());
            txtTamaño.setText(pokemon.getTamaño() + " metros");
            txtPeso.setText(pokemon.getPeso() + " kilogramos");
            cbTipo1.getSelectionModel().select(pokemon.getTipo1());
            cbTipo2.getSelectionModel().select(pokemon.getTipo2());
            utils.recuperarImagenBBDD(pokemon.getSprite(), imgPokemon);
            leerHabilidades(pokemon);
            leerStats(pokemon);
            tipoPaso = 2;
        } else {
            tipoPaso = 1;
        }
    }

    private void cargarPokemon() {
        pokemon.setEspecie(txtEspecie.getText());
        pokemon.setDenominacion(txtDenominacion.getText());
        pokemon.setDescripcion(txtDescripcion.getText());
        pokemon.setTamaño(Double.parseDouble(txtTamaño.getText().replace(" metros", "")));
        pokemon.setPeso(Double.parseDouble(txtPeso.getText().replace(" kilogramos", "")));
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
