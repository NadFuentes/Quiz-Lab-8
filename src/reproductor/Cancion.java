/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reproductor;

/**
 *
 * @author Nadiesda Fuentes
 */
import java.io.File;
/**
 *
 * @author Nadiesda Fuentes
 */
public class Cancion {
    private String nombre;
    private String artista;
    private String duracion;
    private File archivo;
    private String genero;
    private String rutaImagen;
    
    public Cancion(String nombre, String artista, String duracion, File archivo, String genero, String rutaImagen) {
        this.nombre = nombre;
        this.artista = artista;
        this.duracion = duracion;
        this.archivo = archivo;
        this.genero = genero;
        this.rutaImagen = rutaImagen;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getArtista() {
        return artista;
    }
    
    public String getDuracion() {
        return duracion;
    }
    
    public File getArchivo() {
        return archivo;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public String getRutaImagen() {
        return rutaImagen;
    }
}