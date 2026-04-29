package com.example.modelo;

public class Habitacion {

    private int numero;       // 1 a 6
    private Persona persona;  // null si está libre

    public Habitacion() {
    }

    public Habitacion(int numero) {
        this.numero = numero;
        this.persona = null;
    }

    public Habitacion(int numero, Persona persona) {
        this.numero = numero;
        this.persona = persona;
    }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public Persona getPersona() { return persona; }
    public void setPersona(Persona persona) { this.persona = persona; }

    // Métodos de conveniencia
    public boolean estaLibre() {
        return persona == null;
    }

    public void liberar() {
        this.persona = null;
    }

    @Override
    public String toString() {
        return "Habitación " + numero + " - " +
                (estaLibre() ? "[libre]" : persona.toString());
    }
}

