package com.example.persistencia;

import com.example.modelo.Residencia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonRepository {

    private static final String ARCHIVO = "src/main/resources/residencia.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Lee el JSON y lo convierte en un objeto Residencia
    public Residencia cargar() {
        try {
            FileReader lector = new FileReader(ARCHIVO);
            Residencia residencia = gson.fromJson(lector, Residencia.class);
            lector.close();
            return residencia;
        } catch (IOException e) {
            // Si el archivo no existe, devuelve una residencia vacía
            System.out.println("No se encontró el archivo. Se crea una residencia nueva.");
            return new Residencia("Residencia Las Acacias", "Calle Mayor 12", "2025-2026");
        }
    }

    // Convierte el objeto Residencia en JSON y lo guarda en el archivo
    public void guardar(Residencia residencia) {
        try {
            FileWriter escritor = new FileWriter(ARCHIVO);
            gson.toJson(residencia, escritor);
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }
}
