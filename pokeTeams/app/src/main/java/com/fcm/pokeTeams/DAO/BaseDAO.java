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
    public boolean existeRegistro(String nombre, String tabla, String campo) throws SQLException, IOException {
        int conteo;
        String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE " + campo + " = ?";

        try (PreparedStatement ps = DbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            rs.next();
            conteo = rs.getInt(1);
        }

        return conteo>0;
    }

    public String getStringOrNull(ResultSet rs, String column) throws SQLException {
        String value = rs.getString(column);
        return (value != null) ? value.trim() : null;
    }
}
