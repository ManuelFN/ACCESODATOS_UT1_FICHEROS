import com.sun.jdi.Value;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

public class main {
    public static void main(String[] args) {

        JFrame ventana = new JFrame("Trabajo Fichero");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(300,200);
        ventana.setResizable(false);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(new EmptyBorder(30,60,0,60));

        JLabel titulo = new JLabel("Programa creador de ficheros");
        titulo.setBorder(new EmptyBorder(0,0,30,0));

        JButton botonCrear = new JButton("Crear fichero");
        JButton botonSeleccionar = new JButton("Seleccionar fichero");

        panelPrincipal.add(titulo);
        panelPrincipal.add(botonCrear);
        panelPrincipal.add(botonSeleccionar);
        ventana.add(panelPrincipal);

        ventana.setVisible(true);

        JFileChooser ventanaFichero = new JFileChooser();

        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int seleccion = ventanaFichero.showSaveDialog(ventanaFichero.getParent());

                if (seleccion == JFileChooser.FILES_ONLY) {
                    try {
                        String rutaFich = ventanaFichero.getSelectedFile().getAbsolutePath();
                        String nombreFich = ventanaFichero.getSelectedFile().getName();
                        File fichero = new File(rutaFich);
                        BufferedWriter escribir = new BufferedWriter(new FileWriter(nombreFich));
                        fichero.createNewFile();
                        escribir.close();
                    } catch (Exception o) {
                        o.printStackTrace();
                    }
                }
            }
        });

        botonSeleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int seleccion = ventanaFichero.showOpenDialog(ventanaFichero.getParent());

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    JFrame ventanaSeleccion = new JFrame("Insertar información");
                    ventanaSeleccion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    ventanaSeleccion.setSize(300,400);
                    ventanaSeleccion.setResizable(false);

                    JPanel panelSeleccion = new JPanel();

                    JLabel tituloPropFich = new JLabel("Propiedades del fichero:");
                    JTextArea campoInsert = new JTextArea();
                    JTextArea campoContenido = new JTextArea();
                    campoInsert.setPreferredSize(new Dimension(250,100));
                    campoContenido.setPreferredSize(new Dimension(250,100));
                    campoInsert.setEditable(false);
                    campoContenido.setEditable(false);
                    JButton botonMostrarCont = new JButton("Mostrar contenido");
                    JButton botonBorrarFich = new JButton("Borrar fichero");

                    panelSeleccion.add(tituloPropFich);
                    panelSeleccion.add(campoInsert);
                    panelSeleccion.add(botonMostrarCont);
                    panelSeleccion.add(campoContenido);
                    panelSeleccion.add(botonBorrarFich);
                    ventanaSeleccion.add(panelSeleccion);

                    ventanaSeleccion.setVisible(true);
                    ventana.setVisible(false);

                    Path ruta = Paths.get(ventanaFichero.getSelectedFile().getAbsolutePath());
                    FileTime fech = null;
                    try {
                        fech = Files.getLastModifiedTime(ruta);

                        campoInsert.setText("Nombre: " +ventanaFichero.getSelectedFile().getName()+ "\nExtensión: " +ventanaFichero.getSelectedFile().getName().substring(ventanaFichero.getSelectedFile().getName().lastIndexOf("."))+ "\nRuta: " +ventanaFichero.getSelectedFile().getAbsolutePath()+ "\nTamaño en disco: " +String.valueOf(ventanaFichero.getSelectedFile().getTotalSpace())+ " Kb\nÚltima modificación: " +(fech));

                        botonMostrarCont.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    BufferedReader leer = new BufferedReader(new FileReader(ventanaFichero.getSelectedFile().getAbsolutePath()));

                                    String linea;
                                    while ((linea = leer.readLine()) != null) {
                                        campoContenido.append(linea + "\n");
                                        System.out.println(linea);
                                    }

                                    leer.close();

                                    botonBorrarFich.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {

                                        }
                                    });

                                }catch (IOException u) {
                                    u.printStackTrace();
                                }
                            }
                        });

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }
}
