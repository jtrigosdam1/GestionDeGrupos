package com.masanz.logic;

import java.util.Arrays;

public class Grupo {

    private String nombre;
    private Persona[] personas;
    private int tamano;
    private int filas;
    private int columnas;

    public Grupo(String nombre, int filas, int columnas) {
        this.nombre = nombre;
        this.filas = filas;
        this.columnas = columnas;
        personas = new Persona[filas*columnas];
        tamano = 0;
    }

    /**
     * Este método no tiene sentido en la lógica de la aplicación.
     * Se ha incorporado para ayudar a debuguear los métodos.
     * Hay que tener en cuenta que las personas deberan estar metidas
     * de forma ascendente en base a los apellidos y el nombre en el array.
     * @param personas Array de de personas ordenado, puede tener nulls al final.
     * @param tamano Cantidad de personas que se deben considerar.
     */
    public Grupo setPersonasTamano(Persona[] personas, int tamano) {
        this.personas = personas;
        this.tamano = tamano;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

//    public boolean add(Persona persona) {
//        //Esto no mete a la persona de forma ordenada, puntúa cero.
//        if (tamano < personas.length) {
//            personas[tamano++] = persona;
//            return true;
//        }
//        return false;
//    }

    public boolean add(Persona persona) {
        //TODO: add
        return false;
    }

    public int getCuantosSuspendidosHay(){
        //DONE: getCuantosSuspendidosHay
        int suspendidos = 0;
        for (int i = 0; i < tamano; i++) {
            if (personas[i].getPuntos() < 50) {
                suspendidos++;
            }
        }
        return suspendidos;
    }

    public Persona[] getPersonasPorApellidos() {
        //DONE: getPersonasPorApellidos
        for (int i = 0; i < tamano; i++) {
            int posmin = i;
            for (int j = i; j < tamano; j++) {
                if (personas[j].getSiglas().charAt(1) < personas[posmin].getSiglas().charAt(1)) {
                    posmin = j;
                }
            }
            Persona aux = personas[posmin];
            personas[posmin] = personas[i];
            personas[i] = aux;
        }
        return Arrays.copyOf(personas, tamano);
    }

    /**
     * Algoritmo de inserción directa de una persona p en un array a que ya tiene t personas.
     * @param a array de personas, de longitud n.
     * @param t numero de personas ya insertadas, t>=0 y t<=n-1
     * @param p persona a insertar con una cantidad de puntos
     */
    public static void insercionDirectaPorPuntosCreciente(Persona[] a, int t,  Persona p) {
        //DONE: insercionDirectaPorPuntosCreciente
        a[t++] = p;
        Persona aux;
        int j;
        for (int i = 1; i < t; i++) {
            aux = a[i];
            j = i - 1;
            while (j >= 0 && aux.getPuntos() < a[j].getPuntos()) {
                a[j + 1] = a[j--];
            }
            a[j + 1] = aux;
        }
    }

    public Persona[] getPersonasSuspendidas() {
        //DONE: getPersonasSuspendidas
        Persona[] suspendidos = new Persona[getCuantosSuspendidosHay()];
        int pos = 0;
        for (int i = 0; i < tamano; i++) {
            if (personas[i].getPuntos() < 50) {
                insercionDirectaPorPuntosCreciente(suspendidos, pos++, personas[i]);
            }
        }
        return suspendidos;
    }

    /**
     * Ordena un array de personas utilizando el algoritmo de ordenación por selección directa
     * de puntos de forma descendente.
     * @param a array de personas, todas las celdas tienen una referencia a un objeto persona
     *          distinto de null. Al final estarán ordenados por la puntuación de más a menos.
     */
    public static void ordenacionPorSeleccionDirectaDePuntosDescendente(Persona[] a) {
        //DONE: ordenacionPorSeleccionDirectaDePuntosDescendente
        for (int i = 0; i < a.length; i++) {
            int posmin = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j].getPuntos() > a[posmin].getPuntos()) {
                    posmin = j;
                }
            }
            Persona aux = a[posmin];
            a[posmin] = a[i];
            a[i] = aux;
        }
    }

    public Persona[] getPersonasOrdenadasPorPuntos() {
        Persona[] a = new Persona[tamano];
        System.arraycopy(personas,0,a,0,tamano);
        ordenacionPorSeleccionDirectaDePuntosDescendente(a);
        return a;
    }

    /**
     * Devuelve el índice, de 0 a tamano, de la posición que le corresponde a un texto
     * en la ordenación alfabética de apellidos y nombre si se fuese a insertar.
     * Si el texto coicide con getApellidosNombre se debe devolver esa posición.
     * Aunque en el array se considere que hay tamano personas, de 0 a tamano-1,
     * si alfabéticamente le correspondiese después del último, se debería devolver
     * el valor tamano, no tamano-1.
     * @param txt texto cuyo indice de inserción se busca, ej "García, Aiora"
     * @return posición del array en la que se insertaría
     */
    public int busquedaDicotomica(String txt) {
        //TODO: busquedaDicotomica
        boolean encontrado = false;
        int izquierda = 0;
        int derecha = personas.length - 1;
        int mitad = 0;
        while (izquierda <= derecha && !encontrado) {
            mitad = (izquierda + derecha / 2);
            if ((personas[mitad].getApellidosNombre().compareTo(txt) == 0)) {
                encontrado = true;
            }
            else if (personas[mitad].getApellidosNombre().compareTo(txt) > 0) {
                derecha = mitad -1;
            }
            else {
                izquierda = mitad + 1;
            }
        }
        return mitad;
    }

    /**
     * Devuelve un array de personas cuyos apellidos empiezan como se indica
     * @param apellidos texto por el cual deben empezar los apellidos
     * @return una array con las referencias a las personas, puede ser de tamaño 0.
     */
    public Persona[] find(String apellidos) {
        //TODO: find
        return null;
    }

    public int numeroPrimer(String apellidos) {
        return busquedaDicotomica(apellidos) + 1;
    }

    /**
     * Devuelve la persona según el índice alfabético mostrado
     * @param idx indice válido del 1 en adelante hasta tamaño incluido
     * @return persona o null si no existe
     */
    public Persona getPersona(int idx) {
        //DONE: getPersona
        if (idx - 1 < 0 || idx - 1 >= tamano) {
            return null;
        }
        return getPersonasPorApellidos()[idx - 1];
    }

    /**
     * Borra una persona del grupo eliminandolo del array y dejandolo ordenado y seguido con el tamano reducido
     * @param idx indice válido del 1 en adelante hasta tamaño incluido
     */
    public boolean del(int idx) {
        //TODO: del
        for (int i = idx - 1; i < tamano; i++) {
            personas[i] = personas[i + 1];
        }
        tamano--;
        return true;
    }

    @Override
    public String toString() {
        return "{" +
                "nombre:\"" + nombre + "\"" +
                ", tamano:" + tamano +
                ", filas:" + filas +
                ", columnas:" + columnas +
                '}';
    }

}
