/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author DFran49
 */
public class Conexion extends Object {
    static Connection conexion;

    public Conexion() {
        DbConnection bbdd = new DbConnection();
        try {
            this.conexion = bbdd.getConnection();
        } catch (SQLException ex) {
            new Alertas(Alert.AlertType.WARNING, "Base de Datos no disponible", "No se ha podido conectar a la Base e Datos", "Encienda la base de datos").mostrarAlerta();
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Connection getConexion() {
        return conexion;
    }
}
