/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

import com.fcm.pokeTeams.modelos.Entrenador;
import com.fcm.pokeTeams.modelos.Equipo;
import com.fcm.pokeTeams.modelos.Miembro;
import com.fcm.pokeTeams.modelos.Pokemon;
import com.fcm.pokeTeams.util.Conexion;
import com.fcm.pokeTeams.util.Utilidades;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    private controllerLogIn cLi;
    private ContextMenu contextMenu;
    Conexion conexion = null;
    int col = 0;
    int row = 0;
    private Entrenador entrenador;
    Utilidades utils = new Utilidades();
    ObservableList<Pokemon> listaPokemon = FXCollections.observableArrayList();
    private controllerConfirmar cc;
    Map parametros = new HashMap();

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
    private WebView wbInforme;

    @FXML
    void añadirEquipo(MouseEvent event) {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/popUp_añadir_equipo_v1.fxml"));
            root = loader.load();

            Stage miStage = new Stage();
            Scene inicio = new Scene(root);
            miStage.setScene(inicio);
            miStage.setTitle("Añadir equipo");
            miStage.getIcons().add(new Image("Plusle.png"));
            miStage.showAndWait();
            List<String> datos = (List<String>) miStage.getUserData();
            if (!datos.isEmpty()) {
                PreparedStatement preparado = null;
                 try {
                    String query = "INSERT INTO equipo (ID_Equipo, ID_Entrenador, Nombre_Equipo, Formato) VALUES (?, ?, ?, ?)";
                    Connection c = conexion.getConexion();
                    preparado = c.prepareStatement(query);

                    String select = "SELECT MAX(ID_Equipo) AS ultimo_equipo FROM equipo";
                    Statement statement = conexion.getConexion().createStatement();
                    ResultSet result = statement.executeQuery(select);
                    result.next();
                    
                    preparado.setInt(1, result.getInt("ultimo_equipo")+1);
                    preparado.setInt(2, entrenador.getIdEntrenador());
                    preparado.setString(3, datos.get(0));
                    preparado.setString(4, datos.get(1));

                    if (preparado.executeUpdate() > 0) {
                        System.out.println("Inserción exitosa.");
                    } else {
                        System.out.println("No se insertó el Equipo.");
                    }
                    cargarGridEquipo();
                } catch (SQLException e) {
                    System.out.println("Error al insertar: " + e.getMessage());
                } finally {
                    try {
                        if (preparado != null) preparado.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(controllerCore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void añadirPokemon(MouseEvent event) {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/emergente_añadir_pokemon_v1.fxml"));
            root = loader.load();

            Stage miStage = new Stage();
            Scene inicio = new Scene(root);
            miStage.setScene(inicio);
            miStage.setTitle("Añadir pokemon");
            miStage.setOnCloseRequest(evento -> {
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
                cc.enviarAPokemon(miStage, conexion, this, entrenador.isEsAdmin());
                confirmar.setUserData(1);
                confirmar.getIcons().add(new Image("Victini.png"));
                confirmar.showAndWait();
            });
            miStage.getIcons().add(new Image("Plusle.png"));
            miStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(controllerCore.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/popUp_cambiar_contraseña.fxml"));
            root = loader.load();
            
            controllerPopUpCambioContraseña ccn = loader.getController();
            ccn.pasoVariables(conexion, entrenador);

            Stage miStage = new Stage();
            Scene inicio = new Scene(root);
            miStage.setScene(inicio);
            miStage.setTitle("Cambiar contraseña");
            miStage.getIcons().add(new Image("Klink.png"));
            miStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(controllerCore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void cambiarGenero(ActionEvent event) {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/popUp_cambiar_genero.fxml"));
            root = loader.load();
            
            controllerPopUpCambioGenero ccn = loader.getController();
            ccn.pasoVariables(conexion, entrenador, this);

            Stage miStage = new Stage();
            Scene inicio = new Scene(root);
            miStage.setScene(inicio);
            miStage.setTitle("Cambiar genero");
            miStage.getIcons().add(new Image("Klink.png"));
            miStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(controllerCore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void cambiarNombre(ActionEvent event) {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/popUp_cambiar_nombre.fxml"));
            root = loader.load();
            
            controllerPopUpCambioNombre ccn = loader.getController();
            ccn.pasoVariables(conexion, entrenador, this);

            Stage miStage = new Stage();
            Scene inicio = new Scene(root);
            miStage.setScene(inicio);
            miStage.setTitle("Cambiar nombre");
            miStage.getIcons().add(new Image("Klink.png"));
            miStage.setUserData(entrenador.getIdEntrenador());
            miStage.showAndWait();
            
        } catch (IOException ex) {
            Logger.getLogger(controllerCore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void cursorEntra(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView imagen = (ImageView) event.getSource();
            imagen.setOpacity(0.6);
        } else {
            Button boton = (Button) event.getSource();
            boton.setOpacity(0.6);
        }
    }

    @FXML
    void cursorSale(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView imagen = (ImageView) event.getSource();
            imagen.setOpacity(1);
        } else {
            Button boton = (Button) event.getSource();
            boton.setOpacity(1);
        }
    }

    @FXML
    void eliminarCuenta(ActionEvent event) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/popUp_eliminar.fxml"));
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(controllerTarjetaPokemon.class.getName()).log(Level.SEVERE, null, ex);
        }

        Stage miStage = new Stage();
        Scene inicio = new Scene(root);
        miStage.setScene(inicio);
        miStage.setTitle("Eliminar cuenta: " + txtNombreEntrenador.getText());
        miStage.getIcons().add(new Image("Trubbish.png"));
        miStage.setOnCloseRequest(evento -> {
            miStage.setUserData(false);
        });
        miStage.showAndWait();
        if ((boolean) miStage.getUserData()) {
            try {
                
                String query = "DELETE FROM entrenador WHERE ID_Entrenador = ?";
                Connection c = conexion.getConexion();
                PreparedStatement preparado = c.prepareStatement(query);
                preparado.setInt(1, entrenador.getIdEntrenador());
                if (preparado.executeUpdate() > 0) {
                    System.out.println("Borrado");
                } else {
                    System.out.println("No borrado");
                }
            } catch (SQLException e) {
                System.out.println("Error al conectar con la BD: " + e.getMessage());
            }
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
                        PreparedStatement preparado = null;
                        try {
                            String query = "UPDATE entrenador SET Sprite = ? WHERE ID_Entrenador = ?;";
                            Connection c = conexion.getConexion();
                            preparado = c.prepareStatement(query);

                            preparado.setString(1, utils.codificarImagen(imagen));
                            preparado.setInt(2, entrenador.getIdEntrenador());

                            if (preparado.executeUpdate() > 0) {
                                System.out.println("Inserción exitosa.");
                            } else {
                                System.out.println("No se insertó.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Error al editar: " + e.getMessage());
                        } finally {
                            try {
                                if (preparado != null) preparado.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        imgEntrenador.setImage(imagen);
                    }
        });
        
        contextMenu.getItems().add(item1);
        
        imgEntrenador.setOnContextMenuRequested(event -> 
            contextMenu.show(imgEntrenador, event.getScreenX(), event.getScreenY())
        );
        
        cbTipo1.getItems().addAll("Acero","Agua","Bicho","Dragón","Eléctrico","Fantasma",
                "Fuego","Hada","Hielo","Lucha","Normal","Planta","Psíquico",
                "Roca","Siniestro","Tierra","Veneno","Volador");
        cbTipo2.getItems().addAll("Ninguno","Acero","Agua","Bicho","Dragón","Eléctrico","Fantasma",
                "Fuego","Hada","Hielo","Lucha","Normal","Planta","Psíquico",
                "Roca","Siniestro","Tierra","Veneno","Volador");
        cbEstadistica.getItems().addAll("HP","Atk","Def","SpA","SpD","SpE");
        cbEstadisticaOrden.getItems().addAll(cbEstadistica.getItems());
        inicializarSpinners();
        prepararInformes();
        
        utils.crearTooltip("Añadir equipo", btnAddEquipo);
        utils.crearTooltip("Añadir pokemon", btnAddPokemon);
        utils.crearTooltip("Buscar equipo", btnBuscarEquipo);
        utils.crearTooltip("Buscar pokemon", btnBuscarPokemon);
        utils.crearTooltip("Filtrar pokemon", btnFiltrarPokemon);
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
    }
    
    private void cargarPokemon(Pokemon pokemon, boolean a) {
        try {
            FXMLLoader cargarPokemon = new FXMLLoader(getClass().getResource("fxml/tarjeta_pokemon_v1.fxml"));
            SplitPane tarjetaPokemon = cargarPokemon.load();
            controllerTarjetaPokemon controlador = cargarPokemon.getController();

            controlador.asignarPokemon(pokemon, a, conexion);
            controlador.asignarControladorCore(this);
            utils.crearTooltip(pokemon.getEspecie(), tarjetaPokemon);
            gridPokemon.add(tarjetaPokemon, col, row);
            /*tarjetaPokemon.boundsInParentProperty().addListener((obs, oldBounds, newBounds) -> {
                    boolean isVisible = isNodeVisible((ScrollPane)gridPokemon.getParent(), tarjetaPokemon);
                    System.out.println("Tarjeta en [" + i + ", " + j + "] visible: " + isVisible);
                });*/
            if(col == 2) {
                col = 0;
                row++;
            } else {
                col++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void cargarEquipo(Equipo e) {
        try {
            FXMLLoader cargarEquipo = new FXMLLoader(getClass().getResource("fxml/tarjeta_equipo_v1.fxml"));
            SplitPane tarjetaEquipo = cargarEquipo.load();
            controllerEquipos controladorEquipo = cargarEquipo.getController();

            controladorEquipo.asignarEquipo(e, conexion);
            controladorEquipo.asignarControladorCore(this);
            utils.crearTooltip(e.getNombre(), tarjetaEquipo);
            gridEquipos.add(tarjetaEquipo, 0, row);
            row++;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    void setControladorEnlace(controllerLogIn c) {
        cLi = c;
    }
    
    void cargarGridPokemon(Boolean admin) throws SQLException {
        this.gridPokemon.getChildren().clear();
        String query = "SELECT * FROM pokemon";
        Statement statement = conexion.getConexion().createStatement();
        ResultSet result = statement.executeQuery(query);
        while (result.next()) {                    
            Pokemon temp = new Pokemon();
            temp.setnPokedex(result.getString("N_Pokedex"));
            temp.setEspecie(result.getString("Especie"));
            temp.setDenominacion(result.getString("Denominacion"));
            temp.setDescripcion(result.getString("Descripcion"));
            temp.setSprite(result.getString("Sprite"));
            temp.setTipo1(result.getString("Tipo_1"));
            temp.setTipo2(result.getString("Tipo_2"));
            temp.setTamaño(result.getDouble("Tamaño"));
            temp.setPeso(result.getDouble("Peso"));
            temp.setHabilidades(result.getString("Habilidades"));
            temp.setEstadisticas(result.getString("Estadisticas"));
            cargarPokemon(temp, admin);
        }
        row = 0;
        col = 0;
    }
    
    void cargarGridEquipo() throws SQLException {
        this.gridEquipos.getChildren().clear();
        String query = "SELECT DISTINCT ID_Equipo FROM equipo WHERE ID_Entrenador = " + entrenador.getIdEntrenador();

        Statement statement = conexion.getConexion().createStatement();
        ResultSet result = statement.executeQuery(query);
        int idEquipo = 0;
        while (result.next()) {                
            idEquipo = result.getInt("ID_Equipo");
            query = "SELECT * FROM equipo WHERE ID_Equipo = " + idEquipo;
            statement = conexion.getConexion().createStatement();
            ResultSet resultado = statement.executeQuery(query);
            resultado.next();
            Equipo tempEquipo = new Equipo();
            tempEquipo.setIdEquipo(resultado.getInt("ID_Equipo"));
            tempEquipo.setFormato(resultado.getString("Formato"));
            tempEquipo.setNombre(resultado.getString("Nombre_Equipo"));
            tempEquipo.setIdEntrenador(entrenador.getIdEntrenador());
            cargarEquipo(tempEquipo);
        }
        row = 0;
    }
    
    void refrescarUser() throws SQLException {
        String query = "SELECT * FROM entrenador WHERE ID_Entrenador = " + entrenador.getIdEntrenador();

        Statement statement = conexion.getConexion().createStatement();
        ResultSet result = statement.executeQuery(query);
        result.next();


        entrenador.setNombre(result.getString("Nombre"));
        entrenador.setGenero(result.getString("Genero").charAt(0));
        entrenador.setSprite(result.getString("Sprite"));
        entrenador.setEsAdmin(result.getBoolean("esAdmin"));
        
        txtNombreEntrenador.setText(entrenador.getNombre());
        utils.crearTooltip("Entrenador " + entrenador.getNombre(), txtNombreEntrenador);
        System.out.println(entrenador.getGenero());
        switch (entrenador.getGenero()) {
                case 'F' -> {
                    entrenador.setGenero('F');
                    txtGeneroEntrenador.setText("Mujer");
                    utils.crearTooltip("Género: Mujer", txtGeneroEntrenador);
                }
                case 'M' -> {
                    entrenador.setGenero('M');
                    txtGeneroEntrenador.setText("Hombre");
                    utils.crearTooltip("Género: Hombre", txtGeneroEntrenador);
                }
                case 'O' -> {
                    entrenador.setGenero('O');
                    txtGeneroEntrenador.setText("Otro");
                    utils.crearTooltip("Género: Otro", txtGeneroEntrenador);
                }
            }
        
        utils.recuperarImagenBBDD(entrenador.getSprite(), imgEntrenador);
        utils.crearTooltip("Entrenador: " + entrenador.getNombre(), imgEntrenador);
        btnAddPokemon.setVisible(entrenador.isEsAdmin());
        
    }
    
    void enviaLogIn(Conexion c, String user) {
        conexion = c;
        entrenador = new Entrenador();
        try {
            String query = "SELECT ID_Entrenador FROM entrenador WHERE Nombre = '" + user +"'";

            Statement statement = conexion.getConexion().createStatement();
            ResultSet result = statement.executeQuery(query);
            result.next();
            entrenador.setIdEntrenador(result.getInt("ID_Entrenador"));
            
            refrescarUser();
            cargarGridPokemon(entrenador.isEsAdmin());
            
            cargarGridEquipo();
        } catch (SQLException e) {
            System.out.println("Error al conectar con la BD: " + e.getMessage());
        }
    }
    
    private void cerrar() {
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/logIn.fxml"));
            root = loader.load();

            Stage miStage = (Stage) this.txtBusquedaEquipos.getScene().getWindow();
            Scene inicio = new Scene(root);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            miStage.setX((screenBounds.getWidth() - miStage.getWidth() / 1.1));
            miStage.setY((screenBounds.getHeight() - miStage.getHeight() / 1.3));
            miStage.setScene(inicio);
        } catch (IOException ex) {
            Logger.getLogger(controllerCore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void prepararInformes() {
        cbInforme.getItems().addAll("Pokemon","Equipos","Entrenadores");
        
        cbInforme.setOnAction((event) -> {
            int selectedIndex=cbInforme.getSelectionModel().getSelectedIndex();
            Object selectedItem=cbInforme.getSelectionModel().getSelectedItem();
            
            if (btnInformeMostrar.isDisabled()) btnInformeMostrar.setDisable(false);
            
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
        int tipo = ckbInformeInc.isSelected() ? 0:1;
        String ruta = "/reports/";
        System.out.println(spInformeIDEnt.getChildrenUnmodifiable());
        parametros.clear();
        switch (cbInforme.getValue()) {
            case "Pokemon" -> {
                ruta = ruta + "pokemon";
                parametros.put("Tipo1", cbInformeTipo1.getValue());
                parametros.put("Tipo2", cbInformeTipo2.getValue());
            }
            case "Equipos" -> {
                ruta = ruta + "equipos";
                
                if (spInformeIDEnt.getValue() > 0 || spInformeIDEnt.getValue() < 100000) {
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
        System.out.println("a "+spInformeIDEnt.getValue());
        System.out.println("idEnt" + parametros.get("admin"));
        System.out.println(ruta);
        System.out.println(tipo);
        
        lanzaInforme(ruta, parametros, tipo);
    }
    
    private void lanzaInforme(String rutaInf, Map<String, Object> param, int tipo) {
        System.out.println(conexion.getConexion());
        wbInforme = new WebView();
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream(rutaInf));
            try {
                // Llena el informe con los datos de la conexión
                System.out.println(this.conexion);
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, param, conexion.getConexion());

                if (!jasperPrint.getPages().isEmpty()) {

                    //Exporta el informe a un archivo PDF (necesita librería)
                    String pdfOutputPath = "informe.pdf";
                    JasperExportManager.exportReportToPdfFile(jasperPrint, pdfOutputPath);

                    //Exporta el informe a un archivo HTML
                    String outputHtmlFile = "informeHTML.html";
                    JasperExportManager.exportReportToHtmlFile(jasperPrint, outputHtmlFile);

                    //Crea un WebView para mostrar la versión HTML del informe
                    if (tipo == 0) {
                        System.out.println("Incrustado");
                        wbInforme.getEngine().load(new File(outputHtmlFile).toURI().toString());
                    } else { //tipo==1
                        System.out.println("no Incrustado");
                        WebView wvnuevo = new WebView();
                        wvnuevo.getEngine().load(new File(outputHtmlFile).toURI().toString());
                        StackPane stackPane = new StackPane(wvnuevo);
                        Scene scene = new Scene(stackPane, 600, 500);
                        Stage stage = new Stage();
                        stage.setTitle("Informe en HTML");
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setResizable(true);
                        stage.setScene(scene);
                        stage.show();
                    }
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText("Alerta de Informe");
                    //alert.setContentText("La búsqueda " + mititulo.getText() + " no generó páginas");
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
}
