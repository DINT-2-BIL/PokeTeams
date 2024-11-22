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
public class IVsEnvoltorio {
    private List<EV> ivs;

    public IVsEnvoltorio(List<EV> evs) {
        this.ivs = evs;
    }

    public List<EV> getEvs() {
        return ivs;
    }
    
    public EV getEV(int i) {
        return ivs.get(i);
    }
}
