/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author DFran49
 */
import com.fcm.pokeTeams.modelos.EVsEnvoltorio;
import com.fcm.pokeTeams.modelos.Equipo;
import com.fcm.pokeTeams.modelos.EstadisticasEnvoltorio;
import com.fcm.pokeTeams.modelos.Generos;
import com.fcm.pokeTeams.modelos.HabilidadesEnvoltorio;
import com.fcm.pokeTeams.modelos.IVsEnvoltorio;
import com.fcm.pokeTeams.modelos.Miembro;
import com.fcm.pokeTeams.modelos.Movimiento;
import com.fcm.pokeTeams.modelos.MovimientoEnvoltorio;
import com.fcm.pokeTeams.modelos.Naturalezas;
import com.fcm.pokeTeams.modelos.Pokemon;
import com.fcm.pokeTeams.util.Conexion;
import com.fcm.pokeTeams.util.Utilidades;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.File;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class controllerAñadirMiembro implements Initializable {
    private controllerTarjetaMiembro ctm;
    private controllerTarjetaAñadirMiembro ctam;
    private controllerConfirmar cc;
    private List<Slider> listBarrasIv = new ArrayList<>();
    private List<Label> listEtiquetasIv = new ArrayList<>();
    private List<Slider> listBarrasEv = new ArrayList<>();
    private List<Label> listEtiquetasEv = new ArrayList<>();
    private List<ValidationSupport> validadores;
    private ValidationSupport vsSliders = new ValidationSupport();
    Utilidades util = new Utilidades();
    private Conexion conexion = null;
    private Miembro miembro;
    
    @FXML
    private Button btnFinalizar;

    @FXML
    private ComboBox<String> cbEspecie;

    @FXML
    private ComboBox<String> cbGenero;

    @FXML
    private ComboBox<String> cbHabilidad;

    @FXML
    private ComboBox<String> cbNaturaleza;

    @FXML
    private ImageView imgPokemon;

    @FXML
    private Slider sdEVsAtk;

    @FXML
    private Slider sdEVsDef;

    @FXML
    private Slider sdEVsHp;

    @FXML
    private Slider sdEVsSpA;

    @FXML
    private Slider sdEVsSpD;

    @FXML
    private Slider sdEVsSpe;

    @FXML
    private Slider sdIVsAtk;

    @FXML
    private Slider sdIVsDef;

    @FXML
    private Slider sdIVsHp;

    @FXML
    private Slider sdIVsSpA;

    @FXML
    private Slider sdIVsSpD;

    @FXML
    private Slider sdIVsSpe;

    @FXML
    private Spinner<Integer> spNivel;

    @FXML
    private Label txtEVsAtk;

    @FXML
    private Label txtEVsDef;

    @FXML
    private Label txtEVsHp;

    @FXML
    private Label txtEVsSpA;

    @FXML
    private Label txtEVsSpD;

    @FXML
    private Label txtEVsSpe;

    @FXML
    private Label txtIVsAtk;

    @FXML
    private Label txtIVsDef;

    @FXML
    private Label txtIVsHp;

    @FXML
    private Label txtIVsSpA;

    @FXML
    private Label txtIVsSpD;

    @FXML
    private Label txtIVsSpe;

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
    private TextField txtObjeto;

    @FXML
    void actualizarEVs(MouseEvent event) {

    }

    @FXML
    void actualizarIVs(MouseEvent event) {

    }
    
    @FXML
    void finalizar(ActionEvent event) {
        Stage ventana = (Stage) txtEVsAtk.getScene().getWindow();
        ventana.fireEvent(new WindowEvent(ventana, WindowEvent.WINDOW_CLOSE_REQUEST));
        if (ctm != null) {
           ctm.refrescar(); 
        } else if (ctam != null) {
            ctam.refrescar();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarSliders();
        cbGenero.getItems().addAll(Generos.M.getPokemon(),Generos.F.getPokemon(),Generos.N.getPokemon());
        spNivel.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        spNivel.setStyle("-fx-font-size: 24px;");
        cbEspecie.setStyle("-fx-font-size: 24px;");
        cbGenero.setStyle("-fx-font-size: 24px;");
        cbHabilidad.setStyle("-fx-font-size: 24px;");
        cbNaturaleza.getItems().addAll(Naturalezas.getNaturalezas());
        util.crearTooltip("Imagen del pokemon", imgPokemon);
        
        try {
            String query = "SELECT Especie FROM pokemon";

            Statement statement = conexion.getConexion().createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                cbEspecie.getItems().add(result.getString("Especie"));
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la BD: " + e.getMessage());
        }
        
        
        cbEspecie.valueProperty().addListener((observable, valViejo, valNuevo) -> {
            String especie = valNuevo;
            try {
                String query = "SELECT * FROM pokemon WHERE Especie = '" + especie + "'";
                Statement statement = conexion.getConexion().createStatement();
                ResultSet result = statement.executeQuery(query);
                result.next();
                HabilidadesEnvoltorio listaHabilidades = util.leerHabilidades(result.getString("Habilidades"));
                cbHabilidad.getItems().clear();
                listaHabilidades.getHabilidades().forEach(action -> {
                    cbHabilidad.getItems().add(action.getNombre());
                });
                util.recuperarImagenBBDD(result.getString("Sprite"), imgPokemon);
            } catch (SQLException e) {
                System.out.println("Error al conectar con la BD: " + e.getMessage());
            }
            
        });
        validarSliders();
    }

    void setControladorEnlace(controllerTarjetaMiembro c) {
        ctm = c;
    }
    void setControladorEnlaceAñadir(controllerTarjetaAñadirMiembro c) {
        ctam = c;
    }
    
    void asignarCerrado(Conexion c, Equipo e, int i) {
        conexion = c;
        ((Stage) txtMote.getScene().getWindow()).setOnCloseRequest(evento -> {
            evento.consume();
            Parent raiz = null;
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("/fxml/popUp_confirmar_cambios.fxml"));
            try {
                raiz = cargador.load();
            } catch (IOException ex) {
                Logger.getLogger(controllerTarjetaPokemon.class.getName()).log(Level.SEVERE, null, ex);
            }
            cc = cargador.getController();

            Stage confirmar = new Stage();
            confirmar.setUserData(i);
            Scene scene = new Scene(raiz);
            scene.setUserData(e);
            confirmar.setScene(scene);
            confirmar.setTitle("Confirmar");
            cc.enviaStage((Stage) txtEVsAtk.getScene().getWindow(), conexion, "a");
            cc.enviarEditarMiembro(miembro);
            confirmar.getIcons().add(new Image("/img/Victini.png"));
            confirmar.showAndWait();
        });
        txtMote.setText("");
    }
    
    void enviaMiembro(Miembro m) {
        miembro = m;
        cbEspecie.getSelectionModel().select(m.getEspecie());
        spNivel.getValueFactory().setValue(m.getNivel());
        txtMote.setText(m.getMote());
            try {
                String query = "SELECT * FROM pokemon WHERE Especie = '" + m.getEspecie() + "'";
                Statement statement = conexion.getConexion().createStatement();
                ResultSet result = statement.executeQuery(query);
                result.next();
                HabilidadesEnvoltorio listaHabilidades = util.leerHabilidades(result.getString("Habilidades"));
                cbHabilidad.getItems().clear();
                listaHabilidades.getHabilidades().forEach(action -> {
                    cbHabilidad.getItems().add(action.getNombre());
                });
                util.recuperarImagenBBDD(result.getString("Sprite"), imgPokemon);
            } catch (SQLException e) {
                System.out.println("Error al conectar con la BD: " + e.getMessage());
            }
        cbHabilidad.setValue(m.getHabilidad());
        txtObjeto.setText(m.getObjeto());
        cbGenero.getSelectionModel().select(Generos.fromSigla(m.getGenero()).getPokemon());
        cbNaturaleza.getSelectionModel().select(m.getNaturaleza());
        
        util.recuperarImagenBBDD(m.getSprite(), imgPokemon);
        List<TextField> listText = new ArrayList<>();
        listText.add(txtMovimiento1);
        listText.add(txtMovimiento2);
        listText.add(txtMovimiento3);
        listText.add(txtMovimiento4);
        util.leerMovimientos(m, listText);
        inicializarSliders();
        cargarEVs();
        cargarIVs();
    }
    
    void cargarIVs() {
        IVsEnvoltorio ivs = util.leerIVs(miembro);
        for (int i = 0; i < 6; i++) {
            listBarrasIv.get(i).setValue(ivs.getEV(i).getValor());
            listEtiquetasIv.get(i).setText(ivs.getEV(i).getValor()+"/31");
        }
    }
    
    void cargarEVs() {
        EVsEnvoltorio evs = util.leerEVs(miembro);
        for (int i = 0; i < 6; i++) {
            listBarrasEv.get(i).setValue(evs.getEV(i).getValor());
            listEtiquetasEv.get(i).setText(evs.getEV(i).getValor()+"/255");
        }
    }
    
    private void validarSliders() {
        for (Slider sd : listBarrasEv) {
            vsSliders.registerValidator(sd, Validator.createPredicateValidator(
                valor -> {
                    if (sumaEVs() > 508) {
                    return false;
                    }
                    return true;
                },
                "La suma total de los EVs debe ser igual o menor a 508 (Actual:"+sumaEVs()+")"
            ));
        }
        
        validadores = new ArrayList<>();
        validadores.addAll(Arrays.asList(vsSliders));

        Platform.runLater(() -> {
            for (ValidationSupport validationSupport : validadores) {
                validationSupport.initInitialDecoration();
            }
        });
    }
    
    private void inicializarSliders() {
        listBarrasIv.clear();
        listBarrasEv.clear();
        listBarrasIv.addAll(List.of(sdIVsHp, sdIVsAtk, sdIVsDef, sdIVsSpA, sdIVsSpD, sdIVsSpe));
        listBarrasEv.addAll(List.of(sdEVsHp,sdEVsAtk,sdEVsDef,sdEVsSpA,sdEVsSpD,sdEVsSpe));
        listEtiquetasIv.clear();
        listEtiquetasEv.clear();
        listEtiquetasIv.addAll(List.of(txtIVsHp, txtIVsAtk, txtIVsDef, txtIVsSpA, txtIVsSpD, txtIVsSpe));
        listEtiquetasEv.addAll(List.of(txtEVsHp, txtEVsAtk, txtEVsDef, txtEVsSpA, txtEVsSpD, txtEVsSpe));
        for (int i = 0; i <6; i++) {
            Label tempLabelEv = listEtiquetasEv.get(i);
            Label tempLabelIv = listEtiquetasIv.get(i);
            listBarrasEv.get(i).valueProperty().addListener((observable, oldValue, newValue) -> {
                tempLabelEv.setText(String.valueOf(newValue.intValue() + "/255"));
                sumaEVs();
                vsSliders.revalidate();
            });
            listBarrasIv.get(i).valueProperty().addListener((observable, oldValue, newValue) -> {
                tempLabelIv.setText(String.valueOf(newValue.intValue() + "/31"));
            });
        }
    }
    
    private int sumaEVs() {
        return listBarrasEv.stream().mapToInt(slider -> (int) slider.getValue()).sum();
    }
}

