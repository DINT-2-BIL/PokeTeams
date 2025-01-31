/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.DAO;

import com.fcm.pokeTeams.modelos.Pokemon;
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
public class PokemonDAO implements SentenciasInt<Pokemon> {

    private static final PokemonDAO instance = new PokemonDAO();
    private Conexion conexion = Conexion.getInstance();

    private PokemonDAO() {
    }

    public static PokemonDAO getInstance() {
        return instance;
    }

    @Override
    public void update(Pokemon p) {
        String sql = "UPDATE pokemon SET Especie = ?, Denominacion = ?, Descripcion = ?, Sprite = ?, Tipo_1 = ?, Tipo_2 = ?, "
                + "Tamaño = ?, Peso = ?, Habilidades = ?, Estadisticas = ? WHERE N_Pokedex = ?";
        try (PreparedStatement ps = conexion.getConexion().prepareStatement(sql)) {
            ps.setString(1, p.getEspecie());
            ps.setString(2, p.getDenominacion());
            ps.setString(3, p.getDescripcion());
            ps.setString(4, p.getSprite());
            ps.setString(5, p.getTipo1());
            ps.setString(6, p.getTipo2());
            ps.setDouble(7, p.getTamaño());
            ps.setDouble(8, p.getPeso());
            ps.setString(9, p.getHabilidades());
            ps.setString(10, p.getEstadisticas());
            ps.setInt(11, p.getnPokedex());
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
    public void insert(Pokemon p) {
        String sql = "INSERT INTO pokemon (Especie, Denominacion, Descripcion, Sprite, Tipo_1, Tipo_2, Tamaño, Peso, Habilidades, Estadisticas) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.getConexion().prepareStatement(sql)) {
            ps.setString(1, p.getEspecie());
            ps.setString(2, p.getDenominacion());
            ps.setString(3, p.getDescripcion());
            ps.setString(4, p.getSprite());
            ps.setString(5, p.getTipo1());
            ps.setString(6, p.getTipo2());
            ps.setDouble(7, p.getTamaño());
            ps.setDouble(8, p.getPeso());
            ps.setString(9, p.getHabilidades());
            ps.setString(10, p.getEstadisticas());
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
    public <U> void delete(U ref) {
        String sql = "DELETE FROM pokemon WHERE N_Pokedex = ?";
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

    public Pokemon getPokemon(ResultSet rs) throws SQLException {
        Pokemon p = new Pokemon();
        p.setnPokedex(rs.getInt("N_Pokedex"));
        p.setEspecie(rs.getString("Especie").trim());
        p.setDenominacion(rs.getString("Denominacion"));
        p.setDescripcion(rs.getString("Descripcion"));
        p.setSprite(rs.getString("Sprite"));
        p.setTipo1(rs.getString("Tipo_1"));
        p.setTipo2(rs.getString("Tipo_2"));
        p.setTamaño(rs.getDouble("Tamaño"));
        p.setPeso(rs.getDouble("Peso"));
        p.setHabilidades(rs.getString("Habilidades"));
        p.setEstadisticas(rs.getString("Estadisticas"));
        return p;
    }

    public ObservableList<Pokemon> getTodos(String filter) {
        ObservableList<Pokemon> lista = FXCollections.observableArrayList();
        Pokemon p;
        String sql = "SELECT * FROM pokemon";

        if (!filter.isEmpty()) {
            sql = "SELECT * FROM pokemon " + filter;
        }

        ResultSet rs;
        try (PreparedStatement ps = conexion.getConexion().prepareStatement(sql)) {
            rs = ps.executeQuery();
            while (rs.next()) {
                p = getPokemon(rs);
                lista.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PokemonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
