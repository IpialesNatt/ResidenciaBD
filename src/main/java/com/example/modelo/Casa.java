package com.example.modelo;
import java.util.ArrayList;
import java.util.List;

public class Casa {

    public static final int NUM_HABITACIONES = 6;

    private String nombre; // Seguira la nomenclatura "WG1", "WG2"...
    private List<Habitacion> habitaciones; // Cada casa tiene una lista de 6 habitaciones

    //constructor vacio para que Gson pueda crear objetos
    public Casa() {
    }
    
    //constructor con nombre para crear casas nuevas, que ya nacen con sus 6 habitaciones vacías
    public Casa(String nombre) {
        this.nombre = nombre;
        this.habitaciones = new ArrayList<>();
        // Al crear una casa nueva ya nacen sus 6 habitaciones vacías
        for (int i = 1; i <= NUM_HABITACIONES; i++) { // recorre del 1 al 6 para crear las habitaciones numeradas del 1 al 6
            habitaciones.add(new Habitacion(i)); // cada habitación se crea con su número correspondiente 
        }
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Habitacion> getHabitaciones() { return habitaciones; }
    public void setHabitaciones(List<Habitacion> habitaciones) { this.habitaciones = habitaciones; }

    // Métodos de conveniencia para buscar habitación por número y contar ocupadas
    public Habitacion getHabitacion(int numero) {
        for (Habitacion h : habitaciones) { //recorre la lista de habitaciones de la casa
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

