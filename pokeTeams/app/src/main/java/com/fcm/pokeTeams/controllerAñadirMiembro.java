/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author DFran49
 */
import com.fcm.pokeTeams.DAO.EntrenadorDAO;
import com.fcm.pokeTeams.DAO.PokemonDAO;
import com.fcm.pokeTeams.modelos.EVsEnvoltorio;
import com.fcm.pokeTeams.enums.Generos;
import com.fcm.pokeTeams.modelos.HabilidadesEnvoltorio;
import com.fcm.pokeTeams.modelos.IVsEnvoltorio;
import com.fcm.pokeTeams.modelos.Miembro;
import com.fcm.pokeTeams.modelos.Movimiento;
import com.fcm.pokeTeams.modelos.MovimientoEnvoltorio;
import com.fcm.pokeTeams.enums.Naturalezas;
import com.fcm.pokeTeams.enums.VistasControladores;
import com.fcm.pokeTeams.modelos.EV;
import com.fcm.pokeTeams.modelos.Entrenador;
import com.fcm.pokeTeams.util.Alertas;
import com.fcm.pokeTeams.util.CargadorFXML;
import com.fcm.pokeTeams.util.Utilidades;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class controllerAñadirMiembro implements Initializable {

    private List<Slider> listBarrasIv = new ArrayList<>();
    private List<Label> listEtiquetasIv = new ArrayList<>();
    private List<Slider> listBarrasEv = new ArrayList<>();
    private List<Label> listEtiquetasEv = new ArrayList<>();
    private List<ValidationSupport> validadores;
    private ValidationSupport vsSliders = new ValidationSupport();
    Utilidades util = Utilidades.getInstance();
    private Miembro miembro;
    private Miembro nuevoMiembro = new Miembro();
    private ArrayList<Object> datos = new ArrayList<>();
    private int tipoPaso;

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
    void finalizar(ActionEvent event) {
        Stage ventana = (Stage) txtEVsAtk.getScene().getWindow();
        ventana.fireEvent(new WindowEvent(ventana, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarSliders();
        inicializarCBs();
        spNivel.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        util.crearTooltip("Imagen del pokemon", imgPokemon);

        ValidationSupport vMote = new ValidationSupport();
        vMote.registerValidator(txtMote, Validator.createPredicateValidator(
            texto -> {
                if (texto == null || texto.toString().isEmpty()) {
                return false;
                }
                try {
                    int numero = texto.toString().length();
                    return numero >= 3 && numero <= 20 && txtMote.getText().matches("^[A-Za-z0-9. ]{3,}$");
                } catch (NumberFormatException e) {
                    return false;
                }
            },
            "El nombre puede tener mínimo 3 caracteres y 20 de máximo y solo puede contener letras, números o puntos"
        ));
        ValidationSupport vObjeto = new ValidationSupport();
        vObjeto.registerValidator(txtObjeto, Validator.createPredicateValidator(
            texto -> {
                if (texto == null || texto.toString().isEmpty()) {
                return false;
                }
                try {
                    int numero = texto.toString().length();
                    return numero >= 3 && numero <= 20 && txtObjeto.getText().matches("^[A-Za-z0-9. ]{3,}$");
                } catch (NumberFormatException e) {
                    return false;
                }
            },
            "El nombre puede tener mínimo 3 caracteres y 20 de máximo y solo puede contener letras, números o puntos"
        ));
        ValidationSupport vMov1 = new ValidationSupport();
        vMov1.registerValidator(txtMovimiento1, Validator.createPredicateValidator(
            texto -> {
                if (texto == null || texto.toString().isEmpty()) {
                return false;
                }
                try {
                    int numero = texto.toString().length();
                    return numero >= 3 && numero <= 20 && txtMovimiento1.getText().matches("^[A-Za-z0-9. ]{3,}$");
                } catch (NumberFormatException e) {
                    return false;
                }
            },
            "El nombre puede tener mínimo 3 caracteres y 20 de máximo y solo puede contener letras, números o puntos"
        ));
        
        validarSliders();
        validadores.addAll(Arrays.asList(vMote, vMov1, vObjeto));

        Platform.runLater(() -> {
            for (ValidationSupport validationSupport : validadores) {
                validationSupport.initInitialDecoration();
            }
            Stage ventana = (Stage) this.txtMote.getScene().getWindow();
            miembro = (Miembro) ventana.getUserData();
            enviaMiembro();

            ventana.setOnCloseRequest(evento -> {
                evento.consume();
                Stage ventanaConfirmar = new Stage();
                
                boolean todoOK = true;
                for (ValidationSupport validationSupport : validadores) {
                    todoOK = (todoOK && validationSupport.getValidationResult().getErrors().isEmpty());
                }
                if (todoOK) {
                    try {
                        datos.add(tipoPaso);
                        datos.add(ventana);
                        cargarMiembro();
                        datos.add(nuevoMiembro);
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

    void enviaMiembro() {
        if (miembro.getEspecie() != null) {
            cbEspecie.getSelectionModel().select(miembro.getEspecie());
            spNivel.getValueFactory().setValue(miembro.getNivel());
            txtMote.setText(miembro.getMote());
            nuevoMiembro.setOldMote(miembro.getMote());
            cbHabilidad.setValue(miembro.getHabilidad());
            txtObjeto.setText(miembro.getObjeto());
            cbGenero.getSelectionModel().select(Generos.fromSigla(miembro.getGenero()).getPokemon());
            cbNaturaleza.getSelectionModel().select(miembro.getNaturaleza());
            util.recuperarImagenBBDD(miembro.getSprite(), imgPokemon);
            List<TextField> listText = List.of(txtMovimiento1, txtMovimiento2, txtMovimiento3, txtMovimiento4);
            util.leerMovimientos(miembro, listText);
            cargarEVs();
            cargarIVs();

            tipoPaso = 4;
        } else {
            tipoPaso = 3;
        }
    }

    void cargarIVs() {
        IVsEnvoltorio ivs = util.leerIVs(miembro);
        for (int i = 0; i < 6; i++) {
            listBarrasIv.get(i).setValue(ivs.getEV(i).getValor());
            listEtiquetasIv.get(i).setText(ivs.getEV(i).getValor() + "/31");
        }
    }

    void cargarEVs() {
        EVsEnvoltorio evs = util.leerEVs(miembro);
        for (int i = 0; i < 6; i++) {
            listBarrasEv.get(i).setValue(evs.getEV(i).getValor());
            listEtiquetasEv.get(i).setText(evs.getEV(i).getValor() + "/255");
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
                    "La suma total de los EVs debe ser igual o menor a 508 (Actual:" + sumaEVs() + ")"
            ));
        }

        validadores = new ArrayList<>();
        validadores.addAll(Arrays.asList(vsSliders));
    }

    private void inicializarSliders() {
        listBarrasIv.clear();
        listBarrasEv.clear();
        listBarrasIv.addAll(List.of(sdIVsHp, sdIVsAtk, sdIVsDef, sdIVsSpA, sdIVsSpD, sdIVsSpe));
        listBarrasEv.addAll(List.of(sdEVsHp, sdEVsAtk, sdEVsDef, sdEVsSpA, sdEVsSpD, sdEVsSpe));
        listEtiquetasIv.clear();
        listEtiquetasEv.clear();
        listEtiquetasIv.addAll(List.of(txtIVsHp, txtIVsAtk, txtIVsDef, txtIVsSpA, txtIVsSpD, txtIVsSpe));
        listEtiquetasEv.addAll(List.of(txtEVsHp, txtEVsAtk, txtEVsDef, txtEVsSpA, txtEVsSpD, txtEVsSpe));
        for (int i = 0; i < 6; i++) {
            Label tempLabelEv = listEtiquetasEv.get(i);
            Label tempLabelIv = listEtiquetasIv.get(i);
            listBarrasEv.get(i).valueProperty().addListener((observable, oldValue, newValue) -> {
                tempLabelEv.setText(String.valueOf(newValue.intValue() + "/255"));
                vsSliders.revalidate();
            });
            listBarrasIv.get(i).valueProperty().addListener((observable, oldValue, newValue) -> {
                tempLabelIv.setText(String.valueOf(newValue.intValue() + "/31"));
            });
        }
    }

    private void inicializarCBs() {
        cbGenero.getItems().addAll(Generos.M.getPokemon(), Generos.F.getPokemon(), Generos.N.getPokemon());
        cbNaturaleza.getItems().addAll(Naturalezas.getNaturalezas());
        cbNaturaleza.getSelectionModel().selectFirst();
        PokemonDAO.getInstance().getTodos("").forEach(elemento -> {
            cbEspecie.getItems().add(elemento.getEspecie());
        });
        cbEspecie.valueProperty().addListener((observable, valViejo, valNuevo) -> {
            PokemonDAO.getInstance().getTodos("WHERE Especie = '" + valNuevo + "'").forEach(elemento -> {
                HabilidadesEnvoltorio listaHabilidades = util.leerHabilidades(elemento.getHabilidades());
                cbHabilidad.getItems().clear();
                listaHabilidades.getHabilidades().forEach(action -> {
                    cbHabilidad.getItems().add(action.getNombre());
                });
                cbHabilidad.getSelectionModel().selectFirst();
                util.recuperarImagenBBDD(elemento.getSprite(), imgPokemon);
            });
        });
    }

    private void cargarMiembro() {
        List<String> nombres = List.of("HP", "Atk", "Def", "SpA", "SpD", "SpE");
        nuevoMiembro.setEquipo(miembro.getEquipo());
        nuevoMiembro.setEspecie(cbEspecie.getSelectionModel().getSelectedItem());
        nuevoMiembro.setEvs(leerEVs(nombres));
        nuevoMiembro.setIvs(leerIVs(nombres));
        nuevoMiembro.setGenero(Generos.fromPokemon(cbGenero.getValue()));
        nuevoMiembro.setHabilidad(cbHabilidad.getSelectionModel().getSelectedItem());
        nuevoMiembro.setMote(txtMote.getText());
        nuevoMiembro.setMovimientos(leerMovimientos());
        nuevoMiembro.setNaturaleza(cbNaturaleza.getSelectionModel().getSelectedItem());
        nuevoMiembro.setNivel(spNivel.getValue());
        nuevoMiembro.setObjeto(txtObjeto.getText());
        nuevoMiembro.setnPokedex(PokemonDAO.getInstance().getTodos("WHERE Especie = '" + nuevoMiembro.getEspecie() + "'").get(0).getnPokedex());
    }

    private String leerMovimientos() {
        List<Movimiento> movimientos = new ArrayList<>();
        List<TextField> txtMovimientos = List.of(txtMovimiento1, txtMovimiento2, txtMovimiento3, txtMovimiento4);
        for (TextField txt : txtMovimientos) {
            String texto = txt.getText();
            if (!texto.isEmpty()) {
                movimientos.add(new Movimiento(texto));
            }
        }

        MovimientoEnvoltorio envoltorio = new MovimientoEnvoltorio(movimientos);
        return Utilidades.getInstance().escribirMovimientos(envoltorio);
    }

    private String leerEVs(List<String> nombres) {
        List<EV> stats = new ArrayList<>();
        for (int i = 0; i < listBarrasEv.size(); i++) {
            stats.add(new EV(nombres.get(i), (int) listBarrasEv.get(i).getValue()));
        }
        EVsEnvoltorio estadisticas = new EVsEnvoltorio(stats);
        return Utilidades.getInstance().escribirEVs(estadisticas);
    }

    private String leerIVs(List<String> nombres) {
        List<EV> stats = new ArrayList<>();
        for (int i = 0; i < listBarrasIv.size(); i++) {
            stats.add(new EV(nombres.get(i), (int) listBarrasIv.get(i).getValue()));
        }
        IVsEnvoltorio estadisticas = new IVsEnvoltorio(stats);
        return Utilidades.getInstance().escribirIVs(estadisticas);
    }

    private int sumaEVs() {
        return listBarrasEv.stream().mapToInt(slider -> (int) slider.getValue()).sum();
    }
}
