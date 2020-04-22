package app;

import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        SeleccionArchivos menu = new SeleccionArchivos();
        menu.setSize(600,600);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setVisible(true);
    }
}