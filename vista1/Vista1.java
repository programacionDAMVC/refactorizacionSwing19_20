package ejercicio2.vista1;

import ejercicio2.modelo.Ordenador;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import ejercicio2.modelo.OrdenadorDAO;
import ejercicio2.modelo.OrdenadorException;

public class Vista1 extends JFrame {
    private OrdenadorDAO ordenadorDAO = new OrdenadorDAO();
    private int contador = 0;
    private List<Ordenador> listaOrdenadores = ordenadorDAO.listarTodosOrdenadores();
    private JTextField inputID ;
    JTextField inputRAM ;
    JTextField inputSSD;
    JTextField inputPULGADAS;
    public Vista1(){
        super("Aula ordenadores portátiles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize(); //obtener tamaño ventana
        int height = pantalla.height;
        int width = pantalla.width;
        setSize(width/3, height/3);  //mitad de la pantalla
        setLocationRelativeTo(null); //centramos la ventana
        Container cp = getContentPane();
        BorderLayout borderLayout = new BorderLayout();
        cp.setLayout(borderLayout);
        JButton botonMas = new JButton(">>");
        botonMas.addActionListener(actionEvent -> {
            contador++;
            if (contador >= listaOrdenadores.size())
                contador %= listaOrdenadores.size();
            inputID.setText(listaOrdenadores.get(contador).getId() + "");
            inputRAM.setText(listaOrdenadores.get(contador).getRam() + "");
            inputSSD.setText(listaOrdenadores.get(contador).getSsd() + "");
            inputPULGADAS.setText(listaOrdenadores.get(contador).getPantalla() + "");
        });
        JButton botonMenos = new JButton("<<");
        botonMenos.addActionListener(actionEvent -> {
            contador--;
            if (contador < 0)
                contador += listaOrdenadores.size();
            inputID.setText(listaOrdenadores.get(contador).getId() + "");
            inputRAM.setText(listaOrdenadores.get(contador).getRam() + "");
            inputSSD.setText(listaOrdenadores.get(contador).getSsd() + "");
            inputPULGADAS.setText(listaOrdenadores.get(contador).getPantalla() + "");
        });
        JButton botonBorrar = new JButton("Borrar");
        botonBorrar.addActionListener(actionEvent -> {
            int idBorrar = Integer.parseInt(inputID.getText());
            ordenadorDAO.borrarOrdenadorPorId(idBorrar);
            listaOrdenadores.remove(contador);
            contador--;
            if (contador < 0)
                contador += listaOrdenadores.size();
            inputID.setText(listaOrdenadores.get(contador).getId() + "");
            inputRAM.setText(listaOrdenadores.get(contador).getRam() + "");
            inputSSD.setText(listaOrdenadores.get(contador).getSsd() + "");
            inputPULGADAS.setText(listaOrdenadores.get(contador).getPantalla() + "");
        });
        JButton botonActualizar = new JButton("Actualizar");
        botonActualizar.addActionListener(actionEvent -> {
            Ordenador ordenador = new Ordenador(Integer.parseInt(inputID.getText()),
                    Integer.parseInt(inputRAM.getText()), 0,0);
            String sRamAmpliar = JOptionPane.showInputDialog("Introduce ram a añadir");
            int iRamApliar = Integer.parseInt(sRamAmpliar);
            listaOrdenadores.get(contador).setRam(ordenador.getRam() + iRamApliar);
            ordenador.setRam(ordenador.getRam() + iRamApliar);
            ordenadorDAO.ampliarMemoriaRAMOrdenadorPorId(ordenador);
            inputRAM.setText(ordenador.getRam() + "");
        });
        JButton botonCrear = new JButton("Crear");
        botonCrear.addActionListener(actionEvent -> {
            String sRam = JOptionPane.showInputDialog("Introduce ram");
            int iRam = Integer.parseInt(sRam);
            String sSSD = JOptionPane.showInputDialog("Introduce SSD");
            int iSSD = Integer.parseInt(sSSD);
            String sPULG = JOptionPane.showInputDialog("Introduce pulgadas");
            int iPULG = Integer.parseInt(sPULG);
            Ordenador ordenador = new Ordenador(iRam, iSSD, iPULG);
            try {
                ordenadorDAO.crearOrdenador(ordenador);
            } catch (OrdenadorException e) {
                e.printStackTrace();
            }
            listaOrdenadores = ordenadorDAO.listarTodosOrdenadores();
        });
        JButton botonSalir = new JButton("Salir");
        botonSalir.addActionListener(actionEvent -> System.exit(0));
        JPanel panelInferior = new JPanel();
        panelInferior.add(botonSalir);
        panelInferior.add(botonBorrar);
        panelInferior.add(botonMenos);
        panelInferior.add(botonMas);
        panelInferior.add(botonActualizar);
        panelInferior.add(botonCrear);
        cp.add(panelInferior,BorderLayout.SOUTH);
        JLabel etiquetaSuperior = new JLabel("Datos de ordenadores portátiles");
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(etiquetaSuperior);
        cp.add(panelSuperior, BorderLayout.NORTH);
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(8,2));
        JLabel etiquetaID = new JLabel("ID", SwingConstants.CENTER);
        JLabel etiquetaRAM = new JLabel("RAM", SwingConstants.CENTER);
        JLabel etiquetaSSD = new JLabel("SSD", SwingConstants.CENTER);
        JLabel etiquetaPULGADAS = new JLabel("PULGADAS", SwingConstants.CENTER);
        etiquetaID.setFont(new Font("Serif", Font.BOLD, 18));
        etiquetaSSD.setFont(new Font("Serif", Font.BOLD, 18));
        etiquetaRAM.setFont(new Font("Serif", Font.BOLD, 18));
        etiquetaPULGADAS.setFont(new Font("Serif", Font.BOLD, 18));

        inputID = new JTextField(5); inputID.setText(listaOrdenadores.get(contador ) .getId() + ""); inputID.setEnabled(false);
        inputRAM = new JTextField(5); inputRAM.setText(listaOrdenadores.get(contador).getRam() + "");
        inputSSD = new JTextField(5); inputSSD.setText(listaOrdenadores.get(contador).getSsd() + "");
        inputPULGADAS = new JTextField(5); inputPULGADAS.setText(listaOrdenadores.get(contador).getPantalla() + "");
        JLabel etiquetaVacia = new JLabel("  ");
        panelCentral.add(etiquetaVacia); panelCentral.add(etiquetaVacia);
        panelCentral.add(etiquetaID); panelCentral.add(inputID);
        panelCentral.add(etiquetaVacia); panelCentral.add(etiquetaVacia);
        panelCentral.add(etiquetaRAM); panelCentral.add(inputRAM);
        panelCentral.add(etiquetaVacia); panelCentral.add(etiquetaVacia);
        panelCentral.add(etiquetaSSD); panelCentral.add(inputSSD);
        panelCentral.add(etiquetaVacia); panelCentral.add(etiquetaVacia);
        panelCentral.add(etiquetaPULGADAS); panelCentral.add(inputPULGADAS);
        panelCentral.add(etiquetaVacia); panelCentral.add(etiquetaVacia);
        cp.add(panelCentral, BorderLayout.CENTER);





    }
}
