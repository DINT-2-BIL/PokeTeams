/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DFran49
 */
public class Conexion extends Object {

    private static final Conexion instance = new Conexion();
    private static Connection conexion;

    public static Conexion getInstance() {
        return instance;
    }

    private Conexion() {
        generarConexion();
    }

    private void generarConexion() {
        DbConnection bbdd = new DbConnection();
        this.conexion = bbdd.getConnection();
    }

    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                generarConexion();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }
}
