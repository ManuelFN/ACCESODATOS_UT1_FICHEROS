package AccesoDatos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

public class Main {

    public static void main(String[] args) {

        JFrame marco = new JFrame("UT1 Ficheros - Acceso a Datos");

        JPanel panel_principal = new JPanel();
        JPanel panel_titulo = new JPanel();
        panel_titulo.setBorder(new EmptyBorder(0, 0, 0, 0));
        JPanel panel_crear = new JPanel();
        panel_crear.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel panel_opcional = new JPanel();

        JButton boton_crear = new JButton("Crear archivo");
        JButton boton_contenido_opcional = new JButton("Insertar");
        JButton boton_editar = new JButton("Editar Contenido");
        //JButton boton_seleccionar = new JButton("Seleccionar Archivo");
        JButton boton_borrar = new JButton("Borrar Archivo");

        //JTextField tf_archivo_seleccionado = new JTextField(20);
        //tf_archivo_seleccionado.setEditable(false);

        JLabel titulo = new JLabel("Ficheros");
        titulo.setFont(new Font("Verdana", Font.PLAIN, 45));
        JLabel lb_estado_crear = new JLabel();
        //JLabel lb_opcional = new JLabel("Contenido (Opcional)");


        //panel_opcional.setBorder(new EmptyBorder(0, 0, 0, 50));
        //lb_opcional.setBorder(new EmptyBorder(0, 0, 0, 10));

        marco.add(panel_principal);
        panel_principal.add(panel_titulo);
        panel_principal.add(panel_crear);
        panel_principal.add(panel_opcional);

        panel_titulo.add(titulo);

        panel_crear.add(boton_crear);
        panel_crear.add(lb_estado_crear);
        panel_crear.add(boton_borrar);
        panel_crear.add(boton_editar);
        //panel_crear.add(boton_seleccionar);
        //panel_crear.add(tf_archivo_seleccionado);

        //panel_opcional.add(lb_opcional);
        //panel_opcional.add(boton_contenido_opcional);

        boton_crear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearArchivo();
                lb_estado_crear.setText("Archivo Creado");
            }
        });

        /*boton_seleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser filechooser = new JFileChooser();
                    filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int seleccion = filechooser.showOpenDialog(filechooser.getParent());

                    if (seleccion == JFileChooser.APPROVE_OPTION) {
                        File fichero = new File(filechooser.getSelectedFile().getAbsolutePath());

                        FileReader leer = new FileReader(fichero);
                        BufferedReader buff_rd = new BufferedReader(leer);

                        leer.close();
                        buff_rd.close();

                        tf_archivo_seleccionado.setText(filechooser.getSelectedFile().getName());
                    }

                } catch (Exception ex) {
                    System.out.println("Ha habido problemas " + ex.getMessage());
                }
            }
        });*/

        boton_contenido_opcional.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser filechooser = new JFileChooser();
                    File fichero = new File(filechooser.getSelectedFile().getAbsolutePath());

                    FileWriter escribir = new FileWriter(fichero);
                    BufferedWriter buff_wr = new BufferedWriter(escribir);

                    //buff_wr.write(txt_area.getText());

                    escribir.close();
                    buff_wr.close();
                } catch (Exception ex) {
                    System.out.println("Ha habido problemas " + ex.getMessage());
                }
            }
        });

        boton_borrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser filechooser = new JFileChooser();
                    filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int seleccion = filechooser.showOpenDialog(filechooser.getParent());

                    if (seleccion == JFileChooser.APPROVE_OPTION) {
                        File fichero = new File(filechooser.getSelectedFile().getAbsolutePath());

                        fichero.delete();
                    }

                } catch (Exception ex) {
                    System.out.println("Ha habido problemas " + ex.getMessage());
                }
            }
        });

        boton_editar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    JFrame marco = new JFrame("Editar Contenido");

                    JPanel panel_p = new JPanel();

                    JTextArea txt_area = new JTextArea();
                    txt_area.setLineWrap(true);
                    JScrollPane sp = new JScrollPane(txt_area);
                    txt_area.setPreferredSize(new Dimension(400, 120));

                    JButton boton_insertar = new JButton("Editar");

                    JFileChooser filechooser = new JFileChooser();
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
                                } catch (Exception ex) {
                                    System.out.println("Ha habido problemas " + ex.getMessage());
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
                    System.out.println("Ha habido problemas " + ex.getMessage());
                }
            }
        });

        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel_principal.setLayout(new GridLayout(4,1));
        marco.setLayout(new GridLayout(1,1));
        marco.setSize(650, 400);
        marco.setVisible(true);
    }

    public static File elegirArchivo() {
        try {
            JFileChooser filechooser = new JFileChooser();
            filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int seleccion = filechooser.showOpenDialog(filechooser.getParent());

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File fichero = new File(filechooser.getSelectedFile().getAbsolutePath());

                return fichero;
            }

        } catch (Exception ex) {
            System.out.println("Ha habido problemas " + ex.getMessage());
        }
        return null;
    }

    public static void crearArchivo() {
        try {

            JFrame v_datos = new JFrame("Propiedades del archivo");

            JLabel jl_nombre = new JLabel("Nombre");
            JLabel jl_formato = new JLabel("Formato o extensión");
            JLabel jl_ruta = new JLabel("Ruta absoluta");
            JLabel jl_tamaño = new JLabel("Tamaño");
            JLabel jl_ult_modif = new JLabel("Última modificación");

            JTextField tf_nombre = new JTextField();
            tf_nombre.setEditable(false);

            JTextField tf_formato = new JTextField();
            tf_formato.setEditable(false);

            JTextField tf_ruta = new JTextField();
            tf_ruta.setEditable(false);

            JTextField tf_tamaño = new JTextField();
            tf_tamaño.setEditable(false);

            JTextField tf_ult_modf = new JTextField();
            tf_ult_modf.setEditable(false);

            JFileChooser filechooser = new JFileChooser();
            filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int seleccion = filechooser.showSaveDialog(filechooser.getParent());

            //Path ruta = Paths.get(filechooser.getSelectedFile().getAbsolutePath());
            //FileTime tiempo = Files.getLastModifiedTime(ruta);

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File fichero = new File(filechooser.getSelectedFile().getAbsolutePath());

                tf_nombre.setText(filechooser.getSelectedFile().getName());
                tf_formato.setText(getFileExtension(filechooser.getSelectedFile()));
                tf_ruta.setText(filechooser.getSelectedFile().getAbsolutePath());
                tf_tamaño.setText(getFileSizeKiloBytes(filechooser.getSelectedFile()));
                //tf_ult_modf.setText(String.valueOf(tiempo));

                FileWriter escribir = new FileWriter(fichero);
                BufferedWriter buff_wr = new BufferedWriter(escribir);

                fichero.createNewFile();

                escribir.close();
                buff_wr.close();
            }

            v_datos.add(jl_nombre);
            v_datos.add(tf_nombre);
            v_datos.add(jl_formato);
            v_datos.add(tf_formato);
            v_datos.add(jl_ruta);
            v_datos.add(tf_ruta);
            v_datos.add(jl_tamaño);
            v_datos.add(tf_tamaño);
            v_datos.add(jl_ult_modif);
            v_datos.add(tf_ult_modf);

            v_datos.setLayout(new GridLayout(10,1));
            v_datos.setSize(400, 700);
            v_datos.setVisible(true);

        } catch (Exception ex) {
            System.out.println("Ha habido problemas " + ex.getMessage());
        }
    }

    private static String getFileExtension(File file) {
        String archivo = file.getName();

        if (archivo.lastIndexOf(".") != -1 && archivo.lastIndexOf(".") != 0)
            return archivo.substring(archivo.lastIndexOf(".") + 1);
        else return "";
    }

    private static String getFileSizeKiloBytes(File file) {
        return (double) file.length() / 1024 + "  kb";
    }

    public static void editarContenido() {
        try {
            JFileChooser filechooser = new JFileChooser();
            filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int seleccion = filechooser.showSaveDialog(filechooser.getParent());

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File fichero = new File(filechooser.getSelectedFile().getAbsolutePath());

                FileWriter escribir = new FileWriter(fichero);
                BufferedWriter buff_wr = new BufferedWriter(escribir);
                fichero.createNewFile();

                escribir.close();
                buff_wr.close();
            }

        } catch (Exception ex) {
            System.out.println("Ha habido problemas " + ex.getMessage());
        }
    }

    public static void copiarArchivo() {

    }

    public static void contarPalabras() {

    }

    public static void contarVocales() {

    }

    public static void cifrarArchivo() {

    }
}
