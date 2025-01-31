/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

import com.fcm.pokeTeams.DAO.EntrenadorDAO;
import com.fcm.pokeTeams.DAO.EquipoDAO;
import com.fcm.pokeTeams.DAO.PokemonDAO;
import com.fcm.pokeTeams.modelos.Entrenador;
import com.fcm.pokeTeams.modelos.Equipo;
import com.fcm.pokeTeams.enums.Generos;
import com.fcm.pokeTeams.modelos.Pokemon;
import com.fcm.pokeTeams.enums.Tipos;
import com.fcm.pokeTeams.enums.VistasControladores;
import com.fcm.pokeTeams.util.CargadorFXML;
import com.fcm.pokeTeams.util.Conexion;
import com.fcm.pokeTeams.util.Utilidades;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author DFran49
 */
public class controllerCore implements Initializable {

    private Stage ventana;
    private ContextMenu contextMenu;
    private int col = 0;
    private int row = 0;
    protected Entrenador entrenador = null;
    private Utilidades utils = Utilidades.getInstance();
    private ObservableList<Pokemon> listaPokemon = FXCollections.observableArrayList();
    private ObservableList<Equipo> listaEquipos = FXCollections.observableArrayList();
    private Map parametros = new HashMap();
    protected String equipoAbierto = "";

    @FXML
    private ImageView btnAddEquipo;

    @FXML
    private ImageView btnAddPokemon;

    @FXML
    private ImageView btnBuscarEquipo;

    @FXML
    private ImageView btnBuscarPokemon;

    @FXML
    private ImageView btnFiltrarPokemon;

    @FXML
    private Button btnInformeMostrar;

    @FXML
    private ComboBox<String> cbEstadistica;

    @FXML
    private ComboBox<String> cbEstadisticaOrden;

    @FXML
    private ComboBox<String> cbInforme;

    @FXML
    private ComboBox<String> cbInformeTipo1;

    @FXML
    private ComboBox<String> cbInformeTipo2;

    @FXML
    private ComboBox<String> cbTipo1;

    @FXML
    private ComboBox<String> cbTipo2;

    @FXML
    private CheckBox ckbInformeAdmin;

    @FXML
    private CheckBox ckbInformeInc;

    @FXML
    private ToggleGroup especie;

    @FXML
    private ToggleGroup estadistica;

    @FXML
    private GridPane gridEquipos;

    @FXML
    private GridPane gridPokemon;

    @FXML
    private ImageView imgEntrenador;

    @FXML
    private ToggleGroup peso;

    @FXML
    private Spinner<Integer> spEstadistica;

    @FXML
    private Spinner<Integer> spInformeIDEnt;

    @FXML
    private Spinner<Double> spPesoMax;

    @FXML
    private Spinner<Double> spPesoMin;

    @FXML
    private Spinner<Double> spTamañoMax;

    @FXML
    private Spinner<Double> spTamañoMin;

    @FXML
    private Tab tabInformes;

    @FXML
    private ToggleGroup tamaño;

    @FXML
    private TitledPane tpFiltro;

    @FXML
    private TitledPane tpOrdenar;

    @FXML
    private TextField txtBusquedaEquipos;

    @FXML
    private TextField txtBusquedaPokemon;

    @FXML
    private TextField txtGeneroEntrenador;

    @FXML
    private TextField txtHabilidad;

    @FXML
    private TextField txtNombreEntrenador;

    @FXML
    private VBox vbFiltro;

    @FXML
    private WebView wvInforme;

    @FXML
    void añadirEquipo(MouseEvent event) {
        Stage añadirEquipo = new Stage();
        añadirEquipo.setUserData(entrenador);
        CargadorFXML.getInstance().cargar(VistasControladores.ADDEQUIPO, añadirEquipo);
        añadirEquipo.showAndWait();
    }

