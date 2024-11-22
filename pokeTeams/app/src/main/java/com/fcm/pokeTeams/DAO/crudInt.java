/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.DAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author DFran49
 */
public interface crudInt<T> {
    List<T> getAll() throws SQLException, IOException;
    T findByCodigo(String ref) throws SQLException, IOException;
}
