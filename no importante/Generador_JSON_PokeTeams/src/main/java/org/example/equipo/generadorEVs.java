package org.example.equipo;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class generadorEVs {
    private List<Stat> evs = new ArrayList<>();
    public List<Stat> getEVs() { return evs; }
    public static void generarEVs(String pokemon) {
        org.example.equipo.generadorEVs listaEVs = new org.example.equipo.generadorEVs();
        int stat = 0;
        String[] nStats = new String[] {"HP","Atk","Def","SpA","SpD","Spe"};

        for (int i = 0; i < 6; i++) {
            Stat temp = new Stat(nStats[i], new Random().nextInt(0,255));
            listaEVs.getEVs().add(temp);
        }


        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("Equipo/evs" + pokemon + ".json")) {
            gson.toJson(listaEVs, writer);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        try (FileReader reader = new FileReader("Equipo/evs" + pokemon + ".json")) {
            generadorEVs listaStatsLeidas = gson.fromJson(reader, generadorEVs.class);
            listaStatsLeidas.getEVs().forEach(p -> System.out.println(p.getEstadistica() +
                    " - " + p.getValor()));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
