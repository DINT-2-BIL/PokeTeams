/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.modelos;

/**
 *
 * @author Francisco
 */
public class EV {
    private String estadistica;
    private int valor;

    public EV(String estadistica, int valor) {
        this.estadistica = estadistica;
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getEstadistica() {
        return estadistica;
    }

    public void setEstadistica(String estadistica) {
        this.estadistica = estadistica;
    }
    
    
}
