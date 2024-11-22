package org.example.pokemon;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class generadorHabilidades {
    private List<Habilidad> habilidades = new ArrayList<>();
    public List<Habilidad> getHabilidades() { return habilidades; }
    public static void generarHabilidades(String pokemon, int nHabilidades) {
        generadorHabilidades listaHabilidades = new generadorHabilidades();
        String nombre = "";
        String descripcion = "";

        for (int i = 1; i <= nHabilidades; i++) {
            System.out.println("Introduzca el nombre de la habilidad " + i);
            nombre = new Scanner(System.in).nextLine();
            System.out.println("Introduzca la descripción de la habilidad " + nombre);
            descripcion = new Scanner(System.in).nextLine();
            Habilidad temp = new Habilidad(nombre, descripcion);
            listaHabilidades.getHabilidades().add(temp);
        }

        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("Pokemon/habilidades" + pokemon + ".json")) {
            gson.toJson(listaHabilidades, writer);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        try (FileReader reader = new FileReader("Pokemon/habilidades" + pokemon + ".json")) {
            generadorHabilidades listaPersonasLeido = gson.fromJson(reader, generadorHabilidades.class);
            listaPersonasLeido.getHabilidades().forEach(p -> System.out.println(p.getNombre() +
                    " - " + p.getDescripcion()));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}