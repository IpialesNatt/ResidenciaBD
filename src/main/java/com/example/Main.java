package com.example;

import com.example.modelo.Casa;
import com.example.modelo.Persona;
import com.example.modelo.Residencia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) {
        Residencia residencia = new Residencia(
                "Residencia Las Acacias",
                "Calle Mayor 12",
                "2025-2026"
        );

        Casa wg1 = new Casa("WG1");
        wg1.getHabitacion(1).setPersona(
                new Persona("Lucía", "García Pérez", "12345678A",
                        "612345678", "lucia@email.com", "Grado en Informática")
        );
        residencia.getCasas().add(wg1);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(residencia));
    }
}

