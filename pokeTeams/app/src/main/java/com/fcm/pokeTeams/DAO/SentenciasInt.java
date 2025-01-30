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
    void update(T elem);
    void insert(T elem);
    <U> void delete(U elem);
}
