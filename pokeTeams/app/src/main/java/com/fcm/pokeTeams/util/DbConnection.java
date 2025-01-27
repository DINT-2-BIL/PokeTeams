/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DFran49
 */
public class DbConnection {
    private String URL;
    private String USER;
    private String PASSWORD; 
    private InputStream input = getClass().getClassLoader().getResourceAsStream("bbdd.properties");

// Constructor privado para evitar instancias 
    public DbConnection() {
        inicializar();
    }
    
    private void inicializar() {
        FileInputStream fileIn = null;
        try {
            Properties properties = new Properties();
            properties.load(input);
            // Mostrar propiedades recuperadas
            URL = "jdbc:mariadb://" + properties.getProperty("SERVER") + ":" + properties.getProperty("PORT") + "/" + properties.getProperty("BBDD");
            USER = properties.getProperty("USER");
            PASSWORD = properties.getProperty("PWD");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Connection getConnection() throws SQLException, FileNotFoundException, IOException {
    // Obtener conexión a la BD 
    return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static void closeConnection(Connection connection) throws SQLException {
// Cerrar conexión
    if (connection != null) connection.close();
    }
}
