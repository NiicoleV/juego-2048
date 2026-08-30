package modelo;

import java.util.Random;

public class Tablero {
    public static final int tamanio = 4;
    private int[][] celdas;
    Random random = new Random();

    public Tablero() {
        celdas = new int[tamanio][tamanio];
        generarCantidadFichas(3);

    }

    //metodo para no tener que poner reiteradamente la funcion agregarFiachaAleatoria y poder definir el numero inicialmente (quizas mencionar en el informe)
    public void generarCantidadFichas(int cant){
        for(int i = 0; i < cant; i++){
            agregarFichaAleatoria();
        }
    }

    //agrrega las fichas iniciales, funciona en tableros mas grandes
    private void agregarFichaAleatoria() {
        boolean encontro = false;
        while(!encontro){
            //busco posicion random y genero el valor aleatorio
            int x = random.nextInt(tamanio);      // fila entre 0 y 3
            int y = random.nextInt(tamanio);      // columna entre 0 y 3
            int valor = random.nextInt(3) + 1;    // 1, 2 o 3

            //si esta cvacia entonces guardo el valor random y ademas termino el while
            if(estaVacia(x, y)){
                setValor(x, y, valor);
                encontro = true;
            }
        }
    }

    //verifica si una casilla esta vacia osea si es 0, creo metodo para no crear la verificacion dentro de cada metodo llamando a getValor
    private boolean estaVacia(int fila, int colum){
        if (getValor(fila, colum) == 0){
            return true;
        }
        return false;
    }

    //funcion para ver si dos valores son fucionables
    private boolean esFusionable(int valor1, int valor2) {
        if (valor1 == 1 && valor2 == 2) return true;
        if (valor1 == 2 && valor2 == 1) return true;
        return valor1 == valor2 && valor1 != 0 && valor1 % 3 == 0;
    }

    //funcion para mover arriba
    public void moverArriba() {
        for (int columna = 0; columna < tamanio; columna++) {
            // se guarda lo original antes de tocar algo, asi siempre compara al original
            int[] original = new int[tamanio];
            for (int fila = 0; fila < tamanio; fila++) {
                original[fila] = getValor(fila, columna);
            }

            // Comparamos cada fila con su vecina de arriba, SIEMPRE en base al snapshot
            for (int fila = 1; fila < tamanio; fila++) {
                int arriba = original[fila - 1];
                int actual = original[fila];

                if (actual == 0) {
                    continue; // significa que como no hay ninguna ficha sigue el for nomas, porque no hauy que mover
                }
                //si la ficha de arriba es 0 entonces movemos para arriba
                if (arriba == 0) {
                    setValor(fila - 1, columna, actual); //se mueve para arriba
                    setValor(fila, columna, 0);
                }
                //si la ficha de arriba no es 0 entonces vemos si es fuccionable y la fuccionamos
                else if (esFusionable(arriba, actual)) {
                    setValor(fila - 1, columna, arriba + actual); // se fusiona
                    setValor(fila, columna, 0);
                }
                // si no es ninguno de los casos no hace nada
            }
        }
        agregarFichaAleatoria();//despues de cada movimiento agregamos una ficha aleatoria
    }

    //SIN HACER
    public void moverAbajo() {
        // TODO
    }

    //SIN HACER
    public void moverIzquierda() {
        // TODO
    }

    //SIN HACER
    public void moverDerecha() {
        // TODO
    }

    //SIN HACER
    public boolean estaTerminado() {
        // TODO: no hay celdas vacías y no hay fusiones posibles
        return false;
    }

    public int getValor(int fila, int columna) {
        return celdas[fila][columna];
    }

    public void setValor(int fila, int columna, int valor){
        celdas[fila][columna] = valor;
    }

    public int[][] getCeldas() {
        return celdas;
    }
}