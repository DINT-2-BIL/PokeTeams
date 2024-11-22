package org.example.pokemon;

import java.util.Scanner;

public class generadorPokemon {
    public static void main(String[] args) {
        System.out.println("Introduzca el pokemon que tendrá las habilidades");
        String pokemon = new Scanner(System.in).nextLine();
        System.out.println("Introduzca el número de habilidades");
        int nHabilidades = new Scanner(System.in).nextInt();
        if (nHabilidades > 0 || nHabilidades < 4) {
            generadorHabilidades.generarHabilidades(pokemon,nHabilidades);
        }
        generadorStats.generarStats(pokemon);
    }
}
