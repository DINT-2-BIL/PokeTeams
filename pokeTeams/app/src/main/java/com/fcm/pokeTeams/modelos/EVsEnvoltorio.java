/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.modelos;

import java.util.List;

/**
 *
 * @author Francisco
 */
public class EVsEnvoltorio {

    private List<EV> evs;

    public EVsEnvoltorio(List<EV> evs) {
        this.evs = evs;
    }

    public List<EV> getEvs() {
        return evs;
    }

    public EV getEV(int i) {
        return evs.get(i);
    }
}
