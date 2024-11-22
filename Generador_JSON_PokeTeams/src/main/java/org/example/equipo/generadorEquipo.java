package org.example.equipo;

import java.util.Random;
import java.util.Scanner;

public class generadorEquipo {
    public static void main(String[] args) {
        System.out.println("Introduzca el pokemon que tendrá las habilidades");
        String pokemon = new Scanner(System.in).nextLine();
        int nMovimientos = new Random().nextInt(1,4);
        if (nMovimientos > 0 || nMovimientos < 5) {
            generadorMovimientos.generarMovimientos(pokemon,nMovimientos);
        }
        System.out.println("Introduzca los EVs");
        generadorEVs.generarEVs(pokemon);
        System.out.println("\nIntroduzca los IVs");
        generadorIVs.generarIVs(pokemon);
    }
}
