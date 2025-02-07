/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fcm.pokeTeams.API;

import com.fcm.pokeTeams.modelos.Pokemon;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 * @author Francisco
 */
public interface PokemonSelectInt {
    @GET("leer.php")
    Call<Pokemon> getPokemon(@Query("N_Pokedex") int n_pokedex);
    Call<Pokemon> getPokemon(@Query("Tipo_1") String tipo_1);
}