    @FXML
    void añadirPokemon(MouseEvent event) {
        Stage añadirPokemon = new Stage();
        CargadorFXML.getInstance().cargar(VistasControladores.ADDEDITPOKEMON, añadirPokemon);
        añadirPokemon.show();
    }

    @FXML
    void buscarEquipo(MouseEvent event) {

    }

    @FXML
    void buscarPokemon(MouseEvent event) {
        vbFiltro.setVisible(false);
        tpFiltro.expandedProperty().set(false);
        tpOrdenar.expandedProperty().set(false);
    }

    @FXML
    void busquedaDinamicaEquipos(KeyEvent event) {

    }

    @FXML
    void busquedaDinamicaPokemon(KeyEvent event) {

    }

    @FXML
    void cambiarContraseña(ActionEvent event) {
        Stage config = new Stage();
        CargadorFXML.getInstance().cargar(VistasControladores.EDITCONTRASEÑA, config);
        config.setUserData(entrenador);
        config.showAndWait();
    }

    @FXML
    void cambiarGenero(ActionEvent event) {
        Stage config = new Stage();
        CargadorFXML.getInstance().cargar(VistasControladores.EDITGENERO, config);
        config.setUserData(entrenador);
        config.showAndWait();
    }

    @FXML
    void cambiarNombre(ActionEvent event) {
        Stage config = new Stage();
        CargadorFXML.getInstance().cargar(VistasControladores.EDITNOMBRE, config);
        config.setUserData(entrenador);
        config.showAndWait();
    }

    @FXML
    void eliminarCuenta(ActionEvent event) {
        Stage eliminar = new Stage();
        CargadorFXML.getInstance().cargar(eliminar,"Eliminar cuenta: " + txtNombreEntrenador.getText());
        eliminar.showAndWait();
        if ((boolean) eliminar.getUserData()) {
            EntrenadorDAO.getInstance().delete(entrenador.getIdEntrenador());
            cerrar();
        }
    }

    @FXML
    void filtrarPokemon(MouseEvent event) {
        if (vbFiltro.isVisible()) {
            vbFiltro.setVisible(false);
        } else {
            vbFiltro.setVisible(true);
        }
    }

    @FXML
    void logOut(ActionEvent event) {
        cerrar();
    }

