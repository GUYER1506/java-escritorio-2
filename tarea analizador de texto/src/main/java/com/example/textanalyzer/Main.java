package com.example.textanalyzer;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal vp = new VentanaPrincipal();
            vp.setLocationRelativeTo(null);
            vp.setVisible(true);
        });
    }
}
