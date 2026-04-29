package com.example.modelo;


import java.util.ArrayList;
import java.util.List;

public class Casa {

    public static final int NUM_HABITACIONES = 6;

    private String nombre;                 // Seguira la nomenclatura "WG1", "WG2"...
    // Cada casa tiene lista de 6 habitaciones, que pueden estar ocupadas o libres
    private List<Habitacion> habitaciones;
//constructor vacio para que Gson pueda crear objetos
    public Casa() {
    }
//constructor con nombre para crear casas nuevas, que ya nacen con sus 6 habitaciones vacías
    public Casa(String nombre) {
        this.nombre = nombre;
        this.habitaciones = new ArrayList<>();
        // Al crear una casa nueva ya nacen sus 6 habitaciones vacías
        for (int i = 1; i <= NUM_HABITACIONES; i++) {
            habitaciones.add(new Habitacion(i));
        }
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Habitacion> getHabitaciones() { return habitaciones; }
    public void setHabitaciones(List<Habitacion> habitaciones) { this.habitaciones = habitaciones; }

    // Métodos de conveniencia para buscar habitación por número y contar ocupadas
    public Habitacion getHabitacion(int numero) {
        for (Habitacion h : habitaciones) {
            if (h.getNumero() == numero) {
                return h;
            }
        }
        return null;
    }
// Cuenta cuántas habitaciones están ocupadas
    public int contarOcupadas() {
        int n = 0;
        for (Habitacion h : habitaciones) {
            if (!h.estaLibre()) n++;
        }
        return n;
    }

    @Override
    public String toString() {
        return nombre + " (" + contarOcupadas() + "/" + NUM_HABITACIONES + " ocupadas)";
    }
}

