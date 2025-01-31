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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;

public class controllerPokedex implements Initializable {

    private Pokemon pokemon;
    private Utilidades util = Utilidades.getInstance();

    @FXML
    private ProgressBar BarSpD;

    @FXML
    private ProgressBar barAtk;

    @FXML
    private ProgressBar barDef;

    @FXML
    private ProgressBar barHp;

    @FXML
    private ProgressBar barSpA;

    @FXML
    private ProgressBar barSpe;

    @FXML
    private ImageView imgPokemon;

    @FXML
    private TitledPane tpaneHabilidad1;

    @FXML
    private TitledPane tpaneHabilidad2;

    @FXML
    private TitledPane tpaneHabilidad3;

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
    private TextArea txtTipo1;

    @FXML
    private TextArea txtTipo2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            pokemon = (Pokemon) this.BarSpD.getScene().getWindow().getUserData();
            txtEspecie.setText(pokemon.getEspecie());
            txtDenominacion.setText(pokemon.getDenominacion());
            txtDescripcion.setText(pokemon.getDescripcion());
            txtTamaño.setText(pokemon.getTamaño() + " metros");
            txtPeso.setText(pokemon.getPeso() + " kilogramos");
            txtTipo1.setText(pokemon.getTipo1());
            txtTipo2.setText(pokemon.getTipo2());
            util.recuperarImagenBBDD(pokemon.getSprite(), imgPokemon);
            util.crearTooltip("Imagen " + pokemon.getEspecie(), imgPokemon);
            leerHabilidades(pokemon);
            leerStats(pokemon);
        });
    }

    void leerHabilidades(Pokemon p) {
        Gson gson = new Gson();
        HabilidadesEnvoltorio listHabilidades = gson.fromJson(p.getHabilidades(), HabilidadesEnvoltorio.class);
        List<TitledPane> listPaneles = new ArrayList<>();
        listPaneles.add(tpaneHabilidad1);
        listPaneles.add(tpaneHabilidad2);
        listPaneles.add(tpaneHabilidad3);
        List<TextArea> listText = new ArrayList<>();
        listText.add(txtHabilidad1);
        listText.add(txtHabilidad2);
        listText.add(txtHabilidad3);
        Habilidad habilidad = null;
        try {
            for (int i = 0; i < 3; i++) {
                if (listHabilidades.vacia()) {
                    
                    listPaneles.get(i).setVisible(false);
                    
                    for (TextArea text : listText) {
                        text.setVisible(false);
                    }
                } else {
                    habilidad = listHabilidades.siguienteHabilidad();
                    listPaneles.get(i).setText(habilidad.getNombre());
                    util.crearTooltip("Desplegable " + habilidad.getNombre(), listPaneles.get(0));
                    listText.get(i).setText(habilidad.getDescripcion());
                    util.crearTooltip("Descripción " + habilidad.getNombre(), listPaneles.get(0));
                }
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    void leerStats(Pokemon p) {
        Gson gson = new Gson();
        EstadisticasEnvoltorio listStats = gson.fromJson(p.getEstadisticas(), EstadisticasEnvoltorio.class);
        List<ProgressBar> listBarras = new ArrayList<>();
        listBarras.add(barHp);
        listBarras.add(barAtk);
        listBarras.add(barDef);
        listBarras.add(barSpA);
        listBarras.add(BarSpD);
        listBarras.add(barSpe);
        List<Label> listEtiquetas = new ArrayList<>();
        listEtiquetas.add(txtHp);
        listEtiquetas.add(txtAtk);
        listEtiquetas.add(txtDef);
        listEtiquetas.add(txtSpA);
        listEtiquetas.add(txtSpD);
        listEtiquetas.add(txtSpe);
        double progreso;

        try {
            for (int i = 0; i < 6; i++) {

                progreso = (listStats.getEstadistica(i).getValor() * 100) / 255;
                progreso = progreso / 100;
                listBarras.get(i).setProgress(progreso);
                if (progreso < 0.25) {
                    listBarras.get(i).setStyle("-fx-accent: #ff0000;");
                } else if (progreso < 0.50) {
                    listBarras.get(i).setStyle("-fx-accent: #ff8000;");
                } else if (progreso < 0.75) {
                    listBarras.get(i).setStyle("-fx-accent: #e5be01;");
                } else {
                    listBarras.get(i).setStyle("-fx-accent: #00913f;");
                }
                listEtiquetas.get(i).setText("" + listStats.getEstadistica(i).getValor());
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
