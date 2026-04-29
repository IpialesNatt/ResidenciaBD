package com.example.modelo;


import java.util.ArrayList;
import java.util.List;

public class Casa {

    public static final int NUM_HABITACIONES = 6;

    private String nombre;                 // "WG1", "WG2"...
    private List<Habitacion> habitaciones;

    public Casa() {
    }

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

    // Métodos de conveniencia
    public Habitacion getHabitacion(int numero) {
        for (Habitacion h : habitaciones) {
            if (h.getNumero() == numero) {
                return h;
            }
        }
        return null;
    }

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

