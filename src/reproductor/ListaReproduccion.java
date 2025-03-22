/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reproductor;

import java.util.ArrayList;

/**
 *
 * @author Nadiesda Fuentes
 */
public class ListaReproduccion {
    private ArrayList<Cancion> canciones;
    
    public ListaReproduccion() {
        canciones = new ArrayList<>();
    }
    
    public void agregarCancion(Cancion cancion) {
        canciones.add(cancion);
    }
    
    public void eliminarCancion(int indice) {
        if (indice >= 0 && indice < canciones.size()) {
            canciones.remove(indice);
        }
    }
    
    public Cancion obtenerCancion(int indice) {
        if (indice >= 0 && indice < canciones.size()) {
            return canciones.get(indice);
        }
        return null;
    }
    
    public String[] obtenerNombresCanciones() {
        String[] nombres = new String[canciones.size()];
        for (int i = 0; i < canciones.size(); i++) {
            nombres[i] = canciones.get(i).getNombre() + " - " + canciones.get(i).getArtista();
        }
        return nombres;
    }
    
    public int obtenerTamano() {
        return canciones.size();
    }
}
