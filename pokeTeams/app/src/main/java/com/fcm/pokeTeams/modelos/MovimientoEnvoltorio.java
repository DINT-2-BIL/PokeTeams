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
public class MovimientoEnvoltorio {
    private List<Movimiento> movimientos;

    public MovimientoEnvoltorio(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }
    
    public boolean vacia() {
        return movimientos.isEmpty();
    }
    
    public Movimiento getMovimiento(int i) {
                return  movimientos.get(i);
    }
    
    public int getSize() {
        return movimientos.size();
    }
}
