package ejercicio2.refactor;

import ejercicio2.modelo.Ordenador;
import ejercicio2.modelo.OrdenadorDAO;
import ejercicio2.modelo.OrdenadorException;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Vista1 extends JFrame {
    private OrdenadorDAO ordenadorDAO = new OrdenadorDAO();
    private int contador = 0;
    private List<Ordenador> listaOrdenadores = ordenadorDAO.listarTodosOrdenadores();
    private JTextField inputID ;
    JTextField inputRAM ;
    JTextField inputSSD;
    JTextField inputPULGADAS;
    private JLabel etiquetaID;
    private JLabel etiquetaRAM;
    private JLabel etiquetaSSD;
    private JLabel etiquetaPULGADAS;
    private JButton botonMas;
    private JButton botonMenos;
    private JButton botonBorrar;
    private JButton botonActualizar;
    private JButton botonCrear;
    private JButton botonSalir;

    public Vista1(){
        super("Aula ordenadores portátiles");
        setVentana();
        Container cp = setLayout();
        crearPanelSuperior(cp);
        crearPanelCentral(cp);
        crearPanelInferior(cp);
    }

    private void setVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize(); //obtener tamaño ventana
        int height = pantalla.height;
        int width = pantalla.width;
        setSize(width/3, height/3);  //mitad de la pantalla
        setLocationRelativeTo(null); //centramos la ventana
    }

    private Container setLayout() {
        Container cp = getContentPane();
        BorderLayout borderLayout = new BorderLayout();
        cp.setLayout(borderLayout);
        return cp;
    }

    private void crearPanelSuperior(Container cp) {
        JLabel etiquetaSuperior = new JLabel("Datos de ordenadores portátiles");
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(etiquetaSuperior);
        cp.add(panelSuperior, BorderLayout.NORTH);
    }

    private void crearPanelCentral(Container cp) {
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(8,2));
        crearEtiquetas();
        crearCamposForm();
        disponerForm(panelCentral);
        cp.add(panelCentral, BorderLayout.CENTER);
    }

    private void crearEtiquetas() {
        etiquetaID = new JLabel("ID", SwingConstants.CENTER);
        etiquetaRAM = new JLabel("RAM", SwingConstants.CENTER);
        etiquetaSSD = new JLabel("SSD", SwingConstants.CENTER);
        etiquetaPULGADAS = new JLabel("PULGADAS", SwingConstants.CENTER);
        etiquetaID.setFont(new Font("Serif", Font.BOLD, 18));
        etiquetaSSD.setFont(new Font("Serif", Font.BOLD, 18));
        etiquetaRAM.setFont(new Font("Serif", Font.BOLD, 18));
        etiquetaPULGADAS.setFont(new Font("Serif", Font.BOLD, 18));
    }

    private void crearCamposForm() {
        inputID = new JTextField(5);
        inputID.setEnabled(false);
        inputRAM = new JTextField(5);
        inputSSD = new JTextField(5);
        inputPULGADAS = new JTextField(5);
        Ordenador ordenador = listaOrdenadores.get(contador);
        setDatosForm(ordenador);
    }

    private void disponerForm(JPanel panelCentral) {
        JLabel etiquetaVacia = new JLabel("  ");
        panelCentral.add(etiquetaVacia);
        panelCentral.add(etiquetaVacia);
        panelCentral.add(etiquetaID);
        panelCentral.add(inputID);
        panelCentral.add(etiquetaVacia);
        panelCentral.add(etiquetaVacia);
        panelCentral.add(etiquetaRAM);
        panelCentral.add(inputRAM);
        panelCentral.add(etiquetaVacia);
        panelCentral.add(etiquetaVacia);
        panelCentral.add(etiquetaSSD);
        panelCentral.add(inputSSD);
        panelCentral.add(etiquetaVacia);
        panelCentral.add(etiquetaVacia);
        panelCentral.add(etiquetaPULGADAS);
        panelCentral.add(inputPULGADAS);
        panelCentral.add(etiquetaVacia);
        panelCentral.add(etiquetaVacia);
    }

    private void crearPanelInferior(Container cp) {
        crearBotones();
        crearEventosBotones();
        crearBotonera(cp);
    }

    private void crearBotones() {
        botonMas = new JButton(">>");
        botonMenos = new JButton("<<");
        botonBorrar = new JButton("Borrar");
        botonActualizar = new JButton("Actualizar");
        botonCrear = new JButton("Crear");
        botonSalir = new JButton("Salir");
    }

    private void crearEventosBotones() {
        botonMas.addActionListener(actionEvent -> {
            contador++;
            if (contador >= listaOrdenadores.size()) {
                contador %= listaOrdenadores.size();
            }
            Ordenador ordenador = listaOrdenadores.get(contador);
            setDatosForm(ordenador);
        });
        botonMenos.addActionListener(actionEvent -> {
            contador--;
            if (contador < 0) {
                contador += listaOrdenadores.size();
            }
            Ordenador ordenador = listaOrdenadores.get(contador);
            setDatosForm(ordenador);
        });
        botonBorrar.addActionListener(actionEvent -> {
            int idBorrar = Integer.parseInt(inputID.getText());
            ordenadorDAO.borrarOrdenadorPorId(idBorrar);
            listaOrdenadores.remove(contador);
            contador--;
            if (contador < 0) {
                contador += listaOrdenadores.size();
            }
            Ordenador ordenador = listaOrdenadores.get(contador);
            setDatosForm(ordenador);
        });
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
        botonSalir.addActionListener(actionEvent -> System.exit(0));
    }

    private void crearBotonera(Container cp) {
        JPanel panelInferior = new JPanel();
        panelInferior.add(botonSalir);
        panelInferior.add(botonBorrar);
        panelInferior.add(botonMenos);
        panelInferior.add(botonMas);
        panelInferior.add(botonActualizar);
        panelInferior.add(botonCrear);
        cp.add(panelInferior,BorderLayout.SOUTH);
    }

    private void setDatosForm(Ordenador ordenador) {
        inputID.setText(ordenador.getId() + "");
        inputRAM.setText(ordenador.getRam() + "");
        inputSSD.setText(ordenador.getSsd() + "");
        inputPULGADAS.setText(ordenador.getPantalla() + "");
    }
}
