/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.DAO;

import com.fcm.pokeTeams.modelos.Equipo;
import com.fcm.pokeTeams.modelos.Miembro;
import com.fcm.pokeTeams.util.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author DFran49
 */
public class MiembroDAO implements SentenciasInt<Miembro> {

    private static final MiembroDAO instance = new MiembroDAO();
    private Conexion conexion = Conexion.getInstance();

    private MiembroDAO() {
    }

    public static MiembroDAO getInstance() {
        return instance;
    }

    @Override
    public void update(Miembro m) {
        String sql = "UPDATE equipo SET N_Pokedex = ?, Genero = ?, Nivel = ?, Mote = ?, Habilidad = ?, Naturaleza = ?, Objeto = ?, Movimientos = ?, EVs = ?, IVs = ? "
                + "WHERE ID_Equipo = ? AND Mote = ?;";
        try (PreparedStatement ps = conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, m.getnPokedex());
            ps.setString(2, String.valueOf(m.getGenero()));
            ps.setInt(3, m.getNivel());
            ps.setString(4, m.getMote());
            ps.setString(5, m.getHabilidad());
            ps.setString(6, m.getNaturaleza());
            ps.setString(7, m.getObjeto());
            ps.setString(8, m.getMovimientos());
            ps.setString(9, m.getEvs());
            ps.setString(10, m.getIvs());
            ps.setInt(11, m.getEquipo().getIdEquipo());
            ps.setString(12, m.getOldMote());

            if (ps.executeUpdate() > 0) {
                System.out.println("Actualización exitosa.");
            } else {
                System.out.println("No se actualizó el pokemon.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void insert(Miembro m) {
        String sql = "INSERT INTO equipo (ID_Equipo, Mote, N_Pokedex, ID_Entrenador, Nombre_Equipo, Formato, Genero, Nivel, Habilidad, "
                + "Objeto, Movimientos, EVs, IVs, Naturaleza) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, m.getEquipo().getIdEquipo());
            ps.setString(2, m.getMote());
            ps.setInt(3, m.getnPokedex());
            ps.setInt(4, m.getEquipo().getIdEntrenador());
            ps.setString(5, m.getEquipo().getNombre());
            ps.setString(6, m.getEquipo().getFormato());
            ps.setString(7, String.valueOf(m.getGenero()));
            ps.setInt(8, m.getNivel());
            ps.setString(9, m.getHabilidad());
            ps.setString(10, m.getObjeto());
            ps.setString(11, m.getMovimientos());
            ps.setString(12, m.getEvs());
            ps.setString(13, m.getIvs());
            ps.setString(14, m.getNaturaleza());

            if (ps.executeUpdate() > 0) {
                System.out.println("Inserción exitosa.");
            } else {
                System.out.println("No se insertó el pokemon.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public <U> void delete(U m) {
        String sql = "DELETE FROM equipo WHERE ID_Equipo = ? AND Mote = ?";
        Miembro miembro = (Miembro) m;
        try (PreparedStatement ps = conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, miembro.getEquipo().getIdEquipo());
            ps.setString(2, miembro.getMote());
            if (ps.executeUpdate() > 0) {
                System.out.println("Borrado");
            } else {
                System.out.println("No borrado");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Miembro getMiembro(ResultSet rs) throws SQLException {
        Miembro m = new Miembro();
        Equipo temp = new Equipo();
        temp.setIdEquipo(rs.getInt("ID_Equipo"));
        temp.setIdEntrenador(rs.getInt("ID_Entrenador"));
        temp.setNombre(rs.getString("Nombre_Equipo"));
        temp.setFormato(rs.getString("Formato"));

        m.setEquipo(temp);
        m.setMote(rs.getString("Mote"));
        m.setEspecie(rs.getString("Especie"));
        m.setnPokedex(rs.getInt("N_Pokedex"));
        m.setGenero(rs.getString("Genero").charAt(0)); // Asumiendo que "Genero" es un String y se quiere convertir a char
        m.setNivel(rs.getInt("Nivel"));
        m.setHabilidad(rs.getString("Habilidad"));
        m.setNaturaleza(rs.getString("Naturaleza"));
        m.setObjeto(rs.getString("Objeto"));
        m.setMovimientos(rs.getString("Movimientos"));
        m.setStats(rs.getString("Estadisticas"));
        m.setEvs(rs.getString("EVs"));
        m.setIvs(rs.getString("IVs"));
        m.setSprite(rs.getString("Sprite"));
        return m;
    }

    public ObservableList<Miembro> getMiembros(Equipo e) {
        ObservableList<Miembro> lista = FXCollections.observableArrayList();

        Miembro m;
        String sql = "SELECT * FROM equipo JOIN pokemon USING(N_Pokedex) WHERE ID_Equipo = ?";

        ResultSet rs;
        try (PreparedStatement ps = conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, e.getIdEquipo());
            rs = ps.executeQuery();
            while (rs.next()) {
                m = getMiembro(rs);
                lista.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PokemonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }
}
