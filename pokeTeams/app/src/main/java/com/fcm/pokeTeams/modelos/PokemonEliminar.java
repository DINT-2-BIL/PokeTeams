/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.modelos;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Francisco
 */
public class PokemonEliminar {
    @SerializedName ("N_Pokedex")
    private int N_Pokedex;

    public PokemonEliminar(int nPokedex) {
        this.N_Pokedex = nPokedex;
    }

    public int getnPokedex() {
        return N_Pokedex;
    }

    public void setnPokedex(int nPokedex) {
        this.N_Pokedex = nPokedex;
    }
    
    
}
