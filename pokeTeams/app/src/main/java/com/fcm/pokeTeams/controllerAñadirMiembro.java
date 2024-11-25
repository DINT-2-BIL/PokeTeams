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
import com.fcm.pokeTeams.modelos.HabilidadesEnvoltorio;
import com.fcm.pokeTeams.modelos.IVsEnvoltorio;
import com.fcm.pokeTeams.modelos.Miembro;
import com.fcm.pokeTeams.modelos.Movimiento;
import com.fcm.pokeTeams.modelos.MovimientoEnvoltorio;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class controllerAñadirMiembro implements Initializable {
    private controllerTarjetaMiembro ctm;
    private controllerConfirmar cc;
    Utilidades util = new Utilidades();
    private Conexion conexion = null;
    private Miembro miembro;

    @FXML
    private ComboBox<String> cbEspecie;

    @FXML
    private ComboBox<Character> cbGenero;

    @FXML
    private ComboBox<String> cbHabilidad;

    @FXML
    private ComboBox<String> cbTipo1;

    @FXML
    private ComboBox<String> cbTipo2;

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
    private TextField txtEspecie;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbTipo1.getItems().addAll("Acero","Agua","Bicho","Dragón","Eléctrico","Fantasma",
                "Fuego","Hada","Hielo","Lucha","Normal","Planta","Psíquico",
                "Roca","Siniestro","Tierra","Veneno","Volador");
        cbTipo2.getItems().addAll("Ninguno","Acero","Agua","Bicho","Dragón","Eléctrico","Fantasma",
                "Fuego","Hada","Hielo","Lucha","Normal","Planta","Psíquico",
                "Roca","Siniestro","Tierra","Veneno","Volador");
        cbGenero.getItems().addAll('M','F','N');
        spNivel.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        spNivel.setStyle("-fx-font-size: 24px;");
        cbEspecie.setStyle("-fx-font-size: 24px;");
        cbGenero.setStyle("-fx-font-size: 24px;");
        cbHabilidad.setStyle("-fx-font-size: 24px;");
        cbTipo1.setStyle("-fx-font-size: 24px;");
        cbTipo2.setStyle("-fx-font-size: 24px;");
        List<Slider> barras = new ArrayList<>();
        this.sdEVsAtk.getParent().getParent().getChildrenUnmodifiable().forEach(elemento -> {
            if (elemento instanceof HBox) {
                ((HBox) elemento).getChildrenUnmodifiable().forEach(campo -> {
                    if (campo instanceof Slider) {
                        ((Slider) campo).getTooltip().setStyle("-fx-font-size: 24px;");
                    }
                });
            }
        });
        this.sdIVsAtk.getParent().getParent().getChildrenUnmodifiable().forEach(elemento -> {
            if (elemento instanceof HBox) {
                ((HBox) elemento).getChildrenUnmodifiable().forEach(campo -> {
                    if (campo instanceof Slider) {
                        ((Slider) campo).getTooltip().setStyle("-fx-font-size: 24px;");
                    }
                });
            }
        });
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
            txtEspecie.setText(especie);
            try {
                String query = "SELECT * FROM pokemon WHERE Especie = '" + especie + "'";
                Statement statement = conexion.getConexion().createStatement();
                ResultSet result = statement.executeQuery(query);
                result.next();
                HabilidadesEnvoltorio listaHabilidades = util.leerHabilidades(result.getString("Habilidades"));
                cbTipo1.setValue(result.getString("Tipo_1"));
                cbTipo2.setValue(result.getString("Tipo_2"));
            } catch (SQLException e) {
                System.out.println("Error al conectar con la BD: " + e.getMessage());
            }
            
        });
    }

    void setControladorEnlace(controllerTarjetaMiembro c) {
        ctm = c;
    }
    
    void asignarCerrado(Conexion c) {
        conexion = c;
        txtMote.setText("a");
        ((Stage) txtMote.getScene().getWindow()).setOnCloseRequest(evento -> {
            evento.consume();
            Parent raiz = null;
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("fxml/popUp_confirmar_cambios.fxml"));
            try {
                raiz = cargador.load();
            } catch (IOException ex) {
                Logger.getLogger(controllerTarjetaPokemon.class.getName()).log(Level.SEVERE, null, ex);
            }
            cc = cargador.getController();

            Stage confirmar = new Stage();
            Scene scene = new Scene(raiz);
            confirmar.setScene(scene);
            confirmar.setTitle("Confirmar");
            cc.enviaStage((Stage) txtEVsAtk.getScene().getWindow(), conexion, "a", false);
            confirmar.getIcons().add(new Image("Victini.png"));
            confirmar.showAndWait();
            if ((boolean) confirmar.getUserData()) {
                System.out.println("ola");
            }
        });
        txtMote.setText("");
    }
    
    void enviaMiembro(Miembro m) {
        miembro = m;
        txtEspecie.setText(m.getEspecie());
        cbEspecie.getItems().clear();
        cbEspecie.getItems().add(m.getEspecie());
        cbEspecie.getSelectionModel().select(0);
        spNivel.getValueFactory().setValue(m.getNivel());
        txtMote.setText(m.getMote());
        cbHabilidad.getItems().clear();
        cbHabilidad.getItems().add(m.getHabilidad());
        cbHabilidad.getSelectionModel().select(0);
        txtObjeto.setText(m.getObjeto());
        cbGenero.getSelectionModel().select(Character.valueOf(m.getGenero()));
        cbTipo1.getSelectionModel().select(m.getTipo1());
        cbTipo2.getSelectionModel().select(m.getTipo2());
        
        util.recuperarImagenBBDD(m.getSprite(), imgPokemon);
        List<TextField> listText = new ArrayList<>();
        listText.add(txtMovimiento1);
        listText.add(txtMovimiento2);
        listText.add(txtMovimiento3);
        listText.add(txtMovimiento4);
        util.leerMovimientos(m, listText);
        cargarEVs();
        cargarIVs();
    }
    
    void cargarIVs() {
        List<Slider> listBarras = new ArrayList<>();
        listBarras.add(sdIVsHp);
        listBarras.add(sdIVsAtk);
        listBarras.add(sdIVsDef);
        listBarras.add(sdIVsSpA);
        listBarras.add(sdIVsSpD);
        listBarras.add(sdIVsSpe);
        List<Label> listEtiquetas = new ArrayList<>();
        listEtiquetas.add(txtIVsHp);
        listEtiquetas.add(txtIVsAtk);
        listEtiquetas.add(txtIVsDef);
        listEtiquetas.add(txtIVsSpA);
        listEtiquetas.add(txtIVsSpD);
        listEtiquetas.add(txtIVsSpe);
        IVsEnvoltorio ivs = util.leerIVs(miembro);
        for (int i = 0; i < 6; i++) {
            listBarras.get(i).setValue(ivs.getEV(i).getValor());
            listEtiquetas.get(i).setText(ivs.getEV(i).getValor()+"/31");
        }
    }
    
    void cargarEVs() {
        List<Slider> listBarras = new ArrayList<>();
        listBarras.add(sdEVsHp);
        listBarras.add(sdEVsAtk);
        listBarras.add(sdEVsDef);
        listBarras.add(sdEVsSpA);
        listBarras.add(sdEVsSpD);
        listBarras.add(sdEVsSpe);
        List<Label> listEtiquetas = new ArrayList<>();
        listEtiquetas.add(txtEVsHp);
        listEtiquetas.add(txtEVsAtk);
        listEtiquetas.add(txtEVsDef);
        listEtiquetas.add(txtEVsSpA);
        listEtiquetas.add(txtEVsSpD);
        listEtiquetas.add(txtEVsSpe);
        EVsEnvoltorio evs = util.leerEVs(miembro);
        for (int i = 0; i < 6; i++) {
            listBarras.get(i).setValue(evs.getEV(i).getValor());
            listEtiquetas.get(i).setText(evs.getEV(i).getValor()+"/255");
        }
    }
}

