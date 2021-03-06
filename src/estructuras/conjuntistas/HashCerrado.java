package estructuras.conjuntistas;

import estructuras.lineales.Lista;

import java.io.Serializable;

public class HashCerrado<T> implements TablaHash<T>, Serializable {
    public static final int TAM = 10000;

    private int reHashing(T elem, int i) {
        int h = (elem.hashCode() + i - 1) % TAM;
        return h < 0 ? -h : h;
    }

    @Override
    public boolean insertar(T elem) {
        // TODO
        return false;
    }

    @Override
    public boolean eliminar(T elem) {
        // TODO
        return false;
    }

    @Override
    public boolean pertenece(T elem) {
        // TODO
        return false;
    }

    @Override
    public boolean esVacia() {
        // TODO
        return false;
    }

    @Override
    public Lista<T> listar() {
        // TODO
        return null;
    }
}
