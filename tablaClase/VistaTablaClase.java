package ejercicio2.tablaClase;

import ejercicio2.modelo.Ordenador;
import ejercicio2.modelo.OrdenadorDAO;
import ejercicio2.modelo.OrdenadorException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class VistaTablaClase extends JFrame {
    JTable tabla;
    OrdenadoresTableModel modeloDeTabla;
    OrdenadorDAO ordenadorDAO = new OrdenadorDAO();
    List<Ordenador> ordenadores = ordenadorDAO.listarTodosOrdenadores();

    public VistaTablaClase() {
        setVista();
        setLayout();
        crearTabla();
        setBotonera();
        pack();
    }

    private void setVista() {
        setTitle("Modelo de Tabla");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setLayout() {
        getContentPane().setLayout(new BorderLayout());
    }

    private void crearTabla() {
        modeloDeTabla = new OrdenadoresTableModel(ordenadores);
        tabla = new JTable(modeloDeTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabla);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void setBotonera() {
        JPanel panelInferior = new JPanel();
        JButton botonBorrar = new JButton("Borrar");
        JButton botonNuevo = new JButton("Nuevo Ordenador");
        panelInferior.add(botonBorrar);
        panelInferior.add(botonNuevo);
        getContentPane().add(panelInferior, BorderLayout.SOUTH);
        botonBorrar.addActionListener(actionEvent -> {
            int fila = tabla.getSelectionModel().getMinSelectionIndex();
            System.out.println(fila);
            Ordenador ordenador = ordenadores.get(fila);
            ordenadorDAO.borrarOrdenadorPorId(ordenador.getId());
            System.out.println("Borrado ordenador " + ordenador.getId());
            ordenadores.remove(ordenador);
            modeloDeTabla.fireTableRowsDeleted(fila, fila);
        });
        botonNuevo.addActionListener(actionEvent -> {
            Ordenador ordenador = vistaCrearOrdenador();
            try {
                ordenadorDAO.crearOrdenador(ordenador);
                ordenadores = ordenadorDAO.listarTodosOrdenadores();
                modeloDeTabla.setOrdenadores(ordenadores);
            } catch (OrdenadorException e) {
                e.printStackTrace();
            }
        });
    }

    public Ordenador vistaCrearOrdenador() {
        String sRam = JOptionPane.showInputDialog("Introduce ram");
        int iRam = Integer.parseInt(sRam);
        String sSSD = JOptionPane.showInputDialog("Introduce SSD");
        int iSSD = Integer.parseInt(sSSD);
        String sPULG = JOptionPane.showInputDialog("Introduce pulgadas");
        int iPULG = Integer.parseInt(sPULG);
        Ordenador ordenador = new Ordenador(iRam, iSSD, iPULG);
        return ordenador;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VistaTablaClase vista = new VistaTablaClase();
                vista.setVisible(true);
            }
        });
    }

}
