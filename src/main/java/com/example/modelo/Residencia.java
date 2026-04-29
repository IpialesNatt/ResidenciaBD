package com.example.modelo;

import java.util.ArrayList;
import java.util.List;

public class Residencia {

    public static final int MAX_CASAS = 5;

    private String nombre;
    private String direccion;
    private String cursoAcademico;
    private List<Casa> casas;

    public Residencia() {
        this.casas = new ArrayList<>();
    }

    public Residencia(String nombre, String direccion, String cursoAcademico) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.cursoAcademico = cursoAcademico;
        this.casas = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCursoAcademico() { return cursoAcademico; }
    public void setCursoAcademico(String cursoAcademico) { this.cursoAcademico = cursoAcademico; }

    public List<Casa> getCasas() { return casas; }
    public void setCasas(List<Casa> casas) { this.casas = casas; }

    // Métodos de conveniencia
    public Casa getCasa(String nombreCasa) {
        for (Casa c : casas) {
            if (c.getNombre().equalsIgnoreCase(nombreCasa)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return nombre + " (" + casas.size() + "/" + MAX_CASAS + " casas)";
    }
}

