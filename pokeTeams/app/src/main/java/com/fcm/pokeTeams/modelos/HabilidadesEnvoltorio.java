/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.modelos;

import java.util.List;

/**
 *
 * @author DFran49
 */
public class HabilidadesEnvoltorio {

    List<Habilidad> habilidades;

    public HabilidadesEnvoltorio(List<Habilidad> habilidades) {
        this.habilidades = habilidades;
    }

    public List<Habilidad> getHabilidades() {
        return habilidades;
    }

    public boolean vacia() {
        if (habilidades.size() == 0) {
            return true;
        }
        return false;
    }

    public Habilidad siguienteHabilidad() {
        if (habilidades.size() != 0) {
            Habilidad temp = habilidades.get(0);
            habilidades.remove(0);
            return temp;
        }
        return null;
    }
}
