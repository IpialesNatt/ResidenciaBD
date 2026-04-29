package com.example.servicio;

import com.example.modelo.Casa;
import com.example.modelo.Habitacion;
import com.example.modelo.Persona;
import com.example.modelo.Residencia;
import com.example.persistencia.JsonRepository;

public class ResidenciaService {

    private JsonRepository repositorio = new JsonRepository();
    private Residencia residencia;

    // Al crear el servicio, carga los datos del JSON
    public ResidenciaService() {
        residencia = repositorio.cargar();
    }

    public Residencia getResidencia() {
        return residencia;
    }

    // AÑADIR: asigna una persona a una habitación libre
    public boolean añadirPersona(String nombreCasa, int numeroHabitacion, Persona persona) {
        Casa casa = residencia.getCasa(nombreCasa);
        if (casa == null) return false;

        Habitacion habitacion = casa.getHabitacion(numeroHabitacion);
        if (habitacion == null || !habitacion.estaLibre()) return false;

        habitacion.setPersona(persona);
        repositorio.guardar(residencia);
        return true;
    }

    // ELIMINAR: libera una habitación quitando la persona
    public boolean eliminarPersona(String nombreCasa, int numeroHabitacion) {
        Casa casa = residencia.getCasa(nombreCasa);
        if (casa == null) return false;

        Habitacion habitacion = casa.getHabitacion(numeroHabitacion);
        if (habitacion == null || habitacion.estaLibre()) return false;

        habitacion.liberar();
        repositorio.guardar(residencia);
        return true;
    }

    // MODIFICAR: reemplaza la persona de una habitación por otra
    public boolean modificarPersona(String nombreCasa, int numeroHabitacion, Persona personaNueva) {
        Casa casa = residencia.getCasa(nombreCasa);
        if (casa == null) return false;

        Habitacion habitacion = casa.getHabitacion(numeroHabitacion);
        if (habitacion == null) return false;

        habitacion.setPersona(personaNueva);
        repositorio.guardar(residencia);
        return true;
    }

    // BUSCAR: devuelve la habitación donde está la persona con ese DNI, o null si no existe
    public Habitacion buscarPorDni(String dni) {
        for (Casa casa : residencia.getCasas()) {
            for (Habitacion habitacion : casa.getHabitaciones()) {
                if (!habitacion.estaLibre() && habitacion.getPersona().getDni().equals(dni)) {
                    return habitacion;
                }
            }
        }
        return null;
    }
}
