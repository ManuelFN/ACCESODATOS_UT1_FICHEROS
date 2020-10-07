package AccesoDatos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;

public class Main {

    static JFileChooser filechooser = new JFileChooser();

    public static void main(String[] args) {

        JFrame marco = new JFrame("UT1 Ficheros - Acceso a Datos");

        JPanel panel_principal = new JPanel();
        JPanel panel_titulo = new JPanel();
        panel_titulo.setBorder(new EmptyBorder(0, 0, 0, 0));
        JPanel panel_crear = new JPanel();
        panel_crear.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel panel_opcional = new JPanel();

        JTextArea txt_area = new JTextArea("Introduce el contenido que quieras");
        txt_area.setLineWrap(true);
        JScrollPane sp = new JScrollPane(txt_area);
        txt_area.setPreferredSize(new Dimension(400, 100));

        JButton boton_crear = new JButton("Crear archivo");
        JButton boton_contenido_opcional = new JButton("Insertar");
        JButton boton_seleccionar = new JButton("Seleccionar Archivo");

        JTextField tf_archivo_seleccionado = new JTextField(20);
        tf_archivo_seleccionado.setEditable(false);

        JLabel titulo = new JLabel("Ficheros");
        titulo.setFont(new Font("Verdana", Font.PLAIN, 45));
        JLabel lb_estado_crear = new JLabel();
        JLabel lb_opcional = new JLabel("Contenido (Opcional)");


        panel_opcional.setBorder(new EmptyBorder(0, 0, 0, 50));
        lb_opcional.setBorder(new EmptyBorder(0, 0, 0, 10));

        marco.add(panel_principal);
        panel_principal.add(panel_titulo);
        panel_principal.add(panel_crear);
        panel_principal.add(panel_opcional);

        panel_titulo.add(titulo);

        panel_crear.add(boton_crear);
        panel_crear.add(lb_estado_crear);
        panel_crear.add(boton_seleccionar);
        panel_crear.add(tf_archivo_seleccionado);

        panel_opcional.add(lb_opcional);
        panel_opcional.add(sp);
        panel_opcional.add(boton_contenido_opcional);

        boton_crear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearArchivo();
                lb_estado_crear.setText("Archivo Creado");
            }
        });

        boton_seleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
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
        });

        boton_contenido_opcional.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File fichero = new File(filechooser.getSelectedFile().getAbsolutePath());

                    FileWriter escribir = new FileWriter(fichero);
                    BufferedWriter buff_wr = new BufferedWriter(escribir);

                    buff_wr.write(txt_area.getText());

                    escribir.close();
                    buff_wr.close();
                } catch (Exception ex) {
                    System.out.println("Ha habido problemas " + ex.getMessage());
                }
            }
        });

        txt_area.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                txt_area.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel_principal.setLayout(new GridLayout(4,1));
        marco.setLayout(new GridLayout(1,1));
        marco.setSize(650, 700);
        marco.setVisible(true);
    }

    public static File elegirArchivo() {
        try {
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
