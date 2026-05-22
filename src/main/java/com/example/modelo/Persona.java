package com.example.modelo;
public class Persona {

    private String nombre;
    private String apellidos;
    private String dni;
    private String telefono;
    private String email;
    private String estudiosEnCurso;

    // Constructor vacío para que Gson pueda crear objetos
    public Persona() {
    }

    public Persona(String nombre, String apellidos, String dni,
                   String telefono, String email, String estudiosEnCurso) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.estudiosEnCurso = estudiosEnCurso;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEstudiosEnCurso() { return estudiosEnCurso; }
    public void setEstudiosEnCurso(String estudiosEnCurso) { this.estudiosEnCurso = estudiosEnCurso; }

    @Override
    public String toString() {
        return nombre + " " + apellidos + " - " + estudiosEnCurso;
    }
}