    @FXML
    void mostrarInforme(ActionEvent event) {
        selInforme();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("Cambiar imagen");
        item1.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();

            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Imagen jpg", "*.jpg"),
                    new FileChooser.ExtensionFilter("Imagen png", "*.png")
            );
            File archivoSeleccionado = fileChooser.showOpenDialog(null);
            if (archivoSeleccionado != null) {
                String rutaArchivo = archivoSeleccionado.toURI().toString();
                Image imagen = new Image(rutaArchivo);
                entrenador.setSprite(utils.codificarImagen(imagen));
                EntrenadorDAO.getInstance().update(entrenador);
                imgEntrenador.setImage(imagen);
            }
        });

        contextMenu.getItems().add(item1);

        imgEntrenador.setOnContextMenuRequested(event
                -> contextMenu.show(imgEntrenador, event.getScreenX(), event.getScreenY())
        );

        cbTipo1.getItems().addAll(Tipos.listaTipo1());
        cbTipo2.getItems().addAll(Tipos.listaTipo2());
        cbEstadistica.getItems().addAll("HP", "Atk", "Def", "SpA", "SpD", "SpE");
        cbEstadisticaOrden.getItems().addAll(cbEstadistica.getItems());
        inicializarSpinners();
        prepararInformes();

        utils.crearTooltip("Añadir equipo", btnAddEquipo);
        utils.crearTooltip("Añadir pokemon", btnAddPokemon);
        utils.crearTooltip("Buscar equipo", btnBuscarEquipo);
        utils.crearTooltip("Buscar pokemon", btnBuscarPokemon);
        utils.crearTooltip("Filtrar pokemon", btnFiltrarPokemon);

        Platform.runLater(() -> {
            iniciar();
            ventana = (Stage) txtBusquedaEquipos.getScene().getWindow();
        });
    }

    private void inicializarSpinners() {
        int stat = 255;
        spEstadistica.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, stat, 0));
        double tamaño = 999.99;
        spTamañoMin.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, tamaño, 0.0, 0.1));
        spTamañoMax.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, tamaño, 0.0, 0.1));
        double peso = 999.99;
        spPesoMin.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, peso, 0.0, 0.1));
        spPesoMax.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, peso, 0.0, 0.1));
        spInformeIDEnt.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1));
    }

    private void cargarPokemon(Pokemon pokemon) {
        /*tarjetaPokemon.boundsInParentProperty().addListener((obs, oldBounds, newBounds) -> {
                boolean isVisible = isNodeVisible((ScrollPane)gridPokemon.getParent(), tarjetaPokemon);
                System.out.println("Tarjeta en [" + i + ", " + j + "] visible: " + isVisible);
            });*/
        CargadorFXML.getInstance().cargar(gridPokemon, col, row, pokemon);
        if (col == 2) {
            col = 0;
            row++;
        } else {
            col++;
        }
    }

    private void cargarEquipo(Equipo e) {
        CargadorFXML.getInstance().cargar(gridEquipos, 0, row, e);
        row++;
    }

    void cargarGridPokemon() {
        this.gridPokemon.getChildren().clear();
        listaPokemon = PokemonDAO.getInstance().getTodos("");
        listaPokemon.forEach(pokemon -> cargarPokemon(pokemon));
        row = 0;
        col = 0;
    }

    void cargarGridEquipo() {
        this.gridEquipos.getChildren().clear();
        listaEquipos = EquipoDAO.getInstance().getTodos("ID_Entrenador = " + entrenador.getIdEntrenador());
        listaEquipos.forEach(equipo -> cargarEquipo(equipo));
        row = 0;
    }

    void refrescarUser() {
        entrenador = EntrenadorDAO.getInstance().selectEntrenador(entrenador.getIdEntrenador());
        asignarDatosUser();
    }

    void asignarDatosUser() {
        txtNombreEntrenador.setText(entrenador.getNombre());
        utils.crearTooltip("Entrenador " + entrenador.getNombre(), txtNombreEntrenador);
        txtGeneroEntrenador.setText(Generos.fromSigla(entrenador.getGenero()).getEntrenador());
        utils.crearTooltip("Género: " + Generos.fromSigla(entrenador.getGenero()).getEntrenador(), txtGeneroEntrenador);
        utils.recuperarImagenBBDD(entrenador.getSprite(), imgEntrenador);
        utils.crearTooltip("Entrenador: " + entrenador.getNombre(), imgEntrenador);
        btnAddPokemon.setVisible(entrenador.isEsAdmin());
    }

    void iniciar() {
        asignarDatosUser();
        cargarGridPokemon();
        cargarGridEquipo();
    }

    private void prepararInformes() {
        cbInforme.getItems().addAll("Pokemon", "Equipos", "Entrenadores");

        cbInforme.setOnAction((event) -> {
            int selectedIndex = cbInforme.getSelectionModel().getSelectedIndex();
            if (btnInformeMostrar.isDisabled()) {
                btnInformeMostrar.setDisable(false);
            }

            if (selectedIndex == 0) {
                cbInformeTipo1.setDisable(false);
                cbInformeTipo2.setDisable(false);
                ckbInformeAdmin.setDisable(true);
                spInformeIDEnt.setDisable(true);
            } else if (selectedIndex == 1) {
                cbInformeTipo1.setDisable(true);
                cbInformeTipo2.setDisable(true);
                ckbInformeAdmin.setDisable(true);
                spInformeIDEnt.setDisable(false);
            } else {
                cbInformeTipo1.setDisable(true);
                cbInformeTipo2.setDisable(true);
                ckbInformeAdmin.setDisable(false);
                spInformeIDEnt.setDisable(true);
            }
        });

        cbInformeTipo1.getItems().add("");
        cbInformeTipo1.getItems().addAll(cbTipo1.getItems());
        cbInformeTipo2.getItems().add("");
        cbInformeTipo2.getItems().addAll(cbTipo2.getItems());
    }

    private void selInforme() {
        int tipo = ckbInformeInc.isSelected() ? 0 : 1;
        String ruta = "/reports/";
        System.out.println(spInformeIDEnt.getChildrenUnmodifiable());
        parametros.clear();
        switch (cbInforme.getValue()) {
            case "Pokemon" -> {
                ruta = ruta + "pokemon";
                if (cbInformeTipo1.getValue() == null) {
                    parametros.put("Tipo1", "");
                } else {
                    System.out.println("Tipo " + cbInformeTipo1.getValue());
                    parametros.put("Tipo1", cbInformeTipo1.getValue());
                }
                if (cbInformeTipo2.getValue() == null) {
                    parametros.put("Tipo2", "");
                } else {
                    System.out.println("Tipo " + cbInformeTipo2.getValue());
                    parametros.put("Tipo2", cbInformeTipo2.getValue());
                }
            }
            case "Equipos" -> {
                ruta = ruta + "equipos";

                if (spInformeIDEnt.getValue() > 0 || spInformeIDEnt.getValue() < Integer.MAX_VALUE) {
                    parametros.put("idEnt", spInformeIDEnt.getValue());
                } else {
                    parametros.put("idEnt", 0);
                }

            }
            case "Entrenadores" -> {
                ruta = ruta + "entrenadores";
                int temp = -1;
                if (ckbInformeAdmin.isIndeterminate()) {
                    temp = 2;
                }
                if (ckbInformeAdmin.isSelected() && !ckbInformeAdmin.isIndeterminate()) {
                    temp = 1;
                }
                if (!ckbInformeAdmin.isSelected() && !ckbInformeAdmin.isIndeterminate()) {
                    temp = 0;
                }
                parametros.put("admin", temp);
            }
        }
        ruta = ruta + ".jasper";
        lanzaInforme(ruta, parametros, tipo);
    }

    private void lanzaInforme(String rutaInf, Map<String, Object> param, int tipo) {
        System.out.println(Conexion.getInstance().getConexion());
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream(rutaInf));
            try {
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, param, Conexion.getInstance().getConexion());

                if (!jasperPrint.getPages().isEmpty()) {

                    String outputHtmlFile = "informeHTML.html";
                    JasperExportManager.exportReportToHtmlFile(jasperPrint, outputHtmlFile);

                    if (tipo == 0) {
                        wvInforme.getEngine().load(new File(outputHtmlFile).toURI().toString());
                    } else {
                        WebView wvnuevo = new WebView();
                        wvnuevo.getEngine().load(new File(outputHtmlFile).toURI().toString());
                        StackPane stackPane = new StackPane(wvnuevo);
                        Scene scene = new Scene(stackPane, 600, 500);
                        Stage stage = new Stage();
                        stage.setTitle("Informe en HTML");
                        stage.getIcons().add(new Image("/img/pokedex.png"));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setResizable(true);
                        stage.setScene(scene);
                        stage.show();
                    }
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText("Alerta de Informe");
                    alert.showAndWait();
                }

            } catch (JRException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "Error al generar el informe: " + e.getMessage());
            }
        } catch (JRException ex) {
            System.out.println("ultimo: " + ex.getMessage());
        }
    }

    protected void asignarEquipoAbierto(String s) {
        equipoAbierto = s;
    }

    protected String comprobarEquipoAbierto() {
        return equipoAbierto;
    }

    private void cerrar() {
        CargadorFXML.getInstance().cargar(VistasControladores.LOGIN, ventana);
        CargadorFXML.getInstance().cerrarSesion();
    }
}
