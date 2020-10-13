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
import java.util.StringTokenizer;

public class main extends Component{
    public static void main(String[] args) {

        main m = new main();

        m.cambiarLookAndFeel();

        JFrame ventana = new JFrame("Trabajo Fichero");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(550,520);
        ventana.setResizable(false);

        Image icon = Toolkit.getDefaultToolkit().getImage("text_icon.png");
        ventana.setIconImage(icon);

        JPanel panelPrincipal = new JPanel(new GridLayout(3,1));
        panelPrincipal.setBorder(new EmptyBorder(10,0,10,0));
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBorder(new EmptyBorder(60,60,0,60));
        JPanel panelCampo = new JPanel();
        panelCampo.setBorder(new EmptyBorder(0,60,0,60));
        JPanel panelBotones = new JPanel(new GridLayout(4,3));
        panelBotones.setBorder(new EmptyBorder(30,60,20,60));

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
        JButton botonCopiarFichero = new JButton("Copiar fichero");

        JTextArea campoCont = new JTextArea();
        campoCont.setPreferredSize(new Dimension(500,100));
        campoCont.setLineWrap(true);
        campoCont.setEditable(false);
        JLabel campoInfo = new JLabel();


        panelTitulo.add(titulo);
        panelCampo.add(campoCont);
        panelTitulo.add(campoInfo);
        panelBotones.add(botonCrear);
        panelBotones.add(botonPropiedades);
        panelBotones.add(botonCopiarFichero);
        panelBotones.add(botonEditar);
        panelBotones.add(botonContarPalabras);
        panelBotones.add(botonContarVocales);
        panelBotones.add(botonCifrar);
        panelBotones.add(botonBorrarFich);
        ventana.add(panelTitulo);
        ventana.add(panelCampo);
        ventana.add(panelBotones);
        panelPrincipal.add(panelTitulo);
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
                panelPrincipal.add(panelCampo);
                panelCampo.add(botonInsertar);
            }
        });

        botonPropiedades.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int seleccion = ventanaFichero.showOpenDialog(ventanaFichero.getParent());

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    JFrame ventanaSeleccion = new JFrame("Propiedades Fichero");
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
                    campoInfo.setText("Texto insertado correctamente");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        botonCopiarFichero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser filechooser = new JFileChooser();

                filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int seleccion = filechooser.showOpenDialog(filechooser.getParent());

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File fichero = new File(filechooser.getSelectedFile().getAbsolutePath());

                    copiarArchivos(fichero, filechooser);
                }
            }
        });

        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                editarArchivos(campoInfo);

            }
        });

        botonContarPalabras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ventanaFichero.showOpenDialog(ventanaFichero.getParent());

                    BufferedReader fichero = new BufferedReader(new FileReader(ventanaFichero.getSelectedFile().getAbsolutePath()));

                    int codigo = fichero.read();
                    int cont = 0;
                    String linea = " ";

                    while (linea != null) {
                        cont = cont + linea.split(" ").length;
                        linea = fichero.readLine();
                    }

                        campoInfo.setText("El txt tiene "+cont+" palabra/s.");

                    fichero.close();
                } catch (Exception a) {
                    System.out.println(e);
                }
            }
        });

        botonContarVocales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ventanaFichero.showOpenDialog(ventanaFichero.getParent());

                    BufferedReader leer = new BufferedReader(new FileReader(ventanaFichero.getSelectedFile().getAbsolutePath()));

                    String texto = null;

                    String lineaSalida = "";
                    StringBuffer contenido = new StringBuffer();
                    String separador = "";

                    while ((lineaSalida = leer.readLine()) != null)
                    {
                        contenido.append(separador + lineaSalida);
                        separador = "\n";
                    }

                    texto = contenido.toString();

                    int cont = 0;
                    for(int x=0;x<texto.length();x++) {
                        if ((texto.charAt(x)=='a') || (texto.charAt(x)=='e') || (texto.charAt(x)=='i') || (texto.charAt(x)=='o') || (texto.charAt(x)=='u')){
                            cont++;
                        }
                    }

                    campoInfo.setText("El txt tiene "+String.valueOf(cont)+" vocal/es.");

                    leer.close();
                } catch (IOException y) {
                    y.printStackTrace();
                }
            }
        });

        botonCifrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ventanaFichero.showOpenDialog(ventanaFichero.getParent());
                    BufferedReader leer = new BufferedReader(new FileReader(ventanaFichero.getSelectedFile().getAbsolutePath()));

                    String texto = null;

                    String lineaSalida = "";
                    StringBuffer contenido = new StringBuffer();
                    String separador = "";

                    while ((lineaSalida = leer.readLine()) != null)
                    {
                        contenido.append(separador + lineaSalida);
                        separador = "\n";
                    }

                    texto = contenido.toString();

                    StringBuilder sb = new StringBuilder();

                    sb.append(texto);

                    System.out.println(sb.reverse());

                } catch (Exception fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
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
                            campoInfo.setText("No se ha podido borrar el fichero");
                        } else {
                            campoInfo.setText("Fichero borrado con exito");
                        }
                    }

                } catch (Exception ex) {
                    System.out.println("Ha habido problemas " + ex.getMessage());
                }
            }
        });
    }

    public static void copiarArchivos(File fichero, JFileChooser filechooser) {

        String ruta = filechooser.getSelectedFile().getAbsolutePath();
        String nombre_archivo = filechooser.getSelectedFile().getName();
        String ruta_sin_nombre_archivo = ruta.substring(0, ruta.length() - filechooser.getSelectedFile().getName().length());

        try {

            FileReader leer = new FileReader(fichero);
            BufferedReader buff_rd = new BufferedReader(leer);

            FileWriter escribir = new FileWriter(ruta_sin_nombre_archivo + "COPIA_" + nombre_archivo);

            String linea;
            while ((linea = buff_rd.readLine()) != null) {
                escribir.write(linea);
            }

            buff_rd.close();
            leer.close();
            escribir.close();

        } catch (Exception ex) {
            System.out.println("Problemas: " + ex.getMessage());
        }
    }

    public static void editarArchivos(JLabel campoInfo) {
        JFileChooser filechooser = new JFileChooser();

        try {

            JFrame marco = new JFrame("Editar Contenido");

            JPanel panel_p = new JPanel();

            JTextArea txt_area = new JTextArea();
            txt_area.setLineWrap(true);
            JScrollPane sp = new JScrollPane(txt_area);
            txt_area.setPreferredSize(new Dimension(400, 120));

            JButton boton_insertar = new JButton("Editar");

            filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int seleccion = filechooser.showOpenDialog(filechooser.getParent());

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File fichero = new File(filechooser.getSelectedFile().getAbsolutePath());

                FileReader leer = new FileReader(fichero);
                BufferedReader buff_rd = new BufferedReader(leer);

                String linea;
                while ((linea = buff_rd.readLine()) != null) {
                    txt_area.append(linea + "\n");
                }

                boton_insertar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            FileWriter escribir = new FileWriter(fichero);

                            String contenido_editado = txt_area.getText();

                            escribir.write(contenido_editado);

                            escribir.close();

                            marco.dispose();
                            campoInfo.setText("Fichero Editado");
                        } catch (Exception ex) {
                            System.out.println("Error: " + ex.getMessage());
                        }
                    }
                });

                leer.close();
                buff_rd.close();

            }

            panel_p.setBorder(new EmptyBorder(15, 0, 0, 0));

            marco.add(panel_p);
            panel_p.add(sp);
            panel_p.add(boton_insertar);

            marco.setLayout(new GridLayout(1,1));
            marco.setSize(500, 200);
            marco.setVisible(true);

        } catch (Exception ex) {
            campoInfo.setText("Error: " + ex.getMessage());
        }
    }

    public void cambiarLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (Throwable e) {
            JOptionPane.showConfirmDialog(this, "Error en Look And Feel");
        }
    }

    public static String rotate(String s, int offset) {
        int i = offset % s.length();
        return s.substring(i) + s.substring(0, i);
    }

}
