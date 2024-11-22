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
import com.fcm.pokeTeams.util.Utilidades;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class controllerAñadirPokemon implements Initializable{
    Utilidades utils = new Utilidades();
    Pokemon poke;
    
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
    void actualizarStats(MouseEvent event) {

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
        cbTipo1.getItems().addAll("Acero","Agua","Bicho","Dragón","Eléctrico","Fantasma",
                "Fuego","Hada","Hielo","Lucha","Normal","Planta","Psíquico",
                "Roca","Siniestro","Tierra","Veneno","Volador");
        cbTipo2.getItems().addAll("Ninguno","Acero","Agua","Bicho","Dragón","Eléctrico","Fantasma",
                "Fuego","Hada","Hielo","Lucha","Normal","Planta","Psíquico",
                "Roca","Siniestro","Tierra","Veneno","Volador");
        utils.crearTooltip("Seleccionar imagen", imgPokemon);
        cbTipo1.setStyle("-fx-font-size: 24px;");
        cbTipo2.setStyle("-fx-font-size: 24px;");
        List<Tooltip> tooltips = new ArrayList<>();
        tooltips.add(sdHp.getTooltip());
        tooltips.add(sdAtk.getTooltip());
        tooltips.add(sdDef.getTooltip());
        tooltips.add(sdSpA.getTooltip());
        tooltips.add(sdSpD.getTooltip());
        tooltips.add(sdSpe.getTooltip());
        
        for (Tooltip t : tooltips) {
            t.setStyle("-fx-font-size: 24px;");
        }
    }

    void enviaPokemon(Pokemon p) {
        poke = p;
        txtEspecie.setText(p.getEspecie());
        txtDenominacion.setText(p.getDenominacion());
        txtDescripcion.setText(p.getDescripcion());
        txtTamaño.setText(p.getTamaño() + " metros");
        txtPeso.setText(p.getPeso() + " kilogramos");
        cbTipo1.getSelectionModel().select(p.getTipo1());
        cbTipo2.getSelectionModel().select(p.getTipo2());
        utils.recuperarImagenBBDD(p.getSprite(), imgPokemon);
        leerHabilidades(p);
        leerStats(p);
    }
    
    void leerHabilidades(Pokemon p) {
        Gson gson = new Gson();
        HabilidadesEnvoltorio listHabilidades = gson.fromJson(p.getHabilidades(), HabilidadesEnvoltorio.class);
        List<TextField> listPaneles = new ArrayList<>();
        listPaneles.add(txtNombreHabilidad1);
        listPaneles.add(txtNombreHabilidad2);
        listPaneles.add(txtNombreHabilidad3);
        List<TextArea> listText = new ArrayList<>();
        listText.add(txtHabilidad1);
        listText.add(txtHabilidad2);
        listText.add(txtHabilidad3);
        Habilidad habilidad = null;
        try {
            for (int i = 0; i < 3; i++) {
                if (!listHabilidades.vacia() && !listPaneles.isEmpty()) {
                    habilidad = listHabilidades.siguienteHabilidad();
                    listPaneles.get(0).setText(habilidad.getNombre());
                    listPaneles.remove(0);
                    listText.get(0).setText(habilidad.getDescripcion());
                    listText.remove(0);
                }
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    void leerStats(Pokemon p) {
        Gson gson = new Gson();
        EstadisticasEnvoltorio listStats = gson.fromJson(p.getEstadisticas(), EstadisticasEnvoltorio.class);
        List<Slider> listBarras = new ArrayList<>();
        listBarras.add(sdHp);
        listBarras.add(sdAtk);
        listBarras.add(sdDef);
        listBarras.add(sdSpA);
        listBarras.add(sdSpD);
        listBarras.add(sdSpe);
        List<Label> listEtiquetas = new ArrayList<>();
        listEtiquetas.add(txtHp);
        listEtiquetas.add(txtAtk);
        listEtiquetas.add(txtDef);
        listEtiquetas.add(txtSpA);
        listEtiquetas.add(txtSpD);
        listEtiquetas.add(txtSpe);
        
        try {
            for (int i = 0; i < 6; i++) {
                listBarras.get(i).setValue(listStats.getEstadistica(i).getValor());
                listEtiquetas.get(i).setText(listStats.getEstadistica(i).getValor()+"/255");
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    
}
