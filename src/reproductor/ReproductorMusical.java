/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reproductor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;


/**
 *
 * @author Nadiesda Fuentes
 */
public class ReproductorMusical extends JFrame {
    private ListaReproduccion listaReproduccion;
    private ReproductorAudio reproductor;
    private JList<String> listaCanciones;
    private DefaultListModel<String> modeloLista;
    private JButton btnPlay, btnPause, btnStop, btnAdd, btnRemove;
    private JLabel lblImagen, lblNombre, lblArtista, lblDuracion, lblGenero;
    private int indiceSeleccionado = -1;
    private boolean reproduciendo = false;

    // Definición de colores para el tema oscuro
    private final Color COLOR_FONDO = new Color(25, 25, 25);         // Negro oscuro
    private final Color COLOR_PANEL = new Color(35, 35, 35);         // Negro menos oscuro
    private final Color COLOR_BOTONES = new Color(50, 50, 50);       // Gris oscuro
    private final Color COLOR_TEXTO = new Color(240, 240, 240);      // Blanco
    private final Color COLOR_BORDE = new Color(70, 70, 70);         // Gris medio
    private final Color COLOR_SELECCION = new Color(90, 90, 90);     // Gris claro

    public ReproductorMusical() {
        // Inicializar componentes
        listaReproduccion = new ListaReproduccion();
        reproductor = new ReproductorAudio();
        
        // Configurar la ventana principal
        setTitle("Reproductor Musical");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Aplicar tema oscuro
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Configurar el estilo con tonos oscuros
        configurarEstiloOscuro();
        
        // Panel de información de la canción
        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.setBackground(COLOR_PANEL);
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel panelDetalles = new JPanel(new GridLayout(4, 1, 0, 5));
        panelDetalles.setBackground(COLOR_PANEL);
        panelDetalles.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        
        lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(200, 200));
        lblImagen.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1));
        lblImagen.setHorizontalAlignment(JLabel.CENTER);
        lblImagen.setBackground(COLOR_FONDO);
        lblImagen.setOpaque(true);
        lblImagen.setForeground(COLOR_TEXTO);
        
        lblNombre = new JLabel("Nombre: ");
        lblArtista = new JLabel("Artista: ");
        lblDuracion = new JLabel("Duración: ");
        lblGenero = new JLabel("Género: ");
        
        lblNombre.setForeground(COLOR_TEXTO);
        lblArtista.setForeground(COLOR_TEXTO);
        lblDuracion.setForeground(COLOR_TEXTO);
        lblGenero.setForeground(COLOR_TEXTO);
        
        // Usar fuente más grande para los labels
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        lblNombre.setFont(labelFont);
        lblArtista.setFont(labelFont);
        lblDuracion.setFont(labelFont);
        lblGenero.setFont(labelFont);
        
        panelDetalles.add(lblNombre);
        panelDetalles.add(lblArtista);
        panelDetalles.add(lblDuracion);
        panelDetalles.add(lblGenero);
        
        panelInfo.add(lblImagen, BorderLayout.WEST);
        panelInfo.add(panelDetalles, BorderLayout.CENTER);
        
        // Panel de controles
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelControles.setBackground(COLOR_PANEL);
        panelControles.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        btnPlay = crearBoton("Play", "play");
        btnPause = crearBoton("Pause", "pause");
        btnStop = crearBoton("Stop", "stop");
        btnAdd = crearBoton("Add", "add");
        btnRemove = crearBoton("Remove", "remove");
        
        panelControles.add(btnPlay);
        panelControles.add(btnPause);
        panelControles.add(btnStop);
        panelControles.add(btnAdd);
        panelControles.add(btnRemove);
        
        // Lista de canciones
        modeloLista = new DefaultListModel<>();
        listaCanciones = new JList<>(modeloLista);
        listaCanciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaCanciones.setBackground(COLOR_FONDO);
        listaCanciones.setForeground(COLOR_TEXTO);
        listaCanciones.setSelectionBackground(COLOR_SELECCION);
        listaCanciones.setSelectionForeground(COLOR_TEXTO);
        listaCanciones.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        listaCanciones.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JScrollPane scrollLista = new JScrollPane(listaCanciones);
        scrollLista.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1));
        scrollLista.getViewport().setBackground(COLOR_FONDO);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(COLOR_FONDO);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelPrincipal.add(panelInfo, BorderLayout.NORTH);
        panelPrincipal.add(scrollLista, BorderLayout.CENTER);
        panelPrincipal.add(panelControles, BorderLayout.SOUTH);
        
        // Agregar al frame
        add(panelPrincipal);
        
        // Configurar eventos
        btnPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproducir();
            }
        });
        
        btnPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pausar();
            }
        });
        
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detener();
            }
        });
        
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCancion();
            }
        });
        
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCancion();
            }
        });
        
        listaCanciones.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                indiceSeleccionado = listaCanciones.getSelectedIndex();
                actualizarInfoCancion();
            }
        });
        
        // Agregar algunas canciones de ejemplo
        agregarCancionesEjemplo();
        
        // Mostrar la ventana
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // Método para configurar el estilo oscuro en los componentes Swing
    private void configurarEstiloOscuro() {
        UIManager.put("Panel.background", COLOR_PANEL);
        UIManager.put("OptionPane.background", COLOR_PANEL);
        UIManager.put("OptionPane.messageForeground", COLOR_TEXTO);
        UIManager.put("Label.foreground", COLOR_TEXTO);
        UIManager.put("TextField.background", COLOR_FONDO);
        UIManager.put("TextField.foreground", COLOR_TEXTO);
        UIManager.put("TextField.caretForeground", COLOR_TEXTO);
        UIManager.put("TextArea.background", COLOR_FONDO);
        UIManager.put("TextArea.foreground", COLOR_TEXTO);
        UIManager.put("Button.background", COLOR_BOTONES);
        UIManager.put("Button.foreground", COLOR_TEXTO);
        UIManager.put("List.background", COLOR_FONDO);
        UIManager.put("List.foreground", COLOR_TEXTO);
        UIManager.put("ScrollPane.background", COLOR_FONDO);
        UIManager.put("FileChooser.background", COLOR_PANEL);
        UIManager.put("FileChooser.foreground", COLOR_TEXTO);
    }
    

