/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.DAO;

import com.fcm.pokeTeams.modelos.Equipo;
import com.fcm.pokeTeams.util.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author DFran49
 */
public class EquipoDAO implements SentenciasInt<Equipo> {

    private static final EquipoDAO instance = new EquipoDAO();
    private Conexion conexion = Conexion.getInstance();

    private EquipoDAO() {
    }

    public static EquipoDAO getInstance() {
        return instance;
    }

    @Override
    public void update(Equipo e) {
        String sql = "UPDATE equipo SET Nombre_Equipo = ?, Formato = ? WHERE ID_Equipo = ?";
        try (PreparedStatement ps = conexion.getConexion().prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getFormato());
            ps.setInt(3, e.getIdEquipo());
            int filasActualizadas;
            filasActualizadas = ps.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Actualización exitosa. Filas afectadas: " + filasActualizadas);
            } else {
                System.out.println("No se encontró el equipo para actualizar.");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void insert(Equipo e) {
        String sql = "INSERT INTO equipo (ID_Equipo, ID_Entrenador, Nombre_Equipo, Formato) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, getUltimoEquipo());
            ps.setInt(2, e.getIdEntrenador());
            ps.setString(3, e.getNombre());
            ps.setString(4, e.getFormato());

            if (ps.executeUpdate() > 0) {
                System.out.println("Inserción exitosa.");
            } else {
                System.out.println("No se insertó el Equipo.");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private int getUltimoEquipo() {
        String sql = "SELECT MAX(ID_Equipo) AS ultimo_equipo FROM equipo";
        try (PreparedStatement ps = conexion.getConexion().prepareStatement(sql)) {
            ResultSet result = ps.executeQuery();
            result.next();
            return result.getInt("ultimo_equipo") + 1;
        } catch (SQLException ex) {
            Logger.getLogger(EquipoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public <U> void delete(U ref) {
        String sql = "DELETE FROM equipo WHERE ID_Equipo = ?";
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

    public Equipo getEquipo(ResultSet rs) throws SQLException {
        Equipo e = new Equipo();
        e.setIdEquipo(rs.getInt("ID_Equipo"));
        e.setIdEntrenador(rs.getInt("ID_Entrenador"));
        e.setNombre(rs.getString("Nombre_Equipo"));
        e.setFormato(rs.getString("Formato"));
        return e;
    }

    public ObservableList<Equipo> getTodos(String filter) {
        ObservableList<Equipo> lista = FXCollections.observableArrayList();
        try {
            ArrayList<Integer> ids = new ArrayList<>();
            Equipo e;

            String sql = "SELECT DISTINCT ID_Equipo FROM equipo WHERE " + filter;
            System.out.println(sql);
            PreparedStatement ps = conexion.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("ID_Equipo"));
            }

            for (int id : ids) {
                sql = "SELECT * FROM equipo WHERE ID_Equipo = ?";
                ps = conexion.getConexion().prepareStatement(sql);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                rs.next();
                e = getEquipo(rs);
                lista.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EquipoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public Equipo getPorID(Equipo e) {
        String sql = "SELECT * FROM equipo WHERE ID_Equipo = ?";
        Equipo equipo = null;
        try {
            PreparedStatement ps = conexion.getConexion().prepareStatement(sql);
            ps = conexion.getConexion().prepareStatement(sql);
            ps.setInt(1, e.getIdEquipo());
            ResultSet rs = ps.executeQuery();
            rs.next();
            equipo = getEquipo(rs);
        } catch (SQLException ex) {
            Logger.getLogger(EquipoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return equipo;
    }
}
