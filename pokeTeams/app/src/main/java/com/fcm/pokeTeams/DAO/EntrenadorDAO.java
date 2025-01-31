/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.DAO;

import com.fcm.pokeTeams.modelos.Entrenador;
import com.fcm.pokeTeams.util.Alertas;
import com.fcm.pokeTeams.util.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author DFran49
 */
public class EntrenadorDAO implements SentenciasInt<Entrenador> {

    private static final EntrenadorDAO instance = new EntrenadorDAO();
    private Conexion conexion = Conexion.getInstance();

    private EntrenadorDAO() {
    }

    public static EntrenadorDAO getInstance() {
        return instance;
    }

    @Override
    public void update(Entrenador e) {
        String sql = "UPDATE entrenador SET Nombre = ?, Genero = ?, Sprite = ?, Contraseña = ? WHERE ID_Entrenador = ?";
        try (PreparedStatement ps = conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(5, e.getIdEntrenador());
            ps.setString(1, e.getNombre());
            ps.setString(2, String.valueOf(e.getGenero()));
            ps.setString(3, e.getSprite());
            ps.setString(4, e.getContraseña());
            if (ps.executeUpdate() > 0) {
                System.out.println("Actualización exitosa.");
            } else {
                System.out.println("No se actualizó el Entrenador.");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void insert(Entrenador e) {
        String sql = "INSERT INTO entrenador (ID_Entrenador, Nombre, Genero, Sprite, Contraseña) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, e.getIdEntrenador());
            ps.setString(2, e.getNombre());
            ps.setString(3, String.valueOf(e.getGenero()));
            ps.setString(4, e.getSprite());
            ps.setString(5, e.getContraseña());
            if (ps.executeUpdate() > 0) {
                System.out.println("Inserción exitosa.");
            } else {
                System.out.println("No se insertó el Entrenador.");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public <U> void delete(U ref) {
        String sql = "DELETE FROM entrenador WHERE ID_Entrenador = ?";
        try (PreparedStatement ps = conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, (int) ref);
            if (ps.executeUpdate() > 0) {
                System.out.println("Borrado");
            } else {
                System.out.println("No borrado");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Entrenador getEntrenador(ResultSet rs) throws SQLException {
        Entrenador e = new Entrenador();
        e.setIdEntrenador(rs.getInt("ID_Entrenador"));
        e.setNombre(rs.getString("Nombre"));
        e.setGenero(rs.getString("Genero").charAt(0));
        e.setSprite(rs.getString("Sprite"));
        e.setContraseña(rs.getString("Contraseña"));
        e.setEsAdmin(rs.getBoolean("esAdmin"));
        return e;
    }

    public Entrenador selectEntrenador(int id) {
        String sql = "SELECT * FROM entrenador WHERE ID_Entrenador = ?";
        ResultSet rs;
        Entrenador e = new Entrenador();
        try (PreparedStatement ps = conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();
            e = getEntrenador(rs);
        } catch (SQLException ex) {
            Logger.getLogger(EntrenadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }

    public Entrenador selectEntrenador(String nombre) {
        String sql = "SELECT * FROM entrenador WHERE Nombre = ?";
        ResultSet rs;
        Entrenador e = new Entrenador();
        try (PreparedStatement ps = conexion.getConexion().prepareStatement(sql)) {
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            rs.next();
            e = getEntrenador(rs);
        } catch (SQLException ex) {
            Logger.getLogger(EntrenadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }

    public Entrenador selectEntrenador(String nombre, String password) {
        String sql = "SELECT * FROM entrenador WHERE Nombre = ? AND Contraseña = ?";
        ResultSet rs;
        Entrenador e = new Entrenador();
        try (PreparedStatement ps = conexion.getConexion().prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, password);
            //ps.setString(1, "Rosa");
            //ps.setString(2, "rosa");

            rs = ps.executeQuery();
            rs.next();
            e = getEntrenador(rs);
        } catch (SQLException ex) {
            Alertas credencialesIncorrectas = new Alertas(Alert.AlertType.ERROR, "CREDENCIALES INCORRECTAS",
                    "Ha introducido el nombre o la contraseña incorrecta!", "Intentelo de nuevo.");
            credencialesIncorrectas.mostrarAlerta();
        }
        return e;
    }
}
