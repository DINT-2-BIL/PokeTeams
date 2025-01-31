/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author Francisco
 */
import com.fcm.pokeTeams.modelos.EVsEnvoltorio;
import com.fcm.pokeTeams.modelos.EstadisticasEnvoltorio;
import com.fcm.pokeTeams.enums.Generos;
import com.fcm.pokeTeams.modelos.IVsEnvoltorio;
import com.fcm.pokeTeams.modelos.Miembro;
import com.fcm.pokeTeams.enums.Naturalezas;
import com.fcm.pokeTeams.util.Utilidades;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class controllerMiembro implements Initializable {

    private Miembro miembro;
    private EVsEnvoltorio evs;
    private IVsEnvoltorio ivs;
    private EstadisticasEnvoltorio estadisticas;
    private ArrayList<String> etiquetasStats = new ArrayList<>(List.of("Hp", "Atk", "Def", "SpA", "SpD", "Spe"));
    Utilidades util = Utilidades.getInstance();

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
    private Button btnCerrar;

    @FXML
    private ImageView imgPokemon;

    @FXML
    private Label txtAtk;

    @FXML
    private Label txtDef;

    @FXML
    private TextField txtEspecie;

    @FXML
    private TextField txtGenero;

    @FXML
    private TextField txtHabilidad;

    @FXML
    private Label txtHp;

    @FXML
    private TextField txtMote;

    @FXML
    private TextField txtMovimiento1;

    @FXML
    private TextField txtMovimiento2;

    @FXML
    private TextField txtMovimiento3;

    @FXML
    private TextField txtMovimiento4;

    @FXML
    private TextField txtNaturaleza;

    @FXML
    private TextField txtNivel;

    @FXML
    private TextField txtObjeto;

    @FXML
    private Label txtSpA;

    @FXML
    private Label txtSpD;

    @FXML
    private Label txtSpe;

    @FXML
    private TextField txtTotalEVs;

    @FXML
    private TextField txtTotalIVs;

    @FXML
    void cerrar(ActionEvent event) {
        ((Stage) txtEspecie.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            miembro = (Miembro) this.txtAtk.getScene().getWindow().getUserData();
            enviaMiembro();
        });
    }

    void enviaMiembro() {
        txtNivel.setText(String.valueOf(miembro.getNivel()));
        txtMote.setText(miembro.getMote());
        txtEspecie.setText(miembro.getEspecie());
        txtHabilidad.setText(miembro.getHabilidad());
        txtObjeto.setText(miembro.getObjeto());
        txtNaturaleza.setText(miembro.getNaturaleza());

        txtGenero.setText(Generos.fromSigla(miembro.getGenero()).getPokemon());
        util.recuperarImagenBBDD(miembro.getSprite(), imgPokemon);
        List<TextField> listText = new ArrayList<>();
        listText.add(txtMovimiento1);
        listText.add(txtMovimiento2);
        listText.add(txtMovimiento3);
        listText.add(txtMovimiento4);
        util.leerMovimientos(miembro, listText);
        util.crearTooltip("Imagen de " + miembro.getEspecie(), imgPokemon);
        asignarStats();
        sumaIEvs();
    }

    void sumaIEvs() {
        int suma = 0;
        for (int i = 0; i < 6; i++) {
            suma += evs.getEV(i).getValor();
        }
        txtTotalEVs.setText(String.valueOf(suma));
        suma = 0;
        for (int i = 0; i < 6; i++) {
            suma += ivs.getEV(i).getValor();
        }
        txtTotalIVs.setText(String.valueOf(suma));
    }

    void asignarStats() {
        calcularStats();
        List<ProgressBar> listBarras = List.of(barHp, barAtk, barDef, barSpA, BarSpD, barSpe);
        List<Label> listEtiquetas = List.of(txtHp, txtAtk, txtDef, txtSpA, txtSpD, txtSpe);
        double progreso;
        List<Integer> stats = calcularStats();

        try {
            for (int i = 0; i < 6; i++) {
                if (stats.get(i) <= 500) {
                    progreso = (stats.get(i) * 100) / 500;
                } else {
                    if (i == 0) {
                        progreso = (stats.get(i) * 100) / 714;
                    } else {
                        progreso = (stats.get(i) * 100) / 614;
                    }
                }

                progreso = progreso / 100;
                listBarras.get(i).setProgress(progreso);
                if (progreso < 0.10) {
                    listBarras.get(i).setStyle("-fx-accent: #ff0000;");
                } else if (progreso < 0.30) {
                    listBarras.get(i).setStyle("-fx-accent: #ff8000;");
                } else if (progreso < 0.60) {
                    listBarras.get(i).setStyle("-fx-accent: #e5be01;");
                } else {
                    listBarras.get(i).setStyle("-fx-accent: #00913f;");
                }
                listEtiquetas.get(i).setText(String.valueOf(stats.get(i)));
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    List<Integer> calcularStats() {
        leerEVs(miembro);
        leerStats(miembro);
        leerIVs(miembro);
        List<Integer> stats = new ArrayList<>();
        double calculo = 0;

        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                calculo = Math.floor(((((2 * estadisticas.getEstadistica(i).getValor()) + ivs.getEV(i).getValor() + (evs.getEV(i).getValor() / 4))
                        * miembro.getNivel()) / 100) + miembro.getNivel() + 10);
            } else {
                calculo = Math.floor((((((2 * estadisticas.getEstadistica(i).getValor()) + ivs.getEV(i).getValor() + (evs.getEV(i).getValor() / 4))
                        * miembro.getNivel()) / 100) + 5)
                        * Naturalezas.fromName(miembro.getNaturaleza()).getValores().get(etiquetasStats.get(i)));
            }
            stats.add((int) calculo);
        }
        return stats;
    }

    void leerEVs(Miembro m) {
        evs = util.leerEVs(miembro);
    }

    void leerIVs(Miembro m) {
        ivs = util.leerIVs(m);
    }

    void leerStats(Miembro p) {
        Gson gson = new Gson();
        estadisticas = gson.fromJson(p.getStats(), EstadisticasEnvoltorio.class);
    }
}
