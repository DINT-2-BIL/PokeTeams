/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.modelos;

import com.google.gson.annotations.SerializedName;
import retrofit2.http.Query;

/**
 *
 * @author DFran49
 */
public class Pokemon {
    @SerializedName ("N_Pokedex")
    private int nPokedex;
    @SerializedName ("Especie")
    private String especie;
    @SerializedName ("Denominacion")
    private String denominacion;
    @SerializedName ("Descripcion")
    private String descripcion;
    @SerializedName ("Sprite")
    private String sprite;
    @SerializedName ("Tipo_1")
    private String tipo1;
    @SerializedName ("Tipo_2")
    private String tipo2;
    @SerializedName ("Tamaño")
    private double tamaño;
    @SerializedName ("Peso")
    private double peso;
    @SerializedName ("Habilidades")
    private String habilidades;
    @SerializedName ("Estadisticas")
    private String estadisticas;

    public Pokemon() {
    }

    public String getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(String estadisticas) {
        this.estadisticas = estadisticas;
    }

    public int getnPokedex() {
        return nPokedex;
    }

    public void setnPokedex(int nPokedex) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pokemon{");
        sb.append("nPokedex=").append(nPokedex);
        sb.append(", especie=").append(especie);
        sb.append(", denominacion=").append(denominacion);
        sb.append(", descripcion=").append(descripcion);
        sb.append(", sprite=").append(sprite);
        sb.append(", tipo1=").append(tipo1);
        sb.append(", tipo2=").append(tipo2);
        sb.append(", tama\u00f1o=").append(tamaño);
        sb.append(", peso=").append(peso);
        sb.append(", habilidades=").append(habilidades);
        sb.append(", estadisticas=").append(estadisticas);
        sb.append('}');
        return sb.toString();
    }

    
}
