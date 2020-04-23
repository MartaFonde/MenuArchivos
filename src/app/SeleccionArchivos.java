package app;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.awt.image.*;

import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * SeleccionArchivos
 */
public class SeleccionArchivos extends JFrame implements ActionListener{

    private JButton archivo;
    private JButton directorio;
    private File fichero;
    private JTextArea contenido;
    private JLabel info;
    private JLabel imagen;
    private String texto;

    public SeleccionArchivos() {
        super("Selecciona algo");
        this.setLayout(null);

        archivo = new JButton("Selecciona archivo");
        archivo.setSize(archivo.getPreferredSize());
        archivo.setLocation(80,5);
        archivo.addActionListener(this);
        add(archivo);

        directorio = new JButton("Selecciona directorio");
        directorio.setSize(directorio.getPreferredSize());
        directorio.setLocation(300,5);
        directorio.addActionListener(this);
        add(directorio);

        contenido = new JTextArea(30,45);
        contenido.setSize(archivo.getPreferredSize());
        contenido.setLocation(40,60);
        contenido.setVisible(false);
        add(contenido);

        info = new JLabel();
        info.setSize(archivo.getPreferredSize());
        info.setLocation(50,100);
        info.setVisible(false);
        add(info);

        imagen = new JLabel();
        imagen.setSize(400,400);
        imagen.setLocation(100,100);
        imagen.setVisible(false);
        add(imagen);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int respuesta;
        if(e.getSource() == archivo){
            FileNameExtensionFilter filtroTexto = new FileNameExtensionFilter("*.txt", "txt");
            FileNameExtensionFilter filtroImag = new FileNameExtensionFilter("*.jpg, *.jpeg, *.gif, *.png", "jpg", "jpeg", "gif", "png");
            JFileChooser f = new JFileChooser();
            f.addChoosableFileFilter(filtroTexto);
            f.addChoosableFileFilter(filtroImag);
            f.setFileSelectionMode(JFileChooser.FILES_ONLY);
            respuesta=f.showOpenDialog(this);
            if(respuesta == JFileChooser.APPROVE_OPTION){
                fichero = f.getSelectedFile();
                archivo(e); 
            }  
        }

        if(e.getSource() == directorio){
            JFileChooser d = new JFileChooser();
            d.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            respuesta = d.showOpenDialog(this);
            if(respuesta == JFileChooser.APPROVE_OPTION){
                fichero = d.getSelectedFile();
                directorio(e);
            }
        }
    }

    public void archivo(ActionEvent e){
        contenido.setVisible(false);
        if(fichero.getName().contains("txt")){
            info.setVisible(false);
            mostrar(e);
        } else if(fichero.getName().contains("jpg") || 
                fichero.getName().contains("jpeg") || 
                fichero.getName().contains("gif") || 
                fichero.getName().contains("png")){
            info.setVisible(false);
            Image img= new ImageIcon(fichero.getAbsolutePath()).getImage();
            ImageIcon img2=new ImageIcon(img.getScaledInstance(400, 400, Image.SCALE_SMOOTH));
            imagen.setIcon(img2);
            imagen.setVisible(true);
               
        } else {
            info.setText("<html><body>Nombre: "+fichero.getName()+""+"<br>Ruta: "+fichero.getAbsolutePath()+
                "<br>Tamaño: "+(fichero.length()/1024)+"KG<br>"+
                "Permisos lectura "+fichero.canRead()+" // ejecución "+fichero.canExecute()+"</body></html>");
            info.setSize(info.getPreferredSize());
            imagen.setVisible(false);
            info.setVisible(true);
        }
    }

    public void directorio(ActionEvent e){
        info.setVisible(false);
        imagen.setVisible(false);
        contenido.setText("");
        texto="";
        for (File lista : fichero.listFiles()) {
            if (lista.isFile()) {
                texto+=lista.getName()+"\n";
            }
            if (lista.isDirectory()){
                texto+="D: " + lista.getName()+"\n";
            }
        }
        if(texto.length() == 0){
            contenido.setText("El directorio está vacío");
        }else{
            contenido.setText(texto);
            contenido.setSize(contenido.getPreferredSize());
        }
        contenido.setVisible(true);
    }

    public void mostrar(ActionEvent e){
        info.setVisible(false);
        imagen.setVisible(false);
        texto="";
        try(Scanner textoArchivo = new Scanner(fichero)) {
            while(textoArchivo.hasNext()){
                texto+=textoArchivo.nextLine()+"\n";
            }
        } catch (IOException excep){
            JOptionPane.showMessageDialog(this, "Error al mostrar archivo ("+excep.getMessage()+")");
        }
        if(texto.length() == 0){
            contenido.setText("El archivo está vacío");
        }else{
            contenido.setText(texto);
            contenido.setSize(contenido.getPreferredSize());
        }
        contenido.setVisible(true);
    }
}