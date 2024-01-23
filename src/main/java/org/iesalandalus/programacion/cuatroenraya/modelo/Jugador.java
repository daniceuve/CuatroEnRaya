package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Objects;

public record Jugador(String nombre, Ficha colorFichas) {

    public Jugador{
        validarNombre(nombre);
        validarColorFichas(colorFichas);
    }

    private void validarNombre(String nombre) {
        Objects.requireNonNull(nombre,"El nombre no puede ser nulo.");
        if (nombre.matches("\\s*"))
            throw new IllegalArgumentException("El nombre no puede estar en blanco.");

    }
    private void validarColorFichas(Ficha ficha) {
        Objects.requireNonNull(ficha, "El color de las fichas no puede ser nulo.");
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", nombre, colorFichas);
    }
}
