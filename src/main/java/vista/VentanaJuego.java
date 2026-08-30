package vista;

import presenter.PresenterJuego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class VentanaJuego extends JFrame implements VistaTablero {

    private JLabel[][] celdas = new JLabel[4][4];
    private JLabel labelPuntaje;
    private PresenterJuego presenter;

    public VentanaJuego() {
        setTitle("Threes!");
        setSize(1366, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel del tablero (centro)
        JPanel panelTablero = new JPanel(new GridLayout(4, 4, 5, 5));
        for (int fila = 0; fila < 4; fila++) {
            for (int columna = 0; columna < 4; columna++) {
                JLabel celda = new JLabel("", SwingConstants.CENTER);
                celda.setOpaque(true);
                celda.setBackground(Color.LIGHT_GRAY);
                celda.setFont(new Font("Arial", Font.BOLD, 24));
                celdas[fila][columna] = celda;
                panelTablero.add(celda);
            }
        }
        add(panelTablero, BorderLayout.CENTER);

        // Label de puntaje (arriba)
        labelPuntaje = new JLabel("Puntaje: 0", SwingConstants.CENTER);
        labelPuntaje.setFont(new Font("Arial", Font.BOLD, 18));
        add(labelPuntaje, BorderLayout.NORTH);

        // Captura de teclado
        setFocusable(true);
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (presenter == null) return; // por si todavía no se conectó
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> presenter.onFlechaArriba();
                    case KeyEvent.VK_DOWN -> presenter.onFlechaAbajo();
                    case KeyEvent.VK_LEFT -> presenter.onFlechaIzquierda();
                    case KeyEvent.VK_RIGHT -> presenter.onFlechaDerecha();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyTyped(KeyEvent e) {}
        });

        setVisible(true);
    }

    public void setPresenter(PresenterJuego presenter) {
        this.presenter = presenter;
    }

    @Override
    public void mostrarTablero(int[][] valores) {
        for (int fila = 0; fila < 4; fila++) {
            for (int columna = 0; columna < 4; columna++) {
                int valor = valores[fila][columna];
                celdas[fila][columna].setText(valor == 0 ? "" : String.valueOf(valor));
            }
        }
    }

    @Override
    public void mostrarPuntaje(int puntaje) {
        labelPuntaje.setText("Puntaje: " + puntaje);
    }

    @Override
    public void mostrarFinDeJuego(int puntajeFinal) {
        JOptionPane.showMessageDialog(this, "¡Juego terminado! Puntaje final: " + puntajeFinal);
    }
}