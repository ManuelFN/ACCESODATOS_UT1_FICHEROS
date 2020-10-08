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
import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        JFrame ventana = new JFrame("Trabajo Fichero");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(550,500);
        ventana.setResizable(false);

        JPanel panelPrincipal = new JPanel(new GridLayout(3,1));
        panelPrincipal.setBorder(new EmptyBorder(10,0,10,0));
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBorder(new EmptyBorder(60,60,0,60));
        JPanel panelCampo = new JPanel();
        panelCampo.setBorder(new EmptyBorder(0,60,0,60));
        JPanel panelBotones = new JPanel(new GridLayout(4,2));
        panelBotones.setBorder(new EmptyBorder(30,60,0,60));

        JLabel titulo = new JLabel("Programa creador de ficheros");
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setBorder(new EmptyBorder(0,200,30,200));
        JLabel etiInsert = new JLabel("Insertar contenido (opcional)");
        etiInsert.setBorder(new EmptyBorder(20,0,10,0));

        JButton botonCrear = new JButton("Crear fichero");
        JButton botonPropiedades = new JButton("Propiedades fichero");
        JButton botonInsertar = new JButton("Insertar texto (opcional)");
        JButton botonEditar = new JButton("Editar fichero");
        JButton botonContarPalabras = new JButton("Contar palabras");
        JButton botonContarVocales = new JButton("Contar vocales");
        JButton botonCifrar = new JButton("Cifrar fichero");
        JButton botonBorrarFich = new JButton("Borrar fichero");

        JTextArea campoCont = new JTextArea();
        campoCont.setPreferredSize(new Dimension(500,200));
        campoCont.setLineWrap(true);
        campoCont.setEditable(false);


        panelTitulo.add(titulo);
        panelCampo.add(campoCont);
        panelBotones.add(botonCrear);
        panelBotones.add(botonPropiedades);
        panelBotones.add(botonInsertar);
        panelBotones.add(botonEditar);
        panelBotones.add(botonContarPalabras);
        panelBotones.add(botonContarVocales);
        panelBotones.add(botonCifrar);
        panelBotones.add(botonBorrarFich);
        ventana.add(panelTitulo);
        ventana.add(panelCampo);
        ventana.add(panelBotones);
        panelPrincipal.add(panelTitulo);
        panelPrincipal.add(panelCampo);
        panelPrincipal.add(panelBotones);
        ventana.add(panelPrincipal);

        ventana.setVisible(true);

        JFileChooser ventanaFichero = new JFileChooser();
        ventanaFichero.setCurrentDirectory(new File("C:\\Users\\User-01\\Desktop"));

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
                        campoCont.setEditable(true);

                        escribir.close();
                    } catch (Exception o) {
                        o.printStackTrace();
                    }
                }
            }
        });

        botonPropiedades.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int seleccion = ventanaFichero.showOpenDialog(ventanaFichero.getParent());

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    JFrame ventanaSeleccion = new JFrame("Insertar información");
                    ventanaSeleccion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    ventanaSeleccion.setSize(300,320);
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

                    panelSeleccion.add(tituloPropFich);
                    panelSeleccion.add(campoInsert);
                    panelSeleccion.add(botonMostrarCont);
                    panelSeleccion.add(campoContenido);
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
                                        campoContenido.append(linea+ "\n");
                                        System.out.println(linea);
                                    }

                                    leer.close();

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

        botonInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String frase = campoCont.getText();
                    BufferedWriter escribir = new BufferedWriter(new FileWriter(ventanaFichero.getSelectedFile().getAbsolutePath()));
                    escribir.write(frase);
                    escribir.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        botonBorrarFich.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ventanaFichero.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int seleccion = ventanaFichero.showOpenDialog(ventanaFichero.getParent());

                    if (seleccion == JFileChooser.APPROVE_OPTION) {
                        File fichero = new File(ventanaFichero.getSelectedFile().getAbsolutePath());
                        fichero.delete();
                        if (fichero.exists()) {
                            campoCont.setText("No se ha podido borrar el fichero...");
                        } else {
                            campoCont.setText("Fichero borrado con exito...");
                        }

                    }

                } catch (Exception ex) {
                    System.out.println("Ha habido problemas " + ex.getMessage());
                }
            }
        });
    }
}
