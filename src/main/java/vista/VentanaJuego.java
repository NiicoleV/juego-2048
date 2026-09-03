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

    //COLORES
    private Color colorFondo = new Color(41, 70, 110);
    private Color colorCeldaVacia = new Color(136, 159, 189);
    private Color colorFicha1 = new Color(87, 183, 230);//azul
    private Color colorFicha2 = new Color(176, 37, 37);//rojo
    private Color colorFicha3 = new Color(255, 255, 255); //blamco
    
    public VentanaJuego() {
        setTitle("Threes!");
        setSize(500, 630);//baje el tamanio de la grilla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        //fondode la ventana 
        getContentPane().setBackground(colorFondo);
        
        // Panel del tablero (centro)
        JPanel panelTablero = new JPanel(new GridLayout(4, 4, 5, 5));
        panelTablero.setPreferredSize(new Dimension(380, 380)); // Cuadrado centrado
        panelTablero.setBackground(colorFondo);
        
        for (int fila = 0; fila < 4; fila++) {
            for (int columna = 0; columna < 4; columna++) {
                JLabel celda = new JLabel("", SwingConstants.CENTER);
                celda.setOpaque(true);
                celda.setBackground(colorCeldaVacia);
                celda.setFont(new Font("Arial", Font.BOLD, 24));
                celdas[fila][columna] = celda;
                panelTablero.add(celda);
            }
        }
        
        //JPanel contenedor, mantiene ;la grilla centrada 
        JPanel panelContenedor = new JPanel(new GridBagLayout());
        panelContenedor.setOpaque(false);
        panelContenedor.add(panelTablero);
        
        add(panelContenedor, BorderLayout.CENTER);


        // Label de puntaje (arriba)
        labelPuntaje = new JLabel("Puntaje: 0", SwingConstants.CENTER);
        labelPuntaje.setFont(new Font("Arial", Font.BOLD, 18));
        labelPuntaje.setForeground(Color.WHITE);
        labelPuntaje.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(labelPuntaje, BorderLayout.NORTH);

     // Boton de reiniciar tablero
        JButton botonReiniciar = new JButton("Reiniciar");
        botonReiniciar.setFont(new Font("Arial", Font.BOLD, 14));
        botonReiniciar.setFocusable(false); 
        botonReiniciar.addActionListener(e -> {
            if (presenter != null) {
                presenter.reiniciarJuego();
            }
        });
        //JPanel para el boton
        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        panelBoton.add(botonReiniciar);
        add(panelBoton, BorderLayout.SOUTH);
       
        
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
                JLabel celda =celdas[fila][columna];
                
                if(valor == 0) {
                	celda.setText("");
                	celda.setBackground(colorCeldaVacia);
                }else {
                	celda.setText(String.valueOf(valor));
                	aplicarColorFicha(celda,valor);
                }
                
            }
        }
    }
    
    private void aplicarColorFicha(JLabel celda, int valor) {
    	if(valor == 1) {
    		celda.setBackground(colorFicha1);
    	}else if (valor == 2) {
    		celda.setBackground(colorFicha2);
    	}else{
    		celda.setBackground(colorFicha3);
    	}
    }

    @Override
    public void mostrarPuntaje(int puntaje) {
        labelPuntaje.setText("Puntaje: " + puntaje);
    }

    @Override
    public void mostrarFinDeJuego(int puntajeFinal) {
    	int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¡Juego terminado! Puntaje final: " + puntajeFinal + "\n Volver a jugar?",
                "Game Over",
                JOptionPane.YES_NO_OPTION
        );    	
    	
    	if(respuesta == JOptionPane.YES_OPTION) {
    		if (presenter != null) {
    			presenter.reiniciarJuego();
    		}
    	}else {
    		System.exit(0);
    	}
    	
    }
    
}