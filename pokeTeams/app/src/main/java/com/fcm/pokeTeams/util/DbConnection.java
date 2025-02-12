/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.util;

import java.io.File;
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
    private String rutaIp = "ip.properties";

    public DbConnection() {
        inicializar();
    }

    private void inicializar() {
        File ficheroIp = new File(rutaIp);
        Properties ip = new Properties();

        if (!ficheroIp.exists()) {
            try {
                ficheroIp.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (FileOutputStream escribir = new FileOutputStream(ficheroIp)) {
                ip.setProperty("SERVER", "localhost");
                ip.setProperty("USER", "root");
                ip.setProperty("PWD", "root");
                ip.store(escribir, "Configuración predeterminada de la IP");
                System.out.println("Archivo de configuración de IP creado con valores predeterminados.");
                escribir.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        try (FileInputStream leer = new FileInputStream(ficheroIp)) {
            ip.load(leer);
        } catch (IOException e) {
            System.out.println("Error al manejar el archivo de configuración de IP: " + e.getMessage());
        }

        FileInputStream fileIn = null;
        try {
            Properties properties = new Properties();
            properties.load(input);
            // Mostrar propiedades recuperadas
            URL = "jdbc:mariadb://" + ip.getProperty("SERVER") + ":" + properties.getProperty("PORT") + "/" + properties.getProperty("BBDD");
            USER = ip.getProperty("USER");
            PASSWORD = ip.getProperty("PWD");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        try {
            // Obtener conexión a la BD
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void closeConnection(Connection connection) throws SQLException {
// Cerrar conexión
        if (connection != null) {
            connection.close();
        }
    }
}
