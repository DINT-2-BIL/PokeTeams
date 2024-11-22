 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.DAO;
import com.fcm.pokeTeams.modelos.Pokemon;
import com.fcm.pokeTeams.util.DbConnection;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DFran49
 */
public class PokemonDAO extends BaseDAO implements crudInt<Pokemon>{
    public PokemonDAO() {}
    
    /*private select() {
        
    }*/

    private Pokemon getPokemon(ResultSet rs) throws SQLException {
        Pokemon p = new Pokemon();
        p.setnPokedex(rs.getString("N_Pokedex"));
        p.setEspecie(rs.getString("Especie").trim());
        p.setDenominacion(rs.getString("Denominacion"));
        p.setDescripcion(rs.getString("Descripcion"));
        p.setSprite(rs.getString("Sprite"));
        p.setTipo1(rs.getString("Tipo_1"));
        p.setTipo2(getStringOrNull(rs, "Tipo_2"));
        p.setTamaño(rs.getDouble("Tamaño"));
        p.setPeso(rs.getDouble("Peso"));
        p.setHabilidades(rs.getString("Habilidades"));
        p.setEstadisticas(rs.getString("Estadisticas"));
        return p;
    }
    
    @Override
    public List<Pokemon> getAll() throws SQLException, IOException {
        List<Pokemon> lista = new ArrayList<>();
        Pokemon p;
        String sql = "SELECT * FROM conferencia";

        /*if (!filter.isEmpty())
            sql = "SELECT * FROM conferencia WHERE referencia LIKE ? OR tema LIKE ? OR sala LIKE ?";*/

        ResultSet rs;
        try (PreparedStatement ps = DbConnection.getConnection().prepareStatement(sql)) {
            /*if (!filter.isEmpty()) {
                for (int i = 1; i <= 3; i++)
                    ps.setString(i, "%" + filter + "%");
            }*/
            rs = ps.executeQuery();
            while (rs.next()) {
                p = getPokemon(rs);
                System.out.println(p);
                lista.add(p);
            }
        }

        return lista;
    }

    @Override
    public Pokemon findByCodigo(String ref) throws SQLException, IOException {
        return null;
    }
    
}
