package com.example.persistencia;

import com.example.modelo.Residencia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class JsonRepository {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Busca el archivo JSON usando el classpath (funciona siempre en Maven/IntelliJ)
    private File obtenerArchivo() {
        URL url = getClass().getClassLoader().getResource("residencia.json");
        if (url != null) {
            try {
                return new File(url.toURI());
            } catch (Exception e) {
                System.out.println("Error al resolver la ruta: " + e.getMessage());
            }
        }
        // Si no lo encuentra por classpath, usa la ruta relativa como respaldo
        return new File("src/main/resources/residencia.json");
    }

    // Lee el JSON y lo convierte en un objeto Residencia
    public Residencia cargar() {
        try {
            FileReader lector = new FileReader(obtenerArchivo());
            Residencia residencia = gson.fromJson(lector, Residencia.class);
            lector.close();
            return residencia;
        } catch (IOException e) {
            System.out.println("No se encontró el archivo. Se crea una residencia nueva.");
            return new Residencia("Residencia Las Acacias", "Calle Mayor 12", "2025-2026");
        }
    }

    // Convierte el objeto Residencia en JSON y lo guarda en el archivo
    public void guardar(Residencia residencia) {
        try {
            FileWriter escritor = new FileWriter(obtenerArchivo());
            gson.toJson(residencia, escritor);
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }
}
