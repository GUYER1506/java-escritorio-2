package com.example.textanalyzer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.datatransfer.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class VentanaPrincipal extends JFrame {

    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuItem menuAbrir;
    private javax.swing.JMenuItem menuGuardar;
    private javax.swing.JMenuItem menuGuardarComo;
    private javax.swing.JMenu menuEditar;
    private javax.swing.JMenuItem menuCopiar;
    private javax.swing.JMenuItem menuCortar;
    private javax.swing.JMenuItem menuPegar;
    private javax.swing.JMenuItem menuBuscar;
    private javax.swing.JMenuItem menuReemplazar;
    private javax.swing.JScrollPane scrollArea;
    private javax.swing.JTextArea areaTexto;
    private javax.swing.JButton btnProcesar;
    private javax.swing.JScrollPane scrollResultados;
    private javax.swing.JTextArea areaResultados;

    private File currentFile = null;

    public VentanaPrincipal() {
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        menuBar = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuAbrir = new javax.swing.JMenuItem();
        menuGuardar = new javax.swing.JMenuItem();
        menuGuardarComo = new javax.swing.JMenuItem();
        menuEditar = new javax.swing.JMenu();
        menuCopiar = new javax.swing.JMenuItem();
        menuCortar = new javax.swing.JMenuItem();
        menuPegar = new javax.swing.JMenuItem();
        menuBuscar = new javax.swing.JMenuItem();
        menuReemplazar = new javax.swing.JMenuItem();
        scrollArea = new javax.swing.JScrollPane();
        areaTexto = new javax.swing.JTextArea();
        btnProcesar = new javax.swing.JButton();
        scrollResultados = new javax.swing.JScrollPane();
        areaResultados = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Analizador de Texto - Murciélago");

        // --- Menu Archivo
        menuArchivo.setText("Archivo");

        menuAbrir.setText("Abrir");
        menuAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        menuAbrir.addActionListener(e -> abrirArchivo());
        menuArchivo.add(menuAbrir);

        menuGuardar.setText("Guardar");
        menuGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        menuGuardar.addActionListener(e -> guardarArchivo(false));
        menuArchivo.add(menuGuardar);

        menuGuardarComo.setText("Guardar como");
        menuGuardarComo.addActionListener(e -> guardarArchivo(true));
        menuArchivo.add(menuGuardarComo);

        menuBar.add(menuArchivo);

        // --- Menu Editar
        menuEditar.setText("Editar");

        menuCopiar.setText("Copiar");
        menuCopiar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        menuCopiar.addActionListener(e -> areaTexto.copy());
        menuEditar.add(menuCopiar);

        menuCortar.setText("Cortar");
        menuCortar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
        menuCortar.addActionListener(e -> areaTexto.cut());
        menuEditar.add(menuCortar);

        menuPegar.setText("Pegar");
        menuPegar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
        menuPegar.addActionListener(e -> areaTexto.paste());
        menuEditar.add(menuPegar);

        menuBuscar.setText("Buscar");
        menuBuscar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK));
        menuBuscar.addActionListener(e -> buscarEnTexto());
        menuEditar.add(menuBuscar);

        menuReemplazar.setText("Reemplazar");
        menuReemplazar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK));
        menuReemplazar.addActionListener(e -> reemplazarEnTexto());
        menuEditar.add(menuReemplazar);

        menuBar.add(menuEditar);

        setJMenuBar(menuBar);

        // --- Área de texto principal
        areaTexto.setColumns(60);
        areaTexto.setRows(15);
        scrollArea.setViewportView(areaTexto);

        btnProcesar.setText("Procesar");
        btnProcesar.addActionListener(e -> procesarTexto());

        // --- Resultados
        areaResultados.setEditable(false);
        areaResultados.setColumns(60);
        areaResultados.setRows(8);
        scrollResultados.setViewportView(areaResultados);

        // Layout (simple GroupLayout to keep GUI builder-like)
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addGap(10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(scrollArea)
                    .addComponent(btnProcesar)
                    .addComponent(scrollResultados)
                )
                .addGap(10))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGap(10)
            .addComponent(scrollArea)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(btnProcesar)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(scrollResultados)
            .addGap(10)
        );

        pack();
    }// </editor-fold>                        

    // File operations
    private void abrirArchivo() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
        int res = fc.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                areaTexto.setText(sb.toString());
                currentFile = f;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarArchivo(boolean guardarComo) {
        if (!guardarComo && currentFile != null) {
            // sobrescribir
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(currentFile), StandardCharsets.UTF_8))) {
                bw.write(areaTexto.getText());
                JOptionPane.showMessageDialog(this, "Archivo guardado correctamente.", "Guardado", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
        int res = fc.showSaveDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            // ensure .txt extension
            if (!f.getName().toLowerCase().endsWith(".txt")) {
                f = new File(f.getAbsolutePath() + ".txt");
            }
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))) {
                bw.write(areaTexto.getText());
                currentFile = f;
                JOptionPane.showMessageDialog(this, "Archivo guardado correctamente.", "Guardado", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Buscar y reemplazar
    private void buscarEnTexto() {
        String buscar = JOptionPane.showInputDialog(this, "Palabra a buscar:", "Buscar", JOptionPane.QUESTION_MESSAGE);
        if (buscar == null || buscar.isEmpty()) return;
        String texto = areaTexto.getText();
        int idx = texto.indexOf(buscar);
        if (idx >= 0) {
            areaTexto.requestFocus();
            areaTexto.select(idx, idx + buscar.length());
            JOptionPane.showMessageDialog(this, "Palabra encontrada en posición: " + idx, "Encontrado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró la palabra.", "Buscar", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void reemplazarEnTexto() {
        JPanel panel = new JPanel(new java.awt.GridLayout(2,2));
        JTextField from = new JTextField();
        JTextField to = new JTextField();
        panel.add(new JLabel("Buscar:"));
        panel.add(from);
        panel.add(new JLabel("Reemplazar por:"));
        panel.add(to);
        int res = JOptionPane.showConfirmDialog(this, panel, "Reemplazar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            String texto = areaTexto.getText();
            texto = texto.replace(from.getText(), to.getText());
            areaTexto.setText(texto);
        }
    }

    // Procesar: estadísticas y traducción Murciélago
    private void procesarTexto() {
        String texto = areaTexto.getText();
        if (texto == null || texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay texto para procesar.", "Procesar", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int chars = texto.length();
        String[] wordsArr = texto.trim().isEmpty() ? new String[0] : texto.trim().split("\\s+"); 
        int words = wordsArr.length;
        int vowels = 0;
        int consonants = 0;
        int digits = 0;
        for (char c : texto.toLowerCase().toCharArray()) {
            if (Character.isDigit(c)) digits++;
            else if ("aeiouáéíóúü".indexOf(c) >= 0) vowels++;
            else if (Character.isLetter(c)) consonants++;
        }

        int lines = texto.split("\n", -1).length;

        String translation = murcielagoCipher(texto);

        StringBuilder sb = new StringBuilder();
        sb.append("--- Estadísticas ---\n");
        sb.append("Caracteres: ").append(chars).append('\n');
        sb.append("Palabras: ").append(words).append('\n');
        sb.append("Líneas: ").append(lines).append('\n');
        sb.append("Vocales: ").append(vowels).append('\n');
        sb.append("Consonantes: ").append(consonants).append('\n');
        sb.append("Dígitos: ").append(digits).append('\n');
        sb.append("\n--- Traducción (clave Murciélago) ---\n");
        sb.append(translation).append('\n');

        areaResultados.setText(sb.toString());
    }

    private String murcielagoCipher(String input) {
        // Mapping m->0, u->1, r->2, c->3, i->4, e->5, l->6, a->7, g->8, o->9 (case-insensitive)
        String mapFrom = "murcielago";
        String mapTo =   "0123456789";
        StringBuilder out = new StringBuilder();
        for (char ch : input.toCharArray()) {
            char low = Character.toLowerCase(ch);
            int idx = mapFrom.indexOf(low);
            if (idx >= 0) {
                out.append(mapTo.charAt(idx));
            } else {
                out.append(ch);
            }
        }
        return out.toString();
    }
}
