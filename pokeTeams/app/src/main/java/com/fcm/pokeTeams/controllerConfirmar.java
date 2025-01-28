/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author DFran49
 */
import com.fcm.pokeTeams.modelos.EV;
import com.fcm.pokeTeams.modelos.EVsEnvoltorio;
import com.fcm.pokeTeams.modelos.Equipo;
import com.fcm.pokeTeams.modelos.EstadisticasEnvoltorio;
import com.fcm.pokeTeams.modelos.Generos;
import com.fcm.pokeTeams.modelos.Habilidad;
import com.fcm.pokeTeams.modelos.HabilidadesEnvoltorio;
import com.fcm.pokeTeams.modelos.IVsEnvoltorio;
import com.fcm.pokeTeams.modelos.Miembro;
import com.fcm.pokeTeams.modelos.Movimiento;
import com.fcm.pokeTeams.modelos.MovimientoEnvoltorio;
import com.fcm.pokeTeams.modelos.Pokemon;
import com.fcm.pokeTeams.modelos.Stat;
import com.fcm.pokeTeams.util.Conexion;
import com.fcm.pokeTeams.util.Utilidades;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class controllerConfirmar implements Initializable {
    private controllerCore cCore;
    Stage entrada;
    Scene escena;
    Utilidades utils = new Utilidades();
    Conexion conexion = null;
    private boolean admin;
    private Pokemon pokemon = new Pokemon();
    private Miembro miembro = new Miembro();
    private Miembro oldMiembro;
    private Equipo equipo;
    
    @FXML
    private Button btnCancelar;

    @FXML
    void cancelar(ActionEvent event) {
        Stage a = (Stage) btnCancelar.getScene().getWindow();
        a.close();
    }

    @FXML
    void cerrar(ActionEvent event) {
        Stage a = (Stage) btnCancelar.getScene().getWindow();
        a.setUserData(false);
        a.close();
        entrada.close();
    }

    @FXML
    void guardar(ActionEvent event) {
        Stage a = (Stage) btnCancelar.getScene().getWindow();
        
        escena = entrada.getScene();
        
        switch ((int) a.getUserData()) {
            case 1 -> {
                cargarDatosPokemon();
                insertarPokemon();
            }
            case 2 -> {
                cargarDatosPokemon();
                editarPokemon();
            }
            case 3 -> {
                cargarDatosMiembro();
                insertarMiembro();
            }
            case 4 -> {
                cargarDatosMiembro();
                editarMiembro();
            }
        }
        a.close();
        entrada.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    private void cargarDatosPokemon() {
        pokemon.setEspecie(((TextField)escena.lookup("#txtEspecie")).getText());
        pokemon.setDenominacion(((TextArea)escena.lookup("#txtDenominacion")).getText());
        pokemon.setDescripcion(((TextArea)escena.lookup("#txtDescripcion")).getText());
        String a = ((TextArea)escena.lookup("#txtTamaño")).getText();
        a = a.replace(" metros", "");
        pokemon.setTamaño(Double.parseDouble(a));
        a = ((TextArea)escena.lookup("#txtPeso")).getText();
        a = a.replace(" kilogramos", "");
        pokemon.setPeso(Double.parseDouble(a));
        pokemon.setSprite(utils.codificarImagen(((ImageView)escena.lookup("#imgPokemon")).getImage()));
        pokemon.setTipo1(((ComboBox<String>)escena.lookup("#cbTipo1")).getValue());
        pokemon.setTipo2(((ComboBox<String>)escena.lookup("#cbTipo2")).getValue());
        pokemon.setEstadisticas(leerStats());
        pokemon.setHabilidades(leerHabilidades());
    }
    
    private void cargarDatosMiembro() {
        miembro.setEspecie(((ComboBox<String>)escena.lookup("#cbEspecie")).getValue());
        miembro.setNivel(((Spinner<Integer>)escena.lookup("#spNivel")).getValue());
        miembro.setMote(((TextField)escena.lookup("#txtMote")).getText());
        miembro.setHabilidad(((ComboBox<String>)escena.lookup("#cbHabilidad")).getValue());
        miembro.setObjeto(((TextField)escena.lookup("#txtObjeto")).getText());
        miembro.setGenero(Generos.fromPokemon(((ComboBox<String>)escena.lookup("#cbGenero")).getValue()));
        miembro.setNaturaleza(((ComboBox<String>)escena.lookup("#cbNaturaleza")).getValue());
        miembro.setMovimientos(leerMovimientos());
        miembro.setIvs(leerIVs());
        miembro.setEvs(leerEVs());
        
        try {
            String query = "SELECT N_Pokedex FROM pokemon WHERE Especie = '" + miembro.getEspecie() + "'";
            Statement statement = conexion.getConexion().createStatement();
            ResultSet result = statement.executeQuery(query);
            result.next();
            miembro.setnPokedex(result.getInt("N_Pokedex"));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        System.out.println(miembro.getnPokedex());
        equipo = (Equipo)btnCancelar.getScene().getUserData();
    }
    
    private void insertarPokemon() {
        
        PreparedStatement preparado = null;
        try {
            String query = "INSERT INTO pokemon (Especie, Denominacion, Descripcion, Sprite, Tipo_1, Tipo_2, Tamaño, Peso, Habilidades, Estadisticas) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Connection c = conexion.getConexion();
            preparado = c.prepareStatement(query);

            preparado.setString(1, pokemon.getEspecie());
            preparado.setString(2, pokemon.getDenominacion());
            preparado.setString(3, pokemon.getDescripcion());
            preparado.setString(4, pokemon.getSprite());
            preparado.setString(5, pokemon.getTipo1());
            preparado.setString(6, pokemon.getTipo2());
            preparado.setDouble(7, pokemon.getTamaño());
            preparado.setDouble(8, pokemon.getPeso());
            preparado.setString(9, pokemon.getHabilidades());
            preparado.setString(10, pokemon.getEstadisticas());

            if (preparado.executeUpdate() > 0) {
                System.out.println("Inserción exitosa.");
            } else {
                System.out.println("No se insertó el Equipo.");
            }
            cCore.cargarGridPokemon(admin);
        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        } finally {
            try {
                if (preparado != null) preparado.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            cCore.cargarGridPokemon(true);
        } catch (SQLException ex) {
            Logger.getLogger(controllerConfirmar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void editarPokemon() {
        PreparedStatement preparado = null;
        try {
            String query = "UPDATE pokemon SET Especie = ?, Denominacion = ?, Descripcion = ?, Sprite = ?, Tipo_1 = ?, Tipo_2 = ?, "
                    + "Tamaño = ?, Peso = ?, Habilidades = ?, Estadisticas = ? WHERE N_Pokedex = ?;";
            Connection c = conexion.getConexion();
            preparado = c.prepareStatement(query);

            preparado.setString(1, pokemon.getEspecie());
            preparado.setString(2, pokemon.getDenominacion());
            preparado.setString(3, pokemon.getDescripcion());
            preparado.setString(4, pokemon.getSprite());
            preparado.setString(5, pokemon.getTipo1());
            preparado.setString(6, pokemon.getTipo2());
            preparado.setDouble(7, pokemon.getTamaño());
            preparado.setDouble(8, pokemon.getPeso());
            preparado.setString(9, pokemon.getHabilidades());
            preparado.setString(10, pokemon.getEstadisticas());
            preparado.setInt(11, pokemon.getnPokedex());

            if (preparado.executeUpdate() > 0) {
                System.out.println("Inserción exitosa.");
            } else {
                System.out.println("No se insertó el Equipo.");
            }
            cCore.cargarGridPokemon(admin);
        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        } finally {
            try {
                if (preparado != null) preparado.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            cCore.cargarGridPokemon(true);
        } catch (SQLException ex) {
            Logger.getLogger(controllerConfirmar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void insertarMiembro() {
        System.out.println("insertar");
        PreparedStatement preparado = null;
        try {
            String query = "INSERT INTO equipo (ID_Equipo, Mote, N_Pokedex, ID_Entrenador, Nombre_Equipo, Formato, Genero, Nivel, Habilidad, "
                    + "Objeto, Movimientos, EVs, IVs) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Connection c = conexion.getConexion();
            
            Statement stmt = c.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0;"); 
            
            
            preparado = c.prepareStatement(query);

            preparado.setString(1, equipo.getIdEquipo()+"");
            preparado.setString(2, miembro.getMote());
            preparado.setInt(3, miembro.getnPokedex());
            preparado.setInt(4, equipo.getIdEntrenador());
            preparado.setString(5, equipo.getNombre());
            preparado.setString(6, equipo.getFormato());
            preparado.setString(7, String.valueOf(miembro.getGenero()));
            preparado.setInt(8, miembro.getNivel());
            preparado.setString(9, miembro.getHabilidad());
            preparado.setString(10, miembro.getObjeto());
            preparado.setString(11, miembro.getMovimientos());
            preparado.setString(12, miembro.getEvs());
            preparado.setString(13, miembro.getIvs());

            if (preparado.executeUpdate() > 0) {
                System.out.println("Inserción exitosa.");
            } else {
                System.out.println("No se insertó el Equipo.");
            }
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1;");
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
    
    private void editarMiembro() {
        PreparedStatement preparado = null;
        try {
            
            String query = "UPDATE equipo SET N_Pokedex = ?, Genero = ?, Nivel = ?, Mote = ?, Habilidad = ?, Naturaleza = ?, Objeto = ?, Movimientos = ?, EVs = ?, IVs = ? "
                    + "WHERE ID_Equipo = ? AND Mote = ?;";
            Connection c = conexion.getConexion();
            
            Statement stmt = c.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0;");
            
            preparado = c.prepareStatement(query);

            preparado.setInt(1, miembro.getnPokedex());
            preparado.setString(2, miembro.getGenero()+"");
            preparado.setInt(3, miembro.getNivel());
            preparado.setString(4, miembro.getMote());
            preparado.setString(5, miembro.getHabilidad());
            preparado.setString(6, miembro.getNaturaleza());
            preparado.setString(7, miembro.getObjeto());
            preparado.setString(8, miembro.getMovimientos());
            preparado.setString(9, miembro.getEvs());
            preparado.setString(10, miembro.getIvs());
            preparado.setInt(11, equipo.getIdEquipo());
            preparado.setString(12, oldMiembro.getMote());

            if (preparado.executeUpdate() > 0) {
                System.out.println("Inserción exitosa.");
            } else {
                System.out.println("No se insertó el Equipo.");
            }
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1;");

        } catch (SQLException e) {
            System.out.println("Error al editar: " + e.getMessage());
        } finally {
            try {
                if (preparado != null) preparado.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private String leerStats() {
        List<Stat> stats = new ArrayList<>();
        stats.add(new Stat("HP", (int)((Slider) escena.lookup("#sdHp")).getValue()));
        stats.add(new Stat("Atk", (int)((Slider) escena.lookup("#sdAtk")).getValue()));
        stats.add(new Stat("Def", (int)((Slider) escena.lookup("#sdDef")).getValue()));
        stats.add(new Stat("SpA", (int)((Slider) escena.lookup("#sdSpA")).getValue()));
        stats.add(new Stat("SpD", (int)((Slider) escena.lookup("#sdSpD")).getValue()));
        stats.add(new Stat("SpE", (int)((Slider) escena.lookup("#sdSpe")).getValue()));
        
        EstadisticasEnvoltorio estadisticas = new EstadisticasEnvoltorio(stats);
        return utils.escribirStats(estadisticas);
    }
    
    private String leerHabilidades() {
        List<Habilidad> habilidades = new ArrayList<>();
        habilidades.add(new Habilidad(((TextField)escena.lookup("#txtNombreHabilidad1")).getText(),
                ((TextArea)escena.lookup("#txtHabilidad1")).getText()));
        if (!((TextField)escena.lookup("#txtNombreHabilidad2")).getText().isEmpty() && !((TextArea)escena.lookup("#txtHabilidad2")).getText().isEmpty()) {
            habilidades.add(new Habilidad(((TextField)escena.lookup("#txtNombreHabilidad2")).getText(),
                ((TextArea)escena.lookup("#txtHabilidad2")).getText()));
        }
        if (!((TextField)escena.lookup("#txtNombreHabilidad3")).getText().isEmpty() && !((TextArea)escena.lookup("#txtHabilidad3")).getText().isEmpty()) {
            habilidades.add(new Habilidad(((TextField)escena.lookup("#txtNombreHabilidad3")).getText(),
                ((TextArea)escena.lookup("#txtHabilidad3")).getText()));
        }
        return utils.escribirHabilidades(new HabilidadesEnvoltorio(habilidades));
    }
    
    private String leerMovimientos() {
        List<Movimiento> movimientos = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            String texto = ((TextField) escena.lookup("#txtMovimiento"+i)).getText();
            if (!texto.isEmpty()) {
                movimientos.add(new Movimiento(texto));
                System.out.println(movimientos.get(i-1).getMovimiento());
            }
        }
        
        MovimientoEnvoltorio envoltorio = new MovimientoEnvoltorio(movimientos);
        return utils.escribirMovimientos(envoltorio);
    }
    
    private String leerIVs() {
        List<EV> stats = new ArrayList<>();
        stats.add(new EV("HP", (int)((Slider) escena.lookup("#sdIVsHp")).getValue()));
        stats.add(new EV("Atk", (int)((Slider) escena.lookup("#sdIVsAtk")).getValue()));
        stats.add(new EV("Def", (int)((Slider) escena.lookup("#sdIVsDef")).getValue()));
        stats.add(new EV("SpA", (int)((Slider) escena.lookup("#sdIVsSpA")).getValue()));
        stats.add(new EV("SpD", (int)((Slider) escena.lookup("#sdIVsSpD")).getValue()));
        stats.add(new EV("SpE", (int)((Slider) escena.lookup("#sdIVsSpe")).getValue()));
        
        IVsEnvoltorio estadisticas = new IVsEnvoltorio(stats);
        return utils.escribirIVs(estadisticas);
    }
    
    private String leerEVs() {
        List<EV> stats = new ArrayList<>();
        stats.add(new EV("HP", (int)((Slider) escena.lookup("#sdEVsHp")).getValue()));
        stats.add(new EV("Atk", (int)((Slider) escena.lookup("#sdEVsAtk")).getValue()));
        stats.add(new EV("Def", (int)((Slider) escena.lookup("#sdEVsDef")).getValue()));
        stats.add(new EV("SpA", (int)((Slider) escena.lookup("#sdEVsSpA")).getValue()));
        stats.add(new EV("SpD", (int)((Slider) escena.lookup("#sdEVsSpD")).getValue()));
        stats.add(new EV("SpE", (int)((Slider) escena.lookup("#sdEVsSpe")).getValue()));
        
        EVsEnvoltorio estadisticas = new EVsEnvoltorio(stats);
        return utils.escribirEVs(estadisticas);
    }

    public void enviarAPokemon(Stage s, Conexion c, controllerCore controlador, boolean admin) {
        entrada = s;
        Stage ventana = (Stage) this.btnCancelar.getScene().getWindow();
        ventana.setOnCloseRequest(event -> event.consume());
        conexion = c;
        this.admin = admin;
        cCore = (controllerCore) controlador;
    }
    
    public void enviarEditarPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }
    
    public void enviarEditarMiembro(Miembro m) {
        this.oldMiembro = m;
    }
    
    public void enviaStage(Stage s, Conexion c, Object controlador) {
        entrada = s;
        Stage ventana = (Stage) this.btnCancelar.getScene().getWindow();
        ventana.setOnCloseRequest(event -> event.consume());
        conexion = c;
        if (controlador instanceof controllerCore) {
            cCore = (controllerCore) controlador;
        }
    }
}
