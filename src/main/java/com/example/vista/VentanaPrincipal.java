package com.example.vista;

import com.example.modelo.Casa;
import com.example.modelo.Habitacion;
import com.example.modelo.Persona;
import com.example.modelo.Residencia;
import com.example.servicio.ResidenciaService;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private ResidenciaService servicio;

    // Campos del formulario
    private JComboBox<String> comboCasa;
    private JComboBox<Integer> comboHabitacion;
    private JTextField campoNombre;
    private JTextField campoApellidos;
    private JTextField campoDni;
    private JTextField campoTelefono;
    private JTextField campoEmail;
    private JTextField campoEstudios;

    // Zona de visualización
    private JTextArea areaTexto;

    // Guarda el DNI del residente encontrado con "Buscar".
    // Lo necesita "Modificar" para saber quién hay que mover/actualizar.
    // Es null cuando no hay ninguna búsqueda activa.
    private String dniEncontrado = null;

    public VentanaPrincipal() {
        servicio = new ResidenciaService();

        setTitle("Gestión de Residencia Universitaria Die Brücke");
        setSize(750, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

         // Panel principal con BoxLayout vertical
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    
    mainPanel.add(crearPanelFormulario());
    mainPanel.add(crearPanelBotones());
    mainPanel.add(crearPanelVisualizacion());
    
    // Para que el área de texto se expanda, se le da "peso" vertical
    add(mainPanel);
    
    mostrarDatos();
    setVisible(true);
    }

    // -----------------------------------------------------------------------------------------------
    // PANEL FORMULARIO: campos de entrada
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del residente"));

        panel.add(new JLabel("Casa:"));
        comboCasa = new JComboBox<>();
        for (Casa casa : servicio.getResidencia().getCasas()) {
            comboCasa.addItem(casa.getNombre());
        }
        panel.add(comboCasa);

        panel.add(new JLabel("Habitación (1-6):"));
        comboHabitacion = new JComboBox<>(new Integer[] { 1, 2, 3, 4, 5, 6 });
        panel.add(comboHabitacion);

        panel.add(new JLabel("Nombre:"));
        campoNombre = new JTextField();
        panel.add(campoNombre);

        panel.add(new JLabel("Apellidos:"));
        campoApellidos = new JTextField();
        panel.add(campoApellidos);

        panel.add(new JLabel("DNI:"));
        campoDni = new JTextField();
        panel.add(campoDni);

        panel.add(new JLabel("Teléfono:"));
        campoTelefono = new JTextField();
        panel.add(campoTelefono);

        panel.add(new JLabel("Email:"));
        campoEmail = new JTextField();
        panel.add(campoEmail);

        panel.add(new JLabel("Estudios en curso:"));
        campoEstudios = new JTextField();
        panel.add(campoEstudios);

        return panel;
    }

    // -----------------------------------------------------------------------------------------------
    // PANEL BOTONES: operaciones principales (CRUD + Buscar)

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));

        JButton btnAnadir = new JButton("Añadir");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscar = new JButton("Buscar por DNI");

        btnAnadir.addActionListener(e -> añadirPersona());
        btnModificar.addActionListener(e -> modificarPersona());
        btnEliminar.addActionListener(e -> eliminarPersona());
        btnBuscar.addActionListener(e -> buscarPersona());

        panel.add(btnAnadir);
        panel.add(btnModificar);
        panel.add(btnEliminar);
        panel.add(btnBuscar);

       return panel;
    }

    // -----------------------------------------------------------------------------------------------
    // PANEL VISUALIZACIÓN: muestra los datos en pantalla

    private JScrollPane crearPanelVisualizacion() {
        areaTexto = new JTextArea(15, 12);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setBorder(BorderFactory.createTitledBorder("Panel informativo de la residencia"));
        return scroll;
    }

    // -----------------------------------------------------------------------------------------------
    // ACCIÓN: Añadir persona a una habitación libre

    private void añadirPersona() {
        if (!camposRellenos()) {
            JOptionPane.showMessageDialog(this, "Es necesario rellenar todos los campos antes de añadir.");
            return;
        }

        String casa = (String) comboCasa.getSelectedItem();
        int habitacion = (int) comboHabitacion.getSelectedItem();
        Persona persona = crearPersonaDesdeFormulario();

        String errorFormato = validarDatos(persona);
        if (errorFormato != null) {
            JOptionPane.showMessageDialog(this, errorFormato);
            return;
        }

        String error = servicio.añadirPersona(casa, habitacion, persona);

        if (error == null) {
            JOptionPane.showMessageDialog(this, "Residente añadido correctamente.");
            mostrarDatos();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, error);
        }
    }

    // -----------------------------------------------------------------------------------------------
    // ACCIÓN: Modificar residente encontrado previamente con Buscar

    private void modificarPersona() {
        // Solo se puede modificar si antes se hizo una búsqueda
        if (dniEncontrado == null) {
            JOptionPane.showMessageDialog(this, "Primero busca el DNI del residente que quieres modificar.");
            return;
        }
        if (!camposRellenos()) {
            JOptionPane.showMessageDialog(this, "Rellena todos los campos antes de modificar.");
            return;
        }

        String nuevaCasa = (String) comboCasa.getSelectedItem();
        int nuevaHabitacion = (int) comboHabitacion.getSelectedItem();
        Persona persona = crearPersonaDesdeFormulario();

        String errorFormato = validarDatos(persona);
        if (errorFormato != null) {
            JOptionPane.showMessageDialog(this, errorFormato);
            return;
        }

        // Pasamos dniEncontrado para que el servicio sepa quién mover
        String error = servicio.modificarPersona(dniEncontrado, nuevaCasa, nuevaHabitacion, persona);

        if (error == null) {
            JOptionPane.showMessageDialog(this, "Datos modificados correctamente.");
            mostrarDatos();
            limpiarCampos(); // también pone dniEncontrado = null
        } else {
            JOptionPane.showMessageDialog(this, error);
        }
    }

    // -----------------------------------------------------------------------------------------------
    // ACCIÓN: Eliminar el residente de una habitación

    private void eliminarPersona() {
        String casa = (String) comboCasa.getSelectedItem();
        int habitacion = (int) comboHabitacion.getSelectedItem();

        boolean exito = servicio.eliminarPersona(casa, habitacion);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Residente eliminado correctamente.");
            mostrarDatos();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "La habitación ya estaba libre.");
        }
    }

    // -----------------------------------------------------------------------------------------------
    // ACCIÓN: Buscar persona por DNI y rellenar el formulario

    private void buscarPersona() {
        String dni = campoDni.getText().trim(); // se usa trim para evitar espacios al principio o al final que el
                                                // usuario pueda haber puesto por error
        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe un DNI en el campo DNI para buscar.");
            return;
        }

        Habitacion habitacion = servicio.buscarPorDni(dni); // se llama al servicio para que busque la habitación donde
                                                            // está el residente con ese DNI. Si no lo encuentra,
                                                            // devuelve null.

        if (habitacion != null) {
            Persona p = habitacion.getPersona();

            // Rellenar los campos de texto con los datos del residente
            campoNombre.setText(p.getNombre());
            campoApellidos.setText(p.getApellidos());
            campoDni.setText(p.getDni());
            campoTelefono.setText(p.getTelefono());
            campoEmail.setText(p.getEmail());
            campoEstudios.setText(p.getEstudiosEnCurso());

            // Poner los combos en su casa y habitación actual
            String casaActual = servicio.getCasaDeDni(dni);
            comboCasa.setSelectedItem(casaActual);
            comboHabitacion.setSelectedItem(habitacion.getNumero());

            // Guardar el DNI para que "Modificar" sepa a quién modificar
            dniEncontrado = dni;

            JOptionPane.showMessageDialog(this,
                    "Residente encontrado en " + casaActual + ", habitación " + habitacion.getNumero() + ".");
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró ningún residente con ese DNI.");
        }
    }

    // -----------------------------------------------------------------------------------------------
    // ACCIÓN: Vaciar todos los campos del formulario

    private void limpiarCampos() {
        campoNombre.setText("");
        campoApellidos.setText("");
        campoDni.setText("");
        campoTelefono.setText("");
        campoEmail.setText("");
        campoEstudios.setText("");
        dniEncontrado = null; // olvidar la búsqueda activa

    }

    // -----------------------------------------------------------------------------------------------
    // AUXILIAR: construye un objeto Persona con los datos del formulario

    private Persona crearPersonaDesdeFormulario() {
        return new Persona(
                campoNombre.getText().trim(),
                campoApellidos.getText().trim(),
                campoDni.getText().trim(),
                campoTelefono.getText().trim(),
                campoEmail.getText().trim(),
                campoEstudios.getText().trim());
    }

    // -----------------------------------------------------------------------------------------------
    // AUXILIAR: valida el formato de DNI, teléfono y email
    // Devuelve null si todo es correcto, o un mensaje de error

    private String validarDatos(Persona persona) {
        // DNI: exactamente 8 dígitos seguidos de 1 letra
        String dni = persona.getDni();
        if (dni.length() != 9) {
            return "El DNI debe tener 8 números y 1 letra (ejemplo: 12345678A).";
        }
        // Comprobar que los primeros 8 caracteres son dígitos
        for (int i = 0; i < 8; i++) {
            if (!Character.isDigit(dni.charAt(i))) { // si alguno no es dígito, error
                return "El DNI debe tener 8 números y 1 letra (ejemplo: 12345678A).";
            }
        }
        if (!Character.isLetter(dni.charAt(8))) { // el último carácter debe ser una letra
            return "El DNI debe terminar con una letra (ejemplo: 12345678A).";
        }

        // Teléfono: exactamente 9 dígitos
        String telefono = persona.getTelefono();
        if (telefono.length() != 9) {
            return "El teléfono debe tener exactamente 9 dígitos.";
        }
        for (int i = 0; i < 9; i++) { // recorre cada carácter del teléfono
            if (!Character.isDigit(telefono.charAt(i))) { // si alguno no es dígito, error
                return "El teléfono solo puede contener números.";
            }
        }

        // Email: debe contener @
        if (!persona.getEmail().contains("@")) { // si el email no contiene el carácter @, error
            return "El email no es válido. Debe contener @.";
        } else if (!persona.getEmail().endsWith(".com") && !persona.getEmail().endsWith(".es")
                && !persona.getEmail().endsWith(".org")) { // si el email no contiene el dominio .com ni .es ni .org,
                                                           // error
            return "El email no es válido. Debe contener un dominio como .com o .es o .org.";
        }

        return null; // null = todo correcto
    }

    // -------------------------------------------------------------------------------
    // AUXILIAR: comprueba que ningún campo de texto está vacío

    private boolean camposRellenos() {
        return !campoNombre.getText().trim().isEmpty() // se usa trim para evitar que el usuario pueda haber puesto solo
                                                       // espacios, lo cual no sería un dato válido
                && !campoApellidos.getText().trim().isEmpty()
                && !campoDni.getText().trim().isEmpty()
                && !campoTelefono.getText().trim().isEmpty()
                && !campoEmail.getText().trim().isEmpty()
                && !campoEstudios.getText().trim().isEmpty();
        // si alguno de los campos está vacío, el metodo camposRellenos devuelve false.
        // Si todos tienen algo escrito, devuelve true.
    }

    // -----------------------------------------------------------------------------------------------
    // AUXILIAR: recorre los datos y los pinta en el área de texto

    private void mostrarDatos() {
        StringBuilder texto = new StringBuilder();
        Residencia residencia = servicio.getResidencia();
        texto.append("\n");
        texto.append("Residencia Universitaria Die Brücke\n");
        texto.append("Dirección: ").append(residencia.getDireccion()).append("\n");
        texto.append("Curso académico: ").append(residencia.getCursoAcademico()).append("\n\n");

        for (Casa casa : residencia.getCasas()) {
            texto.append("-- ").append(casa.getNombre())
                    .append(" (").append(casa.contarOcupadas()).append("/6 ocupadas) --\n");
            for (Habitacion h : casa.getHabitaciones()) {
                texto.append("  ").append(h.toString()).append("\n");
            }
            texto.append("\n");
        }

        areaTexto.setText(texto.toString());
    }
}
