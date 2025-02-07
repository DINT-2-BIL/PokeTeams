/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fcm.pokeTeams.API;

import com.fcm.pokeTeams.modelos.Pokemon;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 *
 * @author Francisco
 */
public interface PokemonUpdateInt {
    @Headers({
        "Content-Type: application/json",
        "Accept: application/json"
    })
    @POST("actualizar.php")
    Call<Pokemon> actualizarPokemon(@Body Pokemon p);
}
