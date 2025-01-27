/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.fcm.pokeTeams.modelos;

/**
 *
 * @author Francisco
 */
public enum Generos {
    M ("Hombre", "Masculino", 'M'),
    F ("Mujer", "Femenino", 'F'),
    O ("Otro", "", 'O'),
    N ("", "Sin género",'N');
    
    private final String entrenador;
    private final String pokemon;
    private final char sigla;
    
    private Generos(String entrenador, String pokemon, char sigla) {
        this.entrenador = entrenador;
        this.pokemon = pokemon;
        this.sigla = sigla;
    }
    
    public String getEntrenador() {
        return this.entrenador;
    }
    
    public String getPokemon() {
        return this.pokemon;
    }
    
    public char getSigla() {
        return this.sigla;
    }
    
    public static Generos fromSigla(char sigla) {
        for (Generos genero : Generos.values()) {
            if(genero.getSigla() == sigla) {
                return genero;
            }
        }
        throw new IllegalArgumentException("No enum encontrado para el género: " + sigla);
    }
    
    public static char fromPokemon(String cadena) {
        for (Generos genero : Generos.values()) {
            if(genero.getPokemon() == cadena) {
                return genero.sigla;
            }
        }
        throw new IllegalArgumentException("No enum encontrado para el género: " + cadena);
    }
}
