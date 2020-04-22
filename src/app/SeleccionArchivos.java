package app;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * SeleccionArchivos
 */
public class SeleccionArchivos extends JFrame implements ActionListener{

    private JButton archivo;
    private File fichero;
    private JTextArea contenido;
    private JLabel info;

    public SeleccionArchivos() {
        super("Selecciona algo");
        this.setLayout(new FlowLayout());

        archivo = new JButton("Selecciona archivo o directorio");
        archivo.addActionListener(this);
        add(archivo);

        contenido = new JTextArea(20,50);
        add(contenido);

        info = new JLabel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int respuesta;
        if(e.getSource() == archivo){
            FileNameExtensionFilter filtroTexto = new FileNameExtensionFilter("*.txt", "txt");
            FileNameExtensionFilter filtroImag = new FileNameExtensionFilter("imagen", "jpg", "jpeg", "gif", "png");
            JFileChooser f = new JFileChooser();
            f.addChoosableFileFilter(filtroTexto);
            f.addChoosableFileFilter(filtroImag);
            //f.setFileSelectionMode(JFileChooser.FILES_ONLY);
            respuesta=f.showOpenDialog(this);
            if(respuesta == JFileChooser.APPROVE_OPTION){
                fichero = f.getSelectedFile();
                if(fichero.isFile()){
                    archivo(e);
                }
                if(fichero.isDirectory()){
                    directorio(e);
                }     
            }  
        }
    }

    public void archivo(ActionEvent e){
        if(fichero.getName().contains("txt")){
            mostrar(e);
        } else if(fichero.getName().contains("jpg") || 
                fichero.getName().contains("jpeg") || 
                fichero.getName().contains("gif") || 
                fichero.getName().contains("png")){
            contenido.setVisible(false);
            info.setText("");
            info.setIcon(new ImageIcon(fichero.getAbsolutePath()));
            info.setSize(200,200);
            add(info);
        } else {
            remove(info);
            info.setText("<html><body>Nombre: "+fichero.getName()+""+"<br>Ruta: "+fichero.getAbsolutePath()+
                "<br>Tamaño: "+(fichero.length()/1024)+"KG<br>"+
                "Permisos lectura "+fichero.canRead()+" // ejecución "+fichero.canExecute()+"</body></html>");
            info.setSize(info.getPreferredSize());
            add(info);
        }
    }

    public void directorio(ActionEvent e){
        contenido.setText("");
        String informacion="<html><body>";
        for (File lista : fichero.listFiles()) {
            if (lista.isFile()) {
                informacion=informacion.concat(lista.getName()+"<br>");
            }
            if (lista.isDirectory()){
                informacion=informacion.concat("D: " + lista.getName()+"<br>");
            }
        }
        informacion=informacion.concat("</body></html>");
        contenido.setText(informacion);
        contenido.setSize(contenido.getPreferredSize());
    }

    public void mostrar(ActionEvent e){
        String texto="";
        try(Scanner textoArchivo = new Scanner(fichero)) {
            while(textoArchivo.hasNext()){
                texto=texto+textoArchivo.nextLine()+"\n";
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
        //contenido.setVisible(true);
    }
}