private JButton crearBoton(String tooltip, String nombreImagen) {
    // Crear un botón personalizado que solo muestra la imagen
    JButton boton = new JButton() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Dibujar el fondo del botón
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            
            // Cargar y dibujar la imagen directamente
            try {
                Image img = ImageIO.read(new File("Imagenes/" + nombreImagen + ".png"));
                int x = (getWidth() - 40) / 2;
                int y = (getHeight() - 40) / 2;
                g.drawImage(img, x, y, 40, 40, this);
            } catch (Exception e) {
                // En caso de error, mostrar el texto del tooltip
                g.setColor(getForeground());
                FontMetrics fm = g.getFontMetrics();
                int textWidth = fm.stringWidth(tooltip);
                int textHeight = fm.getHeight();
                g.drawString(tooltip, (getWidth() - textWidth) / 2, 
                             (getHeight() + textHeight) / 2 - fm.getDescent());
            }
        }
    };
    
    boton.setToolTipText(tooltip);
    boton.setBackground(COLOR_BOTONES);
    boton.setForeground(COLOR_TEXTO);
    boton.setFocusPainted(false);
    boton.setBorderPainted(false);
    boton.setPreferredSize(new Dimension(80, 40));
    boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
    // Variable para rastrear el estado
    final boolean[] isPressed = {false};
    final boolean[] isMouseOver = {false};
    
    // Agregar efectos para los eventos del ratón
    boton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            isMouseOver[0] = true;
            boton.setBackground(COLOR_SELECCION);
            boton.repaint();
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            isMouseOver[0] = false;
            boton.setBackground(COLOR_BOTONES);
            boton.repaint();
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            isPressed[0] = true;
            boton.setBackground(COLOR_BORDE);
            boton.repaint();
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            isPressed[0] = false;
            if (isMouseOver[0]) {
                boton.setBackground(COLOR_SELECCION);
            } else {
                boton.setBackground(COLOR_BOTONES);
            }
            boton.repaint();
        }
    });
    
    return boton;
}
    
    // Método para reproducir la canción seleccionada
    private void reproducir() {
        if (indiceSeleccionado != -1) {
            Cancion cancion = listaReproduccion.obtenerCancion(indiceSeleccionado);
            if (cancion != null) {
                reproductor.reproducir(cancion.getArchivo());
                reproduciendo = true;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una canción para reproducir", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
   // Método para pausar/reanudar la canción
private void pausar() {
    if (reproduciendo) {
        reproductor.pausar();
        
        // Actualizar el botón según el estado de reproducción
        if (reproductor.estaPausado()) {
            // Cambiamos a botón de play cuando está pausado
            btnPause = crearBotonPersonalizado("Resume", "play");
        } else {
            // Cambiamos a botón de pausa cuando está reproduciendo
            btnPause = crearBotonPersonalizado("Pause", "pause");
        }
        
        // Volver a agregar el ActionListener al nuevo botón
        btnPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pausar();
            }
        });
        
        // Reemplazar el botón en el panel de controles
        Container parent = btnPause.getParent();
        if (parent != null) {
            int index = -1;
            Component[] components = parent.getComponents();
            for (int i = 0; i < components.length; i++) {
                if (components[i] == btnPause) {
                    index = i;
                    break;
                }
            }
            
            if (index >= 0) {
                parent.remove(index);
                parent.add(btnPause, index);
                parent.revalidate();
                parent.repaint();
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "No hay ninguna canción reproduciéndose", "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
}

// Método auxiliar para crear un botón personalizado para pausar/reproducir
private JButton crearBotonPersonalizado(String tooltip, String nombreImagen) {
    return crearBoton(tooltip, nombreImagen);
}

    
    // Método para detener la canción
    private void detener() {
        if (reproduciendo) {
            reproductor.detener();
            reproduciendo = false;
            btnPause.setText("⏸");
            btnPause.setToolTipText("Pause");
        }
    }
    
    // Método para agregar una canción
    private void agregarCancion() {
        // Abrir diálogo para seleccionar archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo de música");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) return true;
                String name = f.getName().toLowerCase();
                return name.endsWith(".mp3") || name.endsWith(".wav");
            }
            
            @Override
            public String getDescription() {
                return "Archivos de audio (*.mp3, *.wav)";
            }
        });
        
        int resultado = fileChooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            
            // Crear diálogo para ingresar datos de la canción
            JTextField txtNombre = new JTextField();
            JTextField txtArtista = new JTextField();
            JTextField txtDuracion = new JTextField();
            JTextField txtGenero = new JTextField();
            JTextField txtRutaImagen = new JTextField();
            JButton btnExaminar = new JButton("Examinar");
            
            // Aplicar estilos oscuros a los componentes
            txtNombre.setBackground(COLOR_FONDO);
            txtArtista.setBackground(COLOR_FONDO);
            txtDuracion.setBackground(COLOR_FONDO);
            txtGenero.setBackground(COLOR_FONDO);
            txtRutaImagen.setBackground(COLOR_FONDO);
            
            txtNombre.setForeground(COLOR_TEXTO);
            txtArtista.setForeground(COLOR_TEXTO);
            txtDuracion.setForeground(COLOR_TEXTO);
            txtGenero.setForeground(COLOR_TEXTO);
            txtRutaImagen.setForeground(COLOR_TEXTO);
            
            btnExaminar.setBackground(COLOR_BOTONES);
            btnExaminar.setForeground(COLOR_TEXTO);
            
            // Panel para la ruta de la imagen
            JPanel panelImagen = new JPanel(new BorderLayout());
            panelImagen.setBackground(COLOR_PANEL);
            panelImagen.add(txtRutaImagen, BorderLayout.CENTER);
            panelImagen.add(btnExaminar, BorderLayout.EAST);
            
            btnExaminar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser imagenChooser = new JFileChooser();
                    imagenChooser.setDialogTitle("Seleccionar imagen");
                    imagenChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                        @Override
                        public boolean accept(File f) {
                            if (f.isDirectory()) return true;
                            String name = f.getName().toLowerCase();
                            return name.endsWith(".jpg") || name.endsWith(".jpeg") || 
                                   name.endsWith(".png") || name.endsWith(".gif");
                        }
                        
                        @Override
                        public String getDescription() {
                            return "Imágenes (*.jpg, *.jpeg, *.png, *.gif)";
                        }
                    });
                    
                    int res = imagenChooser.showOpenDialog(ReproductorMusical.this);
                    
                    if (res == JFileChooser.APPROVE_OPTION) {
                        File imagenArchivo = imagenChooser.getSelectedFile();
                        txtRutaImagen.setText(imagenArchivo.getAbsolutePath());
                    }
                }
            });
            
            // Crear el panel del formulario
            JPanel panel = new JPanel(new GridLayout(0, 1, 0, 5));
            panel.setBackground(COLOR_PANEL);
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            // Crear labels con el estilo oscuro
            JLabel lblNombreForm = new JLabel("Nombre de la canción:");
            JLabel lblArtistaForm = new JLabel("Artista:");
            JLabel lblDuracionForm = new JLabel("Duración (mm:ss):");
            JLabel lblGeneroForm = new JLabel("Género:");
            JLabel lblImagenForm = new JLabel("Imagen de la canción:");
            
            lblNombreForm.setForeground(COLOR_TEXTO);
            lblArtistaForm.setForeground(COLOR_TEXTO);
            lblDuracionForm.setForeground(COLOR_TEXTO);
            lblGeneroForm.setForeground(COLOR_TEXTO);
            lblImagenForm.setForeground(COLOR_TEXTO);
            
            panel.add(lblNombreForm);
            panel.add(txtNombre);
            panel.add(lblArtistaForm);
            panel.add(txtArtista);
            panel.add(lblDuracionForm);
            panel.add(txtDuracion);
            panel.add(lblGeneroForm);
            panel.add(txtGenero);
            panel.add(lblImagenForm);
            panel.add(panelImagen);
            
            // Configurar el JOptionPane con estilo oscuro
            UIManager.put("OptionPane.background", COLOR_PANEL);
            UIManager.put("Panel.background", COLOR_PANEL);
            
            // Mostrar el diálogo
            int opcion = JOptionPane.showConfirmDialog(this, panel, "Agregar Canción", JOptionPane.OK_CANCEL_OPTION);
            
            if (opcion == JOptionPane.OK_OPTION) {
                // Crear y agregar la nueva canción
                Cancion nuevaCancion = new Cancion(
                    txtNombre.getText(),
                    txtArtista.getText(),
                    txtDuracion.getText(),
                    archivo,
                    txtGenero.getText(),
                    txtRutaImagen.getText()
                );
                
                listaReproduccion.agregarCancion(nuevaCancion);
                actualizarListaCanciones();
            }
        }
    }
    
    // Método para eliminar la canción seleccionada
    private void eliminarCancion() {
        if (indiceSeleccionado != -1) {
            // Si es la canción que se está reproduciendo, detenerla
            if (reproduciendo && listaReproduccion.obtenerCancion(indiceSeleccionado) != null) {
                detener();
            }
            
            listaReproduccion.eliminarCancion(indiceSeleccionado);
            actualizarListaCanciones();
            
            // Resetear la selección
            indiceSeleccionado = -1;
            actualizarInfoCancion();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una canción para eliminar", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Método para actualizar la lista de canciones
    private void actualizarListaCanciones() {
        modeloLista.clear();
        String[] nombres = listaReproduccion.obtenerNombresCanciones();
        for (String nombre : nombres) {
            modeloLista.addElement(nombre);
        }
    }
    
    // Método para actualizar la información de la canción seleccionada
    private void actualizarInfoCancion() {
        if (indiceSeleccionado != -1) {
            Cancion cancion = listaReproduccion.obtenerCancion(indiceSeleccionado);
            if (cancion != null) {
                lblNombre.setText("Nombre: " + cancion.getNombre());
                lblArtista.setText("Artista: " + cancion.getArtista());
                lblDuracion.setText("Duración: " + cancion.getDuracion());
                lblGenero.setText("Género: " + cancion.getGenero());
                
                // Cargar la imagen
                String rutaImagen = cancion.getRutaImagen();
                if (rutaImagen != null && !rutaImagen.isEmpty()) {
                    try {
                        ImageIcon icono = new ImageIcon(rutaImagen);
                        Image imagen = icono.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        lblImagen.setIcon(new ImageIcon(imagen));
                        lblImagen.setText("");
                    } catch (Exception e) {
                        lblImagen.setIcon(null);
                        lblImagen.setText("Sin imagen");
                    }
                } else {
                    lblImagen.setIcon(null);
                    lblImagen.setText("Sin imagen");
                }
            }
        } else {
            // Limpiar la información
            lblNombre.setText("Nombre: ");
            lblArtista.setText("Artista: ");
            lblDuracion.setText("Duración: ");
            lblGenero.setText("Género: ");
            lblImagen.setIcon(null);
            lblImagen.setText("Sin imagen");
        }
    }
    
    // Método para agregar canciones de ejemplo
private void agregarCancionesEjemplo() {
    try {
        // Usar rutas absolutas a los archivos que realmente existen en tu sistema
        // Cambia estas rutas a donde realmente tienes los archivos MP3
        File archivoEjemplo1 = new File("C:\\Users\\Nadiesda Fuentes\\OneDrive\\Escritorio\\Progra II\\Reproductor\\src\\Canciones/Espresso.mp3");
        String rutaImagen1 = "C:\\Users\\Nadiesda Fuentes\\OneDrive\\Escritorio\\Progra II\\Reproductor\\src\\Imagenes/espresso.png";
        
        // Verificar si el archivo existe
        if (!archivoEjemplo1.exists()) {
            System.out.println("El archivo no existe: " + archivoEjemplo1.getAbsolutePath());
            // Crear un archivo ficticio que no se reproducirá pero permitirá que se muestre en la lista
            archivoEjemplo1 = new File("Espresso.mp3");
        }
        
        Cancion ejemplo1 = new Cancion(
            "Espresso",
            "Sabrina Carpenter",
            "2:55",
            archivoEjemplo1,
            "Pop",
            rutaImagen1
        );
        
        File archivoEjemplo2 = new File("C:\\Users\\Nadiesda Fuentes\\OneDrive\\Escritorio\\Progra II\\Reproductor\\src\\Canciones/youngblood.mp3");
        String rutaImagen2 = "C:\\Users\\Nadiesda Fuentes\\OneDrive\\Escritorio\\Progra II\\Reproductor\\src\\Imagenes/youngblood.png";
        
        // Verificar si el archivo existe
        if (!archivoEjemplo2.exists()) {
            System.out.println("El archivo no existe: " + archivoEjemplo2.getAbsolutePath());
            // Crear un archivo ficticio que no se reproducirá pero permitirá que se muestre en la lista
            archivoEjemplo2 = new File("youngblood.mp3");
        }
        
        Cancion ejemplo2 = new Cancion(
            "YoungBlood",
            "5 Second Of Summer",
            "2:30",
            archivoEjemplo2,
            "Pop Rock",
            rutaImagen2
        );
        
        // Agregar a la lista de reproducción
        listaReproduccion.agregarCancion(ejemplo1);
        listaReproduccion.agregarCancion(ejemplo2);
        
        // Actualizar la lista visual
        actualizarListaCanciones();
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            "Error al cargar canciones de ejemplo: " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Mostrar mensaje informativo
    JOptionPane.showMessageDialog(this, 
        "Se han agregado dos canciones de ejemplo.\n\n" +
        "Nota: Estas canciones son solo para demostración y no tienen\n" +
        "archivos de audio reales asociados. Para reproducir música,\n" +
        "usa el botón '+' para agregar tus propios archivos MP3.", 
        "Canciones de Ejemplo", JOptionPane.INFORMATION_MESSAGE);
}

// Método principal para iniciar la aplicación
public static void main(String[] args) {
    // Usar SwingUtilities para asegurar que la GUI se crea en el hilo de eventos de Swing
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            new ReproductorMusical();
        }
    });
}
}