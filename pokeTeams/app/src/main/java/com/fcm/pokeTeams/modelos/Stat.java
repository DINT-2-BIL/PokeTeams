/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.modelos;

/**
 *
 * @author DFran49
 */
public class Stat {

    String estadistica;
    int valor;

    public Stat(String estadistica, int valor) {
        this.estadistica = estadistica;
        this.valor = valor;
    }

    public String getEstadistica() {
        return estadistica;
    }

    public int getValor() {
        return valor;
    }

}
