/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fcm.pokeTeams.DAO;

import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Francisco
 */
public interface SentenciasInt<T> {
    void update(T elem) throws SQLException, IOException;
    void insert(T elem) throws SQLException, IOException;
    void delete(String ref) throws SQLException, IOException;
    ObservableList<T> getTodos(String filter) throws SQLException, IOException;
    T getElemento() throws SQLException, IOException;
    T findByCodigo(String ref) throws SQLException, IOException;
}
