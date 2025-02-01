package org.example.pokemon;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class generadorStats {
    private List<Stat> stats = new ArrayList<>();
    public List<Stat> getStats() { return stats; }
    public static void generarStats(String pokemon) {
        generadorStats listaStats = new generadorStats();
        int stat = 0;
        String[] nStats = new String[] {"HP","Atk","Def","SpA","SpD","Spe"};

        for (int i = 0; i < 6; i++) {
            System.out.println("Introduzca el valor para la stat: " + nStats[i]);
            stat = new Scanner(System.in).nextInt();
            Stat temp = new Stat(nStats[i], stat);
            listaStats.getStats().add(temp);
        }


        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("Pokemon/stats" + pokemon + ".json")) {
            gson.toJson(listaStats, writer);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        try (FileReader reader = new FileReader("Pokemon/stats" + pokemon + ".json")) {
            generadorStats listaStatsLeidas = gson.fromJson(reader, generadorStats.class);
            listaStatsLeidas.getStats().forEach(p -> System.out.println(p.getEstadistica() +
                    " - " + p.getValor()));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
