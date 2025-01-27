/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.DAO;

import com.fcm.pokeTeams.util.DbConnection;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author DFran49
 */
public abstract class BaseDAO {
    public boolean existeRegistro(String columna, String tabla, String campo) throws SQLException, IOException {
        int conteo = 0;
        String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE " + campo + " = ?";

        /*try (PreparedStatement ps = DbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, columna);
            ResultSet rs = ps.executeQuery();
            rs.next();
            conteo = rs.getInt(1);
        }*/

        return conteo>0;
    }

    public String getStringOrNull(ResultSet rs, String columna) throws SQLException {
        String value = rs.getString(columna);
        return (value != null) ? value.trim() : null;
    }
}
