/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.fcm.pokeTeams.enums;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Francisco
 */
public enum Naturalezas {
    FUERTE("Fuerte", Map.of("Hp", 1.0, "Atk", 1.0, "Def", 1.0, "SpA", 1.0, "SpD", 1.0, "Spe", 1.0)),
    OSADA("Osada", Map.of("Hp", 1.0, "Atk", 0.9, "Def", 1.1, "SpA", 1.0, "SpD", 1.0, "Spe", 1.0)),
    MODESTA("Modesta", Map.of("Hp", 1.0, "Atk", 0.9, "Def", 1.0, "SpA", 1.1, "SpD", 1.0, "Spe", 1.0)),
    SERENA("Serena", Map.of("Hp", 1.0, "Atk", 0.9, "Def", 1.0, "SpA", 1.0, "SpD", 1.1, "Spe", 1.0)),
    MIEDOSA("Miedosa", Map.of("Hp", 1.0, "Atk", 0.9, "Def", 1.0, "SpA", 1.0, "SpD", 1.0, "Spe", 1.1)),
    HURAÑA("Huraña", Map.of("Hp", 1.0, "Atk", 1.1, "Def", 0.9, "SpA", 1.0, "SpD", 1.0, "Spe", 1.0)),
    DOCIL("Dócil", Map.of("Hp", 1.0, "Atk", 1.0, "Def", 1.0, "SpA", 1.0, "SpD", 1.0, "Spe", 1.0)),
    AFABLE("Afable", Map.of("Hp", 1.0, "Atk", 1.0, "Def", 0.9, "SpA", 1.1, "SpD", 1.0, "Spe", 1.0)),
    AMABLE("Amable", Map.of("Hp", 1.0, "Atk", 1.0, "Def", 0.9, "SpA", 1.0, "SpD", 1.1, "Spe", 1.0)),
    ACTIVA("Activa", Map.of("Hp", 1.0, "Atk", 1.0, "Def", 0.9, "SpA", 1.0, "SpD", 1.0, "Spe", 1.1)),
    FIRME("Firme", Map.of("Hp", 1.0, "Atk", 1.1, "Def", 1.0, "SpA", 0.9, "SpD", 1.0, "Spe", 1.0)),
    AGITADA("Agitada", Map.of("Hp", 1.0, "Atk", 1.0, "Def", 1.1, "SpA", 0.9, "SpD", 1.0, "Spe", 1.0)),
    TIMIDA("Tímida", Map.of("Hp", 1.0, "Atk", 1.0, "Def", 1.0, "SpA", 1.0, "SpD", 1.0, "Spe", 1.0)),
    CAUTA("Cauta", Map.of("Hp", 1.0, "Atk", 1.0, "Def", 1.0, "SpA", 0.9, "SpD", 1.1, "Spe", 1.0)),
    ALEGRE("Alegre", Map.of("Hp", 1.0, "Atk", 1.0, "Def", 1.0, "SpA", 0.9, "SpD", 1.0, "Spe", 1.1)),
    PICARA("Pícara", Map.of("Hp", 1.0, "Atk", 1.1, "Def", 1.0, "SpA", 1.0, "SpD", 0.9, "Spe", 1.0)),
    FLOJA("Floja", Map.of("Hp", 1.0, "Atk", 1.0, "Def", 1.1, "SpA", 1.0, "SpD", 0.9, "Spe", 1.0)),
    ALOCADA("Alocada", Map.of("Hp", 1.0, "Atk", 1.0, "Def", 1.0, "SpA", 1.1, "SpD", 0.9, "Spe", 1.0)),
    RARA("Rara", Map.of("Hp", 1.0, "Atk", 1.0, "Def", 1.0, "SpA", 1.0, "SpD", 1.0, "Spe", 1.0)),
    INGENUA("Ingenua", Map.of("Hp", 1.0, "Atk", 1.0, "Def", 1.0, "SpA", 1.0, "SpD", 0.9, "Spe", 1.1)),
    AUDAZ("Audaz", Map.of("Hp", 1.0, "Atk", 1.1, "Def", 1.0, "SpA", 1.0, "SpD", 1.0, "Spe", 0.9)),
    PLACIDA("Plácida", Map.of("Hp", 1.0, "Atk", 1.0, "Def", 1.1, "SpA", 1.0, "SpD", 1.0, "Spe", 0.9)),
    MANSA("Mansa", Map.of("Hp", 1.0, "Atk", 1.0, "Def", 1.0, "SpA", 1.1, "SpD", 1.0, "Spe", 0.9)),
    GROSERA("Grosera", Map.of("Hp", 1.0, "Atk", 1.0, "Def", 1.0, "SpA", 1.0, "SpD", 1.1, "Spe", 0.9)),
    SERIA("Seria", Map.of("Hp", 1.0, "Atk", 1.0, "Def", 1.0, "SpA", 1.0, "SpD", 1.0, "Spe", 1.0));

    private final String nombre;
    private final Map<String, Double> valores;

    private Naturalezas(String nombre, Map<String, Double> valores) {
        this.nombre = nombre;
        this.valores = valores;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Map<String, Double> getValores() {
        return this.valores;
    }

    public static Naturalezas fromName(String nombre) {
        for (Naturalezas naturaleza : Naturalezas.values()) {
            if (naturaleza.getNombre().equals(nombre)) {
                return naturaleza;
            }
        }
        throw new IllegalArgumentException("No enum encontrado para la naturaleza: " + nombre);
    }

    public static ArrayList<String> getNaturalezas() {
        ArrayList<String> naturalezas = new ArrayList<>();
        for (Naturalezas naturaleza : Naturalezas.values()) {
            naturalezas.add(naturaleza.getNombre());
        }
        return naturalezas;
    }
}
