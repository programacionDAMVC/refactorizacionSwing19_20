package ejercicio2;

import ejercicio2.tabla.VistaTabla;

public class TestTabla {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VistaTabla vista = new VistaTabla();
                vista.setVisible(true);
            }
        });
    }


}
