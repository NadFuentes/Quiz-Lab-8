/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reproductor;

import java.io.File;
import java.io.FileInputStream;
import javazoom.jl.player.Player;
/**
 *
 * @author Nadiesda Fuentes
 */
public class ReproductorAudio {
    private Player player;
    private FileInputStream fileInputStream;
    private boolean paused;
    private File currentFile;
    private long pausePosition;
    private Thread playerThread;
    
    public ReproductorAudio() {
        paused = false;
        pausePosition = 0;
    }
    
    public void reproducir(File archivo) {
        try {
            // Si hay algo reproduciendo, detenerlo
            detener();
            
            currentFile = archivo;
            fileInputStream = new FileInputStream(currentFile);
            player = new Player(fileInputStream);
            paused = false;
            
            // Usar un hilo para la reproducción para no bloquear la interfaz
            playerThread = new Thread(() -> {
                try {
                    player.play();
                } catch (Exception e) {
                    System.out.println("Error al reproducir: " + e.getMessage());
                }
            });
            
            playerThread.start();
        } catch (Exception e) {
            System.out.println("Error al iniciar reproducción: " + e.getMessage());
        }
    }
    
    public void pausar() {
        if (player != null) {
            try {
                if (!paused) {
                    // Pausar la reproducción
                    pausePosition = fileInputStream.available();
                    detener();
                    paused = true;
                } else {
                    // Continuar la reproducción desde donde se pausó
                    fileInputStream = new FileInputStream(currentFile);
                    
                    // Saltar hasta la posición donde se pausó
                    long bytesToSkip = currentFile.length() - pausePosition;
                    fileInputStream.skip(bytesToSkip);
                    
                    player = new Player(fileInputStream);
                    
                    playerThread = new Thread(() -> {
                        try {
                            player.play();
                        } catch (Exception e) {
                            System.out.println("Error al continuar reproducción: " + e.getMessage());
                        }
                    });
                    
                    playerThread.start();
                    paused = false;
                }
            } catch (Exception e) {
                System.out.println("Error en pausar/continuar: " + e.getMessage());
            }
        }
    }
    
    public void detener() {
        if (player != null) {
            player.close();
            player = null;
            
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                    fileInputStream = null;
                }
                
                if (playerThread != null && playerThread.isAlive()) {
                    playerThread.interrupt();
                }
            } catch (Exception e) {
                System.out.println("Error al detener: " + e.getMessage());
            }
        }
        paused = false;
    }
    
    public boolean estaPausado() {
        return paused;
    }
}