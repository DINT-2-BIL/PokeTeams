/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.modelos;

/**
 *
 * @author DFran49
 */
public class Pokemon {
    private String nPokedex;
    private String especie;
    private String denominacion;
    private String descripcion;
    private String sprite;
    private String tipo1;
    private String tipo2;
    private double tamaño;
    private double peso;
    private String habilidades;
    private String estadisticas;

    public Pokemon() {
    }
    
    public String getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(String estadisticas) {
        this.estadisticas = estadisticas;
    }

    public String getnPokedex() {
        return nPokedex;
    }

    public void setnPokedex(String nPokedex) {
        this.nPokedex = nPokedex;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public String getTipo1() {
        return tipo1;
    }

    public void setTipo1(String tipo1) {
        this.tipo1 = tipo1;
    }

    public String getTipo2() {
        return tipo2;
    }

    public void setTipo2(String tipo2) {
        this.tipo2 = tipo2;
    }

    public double getTamaño() {
        return tamaño;
    }

    public void setTamaño(double tamaño) {
        this.tamaño = tamaño;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }

    
}
