/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.DAO;

import com.fcm.pokeTeams.API.PokemonDeleteInt;
import com.fcm.pokeTeams.API.PokemonInsertInt;
import com.fcm.pokeTeams.API.PokemonSelectInt;
import com.fcm.pokeTeams.API.PokemonUpdateInt;
import com.fcm.pokeTeams.modelos.ListaPokemon;
import com.fcm.pokeTeams.modelos.Pokemon;
import com.fcm.pokeTeams.modelos.PokemonEliminar;
import com.fcm.pokeTeams.util.CargadorFXML;
import com.fcm.pokeTeams.util.Conexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author DFran49
 */
public class PokemonDAO implements SentenciasInt<Pokemon> {

    private static final PokemonDAO instance = new PokemonDAO();
    private Conexion conexion = Conexion.getInstance();
    public Pokemon[] lp;
    private PokemonDAO() {
    }

    public static PokemonDAO getInstance() {
        return instance;
    }
    
    String baseURL = "http://18.233.170.12/APIRest_pokeTeams/crud/";
    Gson gson = new GsonBuilder().setLenient().create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    PokemonSelectInt servicioLeer = retrofit.create(PokemonSelectInt.class);
    PokemonInsertInt servicioInsertar = retrofit.create(PokemonInsertInt.class);
    PokemonUpdateInt servicioActualizar = retrofit.create(PokemonUpdateInt.class);
    PokemonDeleteInt servicioBorrar = retrofit.create(PokemonDeleteInt.class);
    
    public Call<Pokemon[]> callSel;
    private Call<Pokemon> callIns;
    private Call<Pokemon> callUpd;
    private Call<Pokemon> callDel;

    @Override
    public void update(Pokemon p) {
        System.out.println(p);
        callUpd = servicioActualizar.actualizarPokemon(p);
        System.out.println(p.getnPokedex());
        this.encolaUpd();
    }
    
    public void encolaUpd() {
        callUpd.enqueue(new Callback<Pokemon>() {
            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                System.out.println("Network Error :: " + t.getLocalizedMessage());
            }
            
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                Platform.runLater(() -> {
                    System.out.println("Respuesta ACTUALIZAR: " + response.message());
                    if (response.isSuccessful()) {
                        System.out.println("Actualización correcta.");
                    } else {
                        System.out.println("Actualización no realizada.");
                    }
                });
            }
        });
    }

    @Override
    public void insert(Pokemon p) {
        System.out.println(p);
        callIns = servicioInsertar.insertarPokemon(p);
        this.encolaIns();
    }
    
    public void encolaIns() {
        callIns.enqueue(new Callback<Pokemon>() {
            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                System.out.println("Network Error :: " + t.getLocalizedMessage());
            }
            
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                Platform.runLater(() -> {
                    System.out.println("Respuesta INSERTAR: " + response.message());
                    if (response.isSuccessful()) {
                        System.out.println("Inserción correcta.");
                    } else {
                        System.out.println("Inserción no realizada.");
                    }
                });
            }
        });
    }

    @Override
    public <U> void delete(U ref) {
        System.out.println((Pokemon) ref);
        callDel = servicioBorrar.borrarPokemon((Pokemon) ref);
        this.encolaDel();
    }
    
    public void encolaDel() {
        callDel.enqueue(new Callback<Pokemon>() {
            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                System.out.println("Network Error :: " + t.getLocalizedMessage());
            }
            
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                Platform.runLater(() -> {
                    System.out.println("Respuesta ELIMINAR: " + response.message());
                    if (response.isSuccessful()) {
                        System.out.println("Eliminación correcta.");
                    } else {
                        System.out.println("Eliminación no realizada.");
                    }
                });
            }
        });
    }

    public Pokemon getPokemon(ResultSet rs) throws SQLException {
        Pokemon p = new Pokemon();
        p.setnPokedex(rs.getInt("N_Pokedex"));
        p.setEspecie(rs.getString("Especie").trim());
        p.setDenominacion(rs.getString("Denominacion"));
        p.setDescripcion(rs.getString("Descripcion"));
        p.setSprite(rs.getString("Sprite"));
        p.setTipo1(rs.getString("Tipo_1"));
        p.setTipo2(rs.getString("Tipo_2"));
        p.setTamaño(rs.getDouble("Tamaño"));
        p.setPeso(rs.getDouble("Peso"));
        p.setHabilidades(rs.getString("Habilidades"));
        p.setEstadisticas(rs.getString("Estadisticas"));
        return p;
    }

    public void getTodos(String filter) {
        callSel = servicioLeer.getPokemon(-1);
        this.encolaSel();
        //ObservableList<Pokemon> ol = FXCollections.observableArrayList(lp);
        //return ol;
    }
    
    public void encolaSel() {
        callSel.enqueue(new Callback<Pokemon[]>() {
            @Override
            public void onFailure(Call<Pokemon[]> call, Throwable t) {
                System.out.println("Network Error :: " + t.getLocalizedMessage());
            }
            
            @Override
            public void onResponse(Call<Pokemon[]> call, Response<Pokemon[]> response) {
                Platform.runLater(() -> {
                    System.out.println("Respuesta LEER: " + response.message());
                    if (response.isSuccessful()) {
                        System.out.println("Selección correcta.");
                        lp = response.body();
                        for (Pokemon p : lp) {
                            System.out.println(p.toString());
                        }
                    } else {
                        System.out.println("Selección no realizada.");
                    }
                });
            }
        });
    }
}
