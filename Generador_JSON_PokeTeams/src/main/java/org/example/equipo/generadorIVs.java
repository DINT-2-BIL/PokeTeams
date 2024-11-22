package org.example.equipo;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class generadorIVs {
    private List<Stat> ivs = new ArrayList<>();
    public List<Stat> getIVs() { return ivs; }
    public static void generarIVs(String pokemon) {
        generadorIVs listaIVs = new generadorIVs();
        int stat = 0;
        String[] nStats = new String[] {"HP","Atk","Def","SpA","SpD","Spe"};

        for (int i = 0; i < 6; i++) {
            Stat temp = new Stat(nStats[i], new Random().nextInt(0,31));
            listaIVs.getIVs().add(temp);
        }


        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("Equipo/ivs" + pokemon + ".json")) {
            gson.toJson(listaIVs, writer);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        try (FileReader reader = new FileReader("Equipo/ivs" + pokemon + ".json")) {
            generadorEVs listaStatsLeidas = gson.fromJson(reader, generadorEVs.class);
            listaStatsLeidas.getEVs().forEach(p -> System.out.println(p.getEstadistica() +
                    " - " + p.getValor()));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
