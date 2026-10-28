package aed.tp2;

import java.util.HashMap;

public class DiccionarioTrie<T> {

    // clase interna para los nodos del Trie
    private class NodoTrie {
        private T valor; // Almacena el valor (ej. InfoPromo) si es fin de clave
        private HashMap<Character, NodoTrie> hijos; // Transiciones a los siguientes caracteres

        public NodoTrie() {
            this.valor = null;
            this.hijos = new HashMap<>();
        }
    }

    private NodoTrie raiz;
    private int cantidadClaves;

    public DiccionarioTrie() {
        this.raiz = new NodoTrie();
        this.cantidadClaves = 0;
    }

    // --- Operación: Definir / Insertar en O(|L|) ---
    public void definir(String clave, T valor) {
        NodoTrie actual = this.raiz;

        for (int i = 0; i < clave.length(); i++) {
            char c = clave.charAt(i);
            if (!actual.hijos.containsKey(c)) {
                actual.hijos.put(c, new NodoTrie());
            }
            actual = actual.hijos.get(c);
        }

        if (actual.valor == null) {
            this.cantidadClaves++;
        }
        actual.valor = valor;
    }

    private NodoTrie navegarHasta(String clave) {
        if (clave == null) {
            return null;
        }
        NodoTrie actual = this.raiz;
        for (int i = 0; i < clave.length(); i++) {
            char c = clave.charAt(i);
            if (!actual.hijos.containsKey(c)) {
                return null;
            }
            actual = actual.hijos.get(c);
        }
        return actual;
    }

    // --- Operación: Obtener / Buscar en O(|L|) ---
    public T obtener(String clave) {
        NodoTrie actual = navegarHasta(clave);
        if (actual == null) {
            return null;
        }
        return actual.valor;
    }

    // --- Operación: Pertenece / Existe en O(|L|) ---
    public boolean esta(String clave) {
        return obtener(clave) != null;
    }

    // --- Operación: Borrar / Eliminar en O(|L|) ---
    public void borrar(String clave) {
        if (clave == null) return;
        borrarRecursivo(this.raiz, clave, 0);
    }

    private boolean borrarRecursivo(NodoTrie actual, String clave, int indice) {
        if (actual == null) {
            return false;
        }

        // Caso base: llegamos al final de la clave
        if (indice == clave.length()) {
            if (actual.valor != null) {
                actual.valor = null;
                this.cantidadClaves--;
                // si no tiene hijos, este nodo se puede eliminar
                return actual.hijos.isEmpty();
            }
            return false;
        }

        char c = clave.charAt(indice);
        NodoTrie hijo = actual.hijos.get(c);

        if (hijo == null) {
            return false; // la clave no existe
        }

        boolean seDebeEliminarHijo = borrarRecursivo(hijo, clave, indice + 1);

        if (seDebeEliminarHijo) {
            actual.hijos.remove(c);
            // El nodo actual se elimina si no guarda un valor y no tiene otros hijos
            return actual.valor == null && actual.hijos.isEmpty();
        }

        return false;
    }

    public int tamaño() {
        return this.cantidadClaves;
    }
}
