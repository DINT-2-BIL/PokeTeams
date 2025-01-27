/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.fcm.pokeTeams.modelos;

import java.util.ArrayList;

/**
 *
 * @author DFran49
 */
public enum Tipos {
    NINGUNO ("Ninguno"),
    NORMAL ("Normal"),
    FUEGO ("Fuego"),
    AGUA ("Agua"),
    PLANTA ("Planta"),
    ELECTRICO ("Eléctrico"),
    HIELO ("Hielo"),
    LUCHA ("Lucha"),
    VENENO ("Veneno"),
    TIERRA ("Tierra"),
    VOLADOR ("Volador"),
    PSIQUICO ("Psíquico"),
    BICHO ("Bicho"),
    ROCA ("Roca"),
    FANTASMA ("Fantasma"),
    DRAGON ("Dragón"),
    SINIESTRO ("Siniestro"),
    ACERO ("Acero"),
    HADA ("Hada");
    
    private final String tipo;

    // Constructor
    Tipos(String nombre) {
        this.tipo = nombre;
    }

    public String getTipo() {
        return tipo;
    }
    
    public static ArrayList<String> listaTipo1() {
        ArrayList<String> lista = listaTipo2();
        lista.remove("Ninguno");
        return lista;
    }
    
    public static ArrayList<String> listaTipo2() {
        ArrayList<String> lista = new ArrayList<>();
        for (Tipos tipo : Tipos.values()) {
            lista.add(tipo.getTipo());
        }
        return lista;
    }
}
