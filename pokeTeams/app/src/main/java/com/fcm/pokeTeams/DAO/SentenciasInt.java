/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fcm.pokeTeams.DAO;

/**
 *
 * @author Francisco
 */
public interface SentenciasInt<T> {

    void update(T elem);

    void insert(T elem);

    <U> void delete(U elem);
}
