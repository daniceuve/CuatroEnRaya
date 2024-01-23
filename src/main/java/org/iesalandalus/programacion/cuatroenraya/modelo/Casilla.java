package org.iesalandalus.programacion.cuatroenraya.modelo;

import javax.naming.OperationNotSupportedException;
import java.util.Objects;

public class Casilla {

    private Ficha ficha;

    public Casilla(){
        ficha = null;
    }
    public Ficha getFicha() {
        return ficha;
    }
    public void setFicha(Ficha ficha) throws OperationNotSupportedException{
        if (estaOcupada())
            throw new OperationNotSupportedException("La casilla ya contiene una ficha.");
        this.ficha = Objects.requireNonNull(ficha, "No se puede poner una ficha nula.");
    }

    public boolean estaOcupada() {
        return ficha != null;
    }

    @Override
    public String toString() {
        return estaOcupada() ? ficha.toString() : " ";
    }
}
