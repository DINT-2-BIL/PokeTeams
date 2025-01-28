/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fcm.pokeTeams.DAO;

import com.fcm.pokeTeams.util.Conexion;
import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Francisco
 */
public interface SentenciasInt<T> {
    void update(T elem, Conexion conex) throws SQLException, IOException;
    void insert(T elem, Conexion conex) throws SQLException, IOException;
    <U> void delete(U elem, Conexion conex);
    ObservableList<T> getTodos(String filter, Conexion conex) throws SQLException, IOException;
    T findByCodigo(String ref, Conexion conex) throws SQLException, IOException;
}
