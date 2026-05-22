package com.example.persistencia;

import com.example.modelo.Residencia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Esta clase se encarga de leer y escribir el archivo JSON que contiene los datos de la residencia
// Utiliza la biblioteca Gson para convertir entre objetos Java y JSON

public class JsonRepository {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

// Este método guarda los datos en una carpeta segura fuera del proyecto.
private File obtenerArchivo() {
    // Se crea la ruta: /home/tu_usuario/.residencia (Linux/Mac) o C:\Users\TuUsuario\.residencia (Windows)
    String userHome = System.getProperty("user.home");
    File dir = new File(userHome, "Documents");
    if (!dir.exists()) {
        dir.mkdirs(); // Crea la carpeta si no existe
    }
    // El archivo se llamará 'residencia.json' dentro de esa carpeta
    return new File(dir, "residencia.json");
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
            Residencia residencia = new Residencia("Residencia Die Brücke", "Universität Straße 19", "2025-2026");
            guardar(residencia); // Guarda la residencia nueva para que el archivo exista la próxima vez
            return residencia;
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
