 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.DAO;
import com.fcm.pokeTeams.modelos.Pokemon;
import com.fcm.pokeTeams.util.Conexion;
import com.fcm.pokeTeams.util.DbConnection;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author DFran49
 */
public class PokemonDAO extends BaseDAO implements SentenciasInt<Pokemon> {
    private static final PokemonDAO instance = new PokemonDAO();
    private static int a = 0;

    private PokemonDAO() { }

    public static PokemonDAO getInstance() {
        System.out.println("a "+a);
        a++;
        return instance;
    }

    @Override
    public void update(Pokemon elem, Conexion conex) throws SQLException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Pokemon elem, Conexion conex) throws SQLException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <U> void delete(U ref, Conexion conex) {
        String sql = "DELETE FROM pokemon WHERE N_Pokedex = ?";
        System.out.println((int)ref);
        try (PreparedStatement ps = conex.getConexion().prepareStatement(sql)) {
            ps.setInt(1, (int) ref);
            ps.executeUpdate();
        } catch (Exception e) {
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

    @Override
    public ObservableList<Pokemon> getTodos(String filter, Conexion conex) throws SQLException, IOException {
        ObservableList<Pokemon> lista = FXCollections.observableArrayList();
        Pokemon p;
        String sql = "SELECT * FROM pokemon";

        if (!filter.isEmpty())
            sql = "SELECT * FROM pokemon " + filter;

        ResultSet rs;
        try (PreparedStatement ps = conex.getConexion().prepareStatement(sql)) {
            rs = ps.executeQuery();
            while (rs.next()) {
                p = getPokemon(rs);
                lista.add(p);
            }
        }
        return lista;
    }

    @Override
    public Pokemon findByCodigo(String ref, Conexion conex) throws SQLException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
