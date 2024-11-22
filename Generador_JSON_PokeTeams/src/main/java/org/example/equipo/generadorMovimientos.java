package org.example.equipo;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class generadorMovimientos {
    private List<Movimiento> movimientos = new ArrayList<>();
    public List<Movimiento> getMovimientos() { return movimientos; }
    public static void generarMovimientos(String pokemon, int nMovimientos) {
        generadorMovimientos listaMovimientos = new generadorMovimientos();
        String nombre = "";
        for (int i = 1; i <= nMovimientos; i++) {
            System.out.println("Introduzca el nombre del movimiento " + i);
            nombre = new Scanner(System.in).nextLine();
            Movimiento temp = new Movimiento(nombre);
            listaMovimientos.getMovimientos().add(temp);
        }

        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("Equipo/movimientos" + pokemon + ".json")) {
            gson.toJson(listaMovimientos, writer);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        try (FileReader reader = new FileReader("Equipo/movimientos" + pokemon + ".json")) {
            generadorMovimientos listaPersonasLeido = gson.fromJson(reader, generadorMovimientos.class);
            listaPersonasLeido.getMovimientos().forEach(p -> System.out.println(p.getNombre()));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}