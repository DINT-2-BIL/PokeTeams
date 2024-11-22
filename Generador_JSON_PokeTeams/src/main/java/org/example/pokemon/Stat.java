package org.example.pokemon;

public class Stat {
    String estadistica;
    int valor;

    public Stat(String estadistica, int valor) {
        this.estadistica = estadistica;
        this.valor = valor;
    }

    public String getEstadistica() {
        return estadistica;
    }

    public int getValor() {
        return valor;
    }
}
