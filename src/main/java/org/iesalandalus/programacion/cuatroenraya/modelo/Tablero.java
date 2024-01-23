package org.iesalandalus.programacion.cuatroenraya.modelo;

import javax.naming.OperationNotSupportedException;
import java.util.Objects;

public class Tablero {

    public static final int FILAS = 6;
    public static final int COLUMNAS = 7;
    public static final int FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS = 4;
    private final Casilla[][] casillas;

    public Tablero() {
        casillas = new Casilla[COLUMNAS][FILAS];
        for (int i = 0; i < COLUMNAS; i++) {
            for (int j = 0; j < FILAS; j++) {
             casillas[i][j] = new Casilla();
            }
        }
    }

    private boolean columnaVacia(int columna) {
        boolean comprobarVacio = true;
        for (int i = 0; i < FILAS && comprobarVacio; i++) {
            if (casillas[columna][i].estaOcupada())
                comprobarVacio = false;
        }
        return comprobarVacio;
    }

    public boolean estaVacio() {
        boolean comprobarVacio = true;
        for (int i = 0; i < FILAS && comprobarVacio; i++) {
            comprobarVacio = columnaVacia(i);
        }
        return comprobarVacio;
    }
    private boolean columnaLlena(int columna) {
        boolean comprobarLleno = true;
        for (int i = 0; i < COLUMNAS && comprobarLleno; i++) {
            if (!casillas[columna][i].estaOcupada())
                comprobarLleno = false;
        }
        return comprobarLleno;
    }

    public boolean estaLleno() {
        boolean comprobarLleno = true;
        for (int i = 0; i < COLUMNAS && comprobarLleno; i++) {
            comprobarLleno = columnaLlena(i);
        }
        return comprobarLleno;
    }

    private void comprobarFicha(Ficha ficha) {
        Objects.requireNonNull(ficha, "La ficha no puede ser nula.");
    }
    private void comprobarColumna(int columna) {
        if (columna < 0 || columna >= COLUMNAS)
            throw new IllegalArgumentException("Columna incorrecta.");
    }

    private int getPrimeraFilaVacia (int columna) {
        int filaVacia = 0;
        boolean filaLLena = true;
        for (int i = 0; i < FILAS && filaLLena; i++ ) {
            if (!casillas[columna][i].estaOcupada()) {
                filaVacia = i;
                filaLLena = false;
            }
        }
        return filaVacia;
    }

    private boolean objetivoAlcanzado(int fichasIgualesConsecutivas) {
        return fichasIgualesConsecutivas <= FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
    }
    private boolean comprobarHorizontal(int fila, Ficha ficha) {
        int fichasConsecutivas = 0;
        for (int i = 0; i < FILAS; i++) {
            if (casillas[i][fila].estaOcupada() && casillas[i][fila].getFicha().equals(ficha)) {
                fichasConsecutivas++;
            }
            else fichasConsecutivas = 0;
        }
        return objetivoAlcanzado(fichasConsecutivas);
    }

    private boolean comprobarVertical(int columna, Ficha ficha) {
        int fichasConsecutivas = 0;
        for (int i = 0; i < FILAS; i++) {
            if (casillas[columna][i].estaOcupada() && casillas[columna][i].getFicha().equals(ficha)) {
                fichasConsecutivas++;
            }
            else fichasConsecutivas = 0;
        }
        return objetivoAlcanzado(fichasConsecutivas);
    }

    private int menor(int fila, int columna) {
        return Math.min(fila, columna);
    }

    private boolean comprobarDiagonalNE(int filaSemilla, int columnaSemilla, Ficha ficha) {
        int desplazamiento = menor(filaSemilla, columnaSemilla);
        int filaInicial = filaSemilla - desplazamiento;
        int columnaInicial = columnaSemilla - desplazamiento;
        int i = filaInicial;
        int j = columnaInicial;
        int fichasConsecutivas = 0;
        while (i <= FILAS || j <= COLUMNAS) {
            if (casillas[j][i].estaOcupada() && casillas[j][i].getFicha().equals(ficha))
                fichasConsecutivas++;
            else fichasConsecutivas = 0;
            i++;
            j++;
        }
        return objetivoAlcanzado(fichasConsecutivas);
    }
    private boolean comprobarDiagonalNO(int filaSemilla, int columnaSemilla, Ficha ficha) {
        int desplazamiento = menor(filaSemilla, (COLUMNAS - 1 - columnaSemilla));
        int filaInicial = filaSemilla - desplazamiento;
        int columnaInicial = columnaSemilla + desplazamiento;
        int i = filaInicial;
        int j = columnaInicial;
        int fichasConsecutivas = 0;
        while (i <= FILAS || j >= 0) {
            if (casillas[j][i].estaOcupada() && casillas[j][i].getFicha().equals(ficha))
                fichasConsecutivas++;
            else fichasConsecutivas = 0;
            i++;
            j--;
        }
        return objetivoAlcanzado(fichasConsecutivas);
    }

    private boolean comprobarTirada(int fila, int columna) {
        Ficha ficha = casillas[columna][fila].getFicha();
        return (comprobarHorizontal(fila, ficha) || comprobarVertical(columna, ficha) || comprobarDiagonalNE(fila, columna, ficha) || comprobarDiagonalNO(fila, columna, ficha));
    }

    public boolean introducirFicha(int columna, Ficha ficha) throws OperationNotSupportedException {

        comprobarFicha(ficha);
        comprobarColumna(columna);
        if (columnaLlena(columna)) {
            throw new OperationNotSupportedException("Columna llena.");
        }

        int fila = getPrimeraFilaVacia(columna);

        casillas[columna][fila].setFicha(ficha);

        return comprobarTirada(fila, columna);

    }

    @Override
    public String toString() {

        StringBuilder tablero = new StringBuilder();
        for (int i = FILAS - 1; i >= 0; i--) {
            tablero.append("|");
            for (int j = 0; j < COLUMNAS; j++) {
                if (casillas[j][i].estaOcupada()) {
                    tablero.append(casillas[j][i].getFicha());
                } else {
                    tablero.append(" ");
                }
            }
            tablero.append("|\n");
        }
        tablero.append(" -------\n");
        return tablero.toString();

    }
}
