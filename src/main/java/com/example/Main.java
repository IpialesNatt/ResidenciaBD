package com.example;

import com.example.vista.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Swing necesita que la ventana se cree en su propio hilo (EDT)
        SwingUtilities.invokeLater(() -> new VentanaPrincipal());
    }

    // IA utilizada: Claude (claude.ai) — revisión de imports, estructura de capas y código de la vista.
}
