package com.example.servicio;

import com.example.modelo.Casa;
import com.example.modelo.Habitacion;
import com.example.modelo.Persona;
import com.example.modelo.Residencia;
import com.example.persistencia.JsonRepository;

// La clase de servicio es la que contiene la lógica de negocio de la residencia
public class ResidenciaService {

    private JsonRepository repositorio = new JsonRepository();
    private Residencia residencia;

    public ResidenciaService() {
        residencia = repositorio.cargar();
    }

    public Residencia getResidencia() {
        return residencia;
    }

    // AÑADIR: asigna una persona a una habitación libre
    // Devuelve null si todo va bien, o un mensaje de error si algo falla
    public String añadirPersona(String nombreCasa, int numeroHabitacion, Persona persona) {
       
        // Comprobar que el DNI no existe ya en la residencia
        if (buscarPorDni(persona.getDni()) != null) {
            return "Ya existe un studiante con el DNI " + persona.getDni() + ".";
        }

        Casa casa = residencia.getCasa(nombreCasa);
        if (casa == null) return "No existe la casa " + nombreCasa + ".";

        Habitacion habitacion = casa.getHabitacion(numeroHabitacion);
        if (habitacion == null || !habitacion.estaLibre()) {
            return "La habitación " + numeroHabitacion + " ya está ocupada o no existe.";
        }

        habitacion.setPersona(persona);
        repositorio.guardar(residencia);
        return null; // null = éxito
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


    public String modificarPersona(String dniOriginal, String nuevaCasa, int nuevaHabitacion, Persona personaNueva) {
        // Buscar dónde está actualmente el residente
        Habitacion habitacionActual = null;
        for (Casa c : residencia.getCasas()) {
            for (Habitacion h : c.getHabitaciones()) {
                if (!h.estaLibre() && h.getPersona().getDni().equals(dniOriginal)) {
                    habitacionActual = h;
                }
            }
        }
        if (habitacionActual == null) {
            return "No se encontró el residente con DNI " + dniOriginal + ".";
        }

        // Si el DNI cambia, comprobar que el nuevo no está ya en uso
        if (!dniOriginal.equals(personaNueva.getDni()) && buscarPorDni(personaNueva.getDni()) != null) {
            return "Ya existe un residente con el DNI " + personaNueva.getDni() + ".";
        }

        // Buscar la habitación destino
        Casa casaDestino = residencia.getCasa(nuevaCasa);
        if (casaDestino == null) return "No existe la casa " + nuevaCasa + ".";
        Habitacion habitacionDestino = casaDestino.getHabitacion(nuevaHabitacion);
        if (habitacionDestino == null) return "No existe la habitación " + nuevaHabitacion + ".";

        // Si es la misma habitación, solo actualizar los datos
        if (habitacionActual == habitacionDestino) {
            habitacionActual.setPersona(personaNueva);
            repositorio.guardar(residencia);
            return null;
        }

        // Si es distinta habitación, la destino debe estar libre
        if (!habitacionDestino.estaLibre()) {
            return "La habitación " + nuevaHabitacion + " de " + nuevaCasa + " ya está ocupada.";
        }

        // Mover: liberar la original y ocupar la nueva
        habitacionActual.liberar();
        habitacionDestino.setPersona(personaNueva);
        repositorio.guardar(residencia);
        return null; // null = éxito
    }

    // BUSCAR: devuelve la habitación donde está la persona con ese DNI
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

    //buscar por nombre
    

    // BUSCAR CASA: devuelve el nombre de la casa donde vive esa persona
    public String getCasaDeDni(String dni) {
        for (Casa casa : residencia.getCasas()) {
            for (Habitacion habitacion : casa.getHabitaciones()) {
                if (!habitacion.estaLibre() && habitacion.getPersona().getDni().equals(dni)) {
                    return casa.getNombre();
                }
            }
        }
        return null;
    }
}
