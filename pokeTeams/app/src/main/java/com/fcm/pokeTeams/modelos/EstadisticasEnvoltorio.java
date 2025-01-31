/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DFran49
 */
public class EstadisticasEnvoltorio {

    List<Stat> stats;

    public EstadisticasEnvoltorio(List<Stat> s) {
        stats = s;
    }

    public List<Stat> getEstadisticas() {
        return stats;
    }

    public Stat getEstadistica(int i) {
        return stats.get(i);
    }
}
