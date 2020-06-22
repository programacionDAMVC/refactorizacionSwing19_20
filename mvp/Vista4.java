package ejercicio2.mvp;

import ejercicio2.modelo.Ordenador;

import javax.swing.*;
import java.awt.*;

public class Vista4 extends JFrame implements OrdenadorContract.Vista{

    private JTextField inputRAM;
    private JTextField inputSSD;
    private JTextField inputPULGADAS;
    private JTextField inputID;
    private JButton botonMas;
    private JButton botonMenos;
    private JButton botonBorrar;
    private JButton botonActualizar;
    private JButton botonCrear;
    private JButton botonSalir;
    private JLabel etiquetaID;
    private JLabel etiquetaRAM;
    private JLabel etiquetaSSD;
    private JLabel etiquetaPULGADAS;
    private OrdenadorContract.Presenter presenter = new Presenter(this);

    public Vista4() {
        super("Aula ordenadores portátiles");
        setVentana();
        Container cp = getContentPane();
        setLayout(cp);
        setPanelSuperior(cp);
        setPanelCentral(cp);
        setPanelInferior(cp);
        Ordenador ordenador = presenter.verOrdenador();
        setOrdenadorForm(ordenador);
    }

    private void setVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize(); //obtener tamaño ventana
        int height = pantalla.height;
        int width = pantalla.width;
        setSize(width / 3, height / 3);  //mitad de la pantalla
        setLocationRelativeTo(null); //centramos la ventana
    }

    private void setLayout(Container cp) {
        BorderLayout borderLayout = new BorderLayout();
        cp.setLayout(borderLayout);
    }

    private void setPanelSuperior(Container cp) {
        JLabel etiquetaSuperior = new JLabel("Datos de ordenadores portátiles");
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(etiquetaSuperior);
        cp.add(panelSuperior, BorderLayout.NORTH);
    }

    private void setPanelCentral(Container cp) {
        crearEtiquetasForm();
        crearCampos();
        disponerForm(cp);
    }

    private void crearEtiquetasForm() {
        etiquetaID = new JLabel("ID", SwingConstants.CENTER);
        etiquetaRAM = new JLabel("RAM", SwingConstants.CENTER);
        etiquetaSSD = new JLabel("SSD", SwingConstants.CENTER);
        etiquetaPULGADAS = new JLabel("PULGADAS", SwingConstants.CENTER);
        etiquetaID.setFont(new Font("Serif", Font.BOLD, 18));
        etiquetaSSD.setFont(new Font("Serif", Font.BOLD, 18));
        etiquetaRAM.setFont(new Font("Serif", Font.BOLD, 18));
        etiquetaPULGADAS.setFont(new Font("Serif", Font.BOLD, 18));
    }

    private void crearCampos() {
        inputID = new JTextField(5);
        inputID.setEnabled(false);
        inputRAM = new JTextField(5);
        inputSSD = new JTextField(5);
        inputPULGADAS = new JTextField(5);
    }

    private void disponerForm(Container cp) {
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(8, 2));
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
        cp.add(panelCentral, BorderLayout.CENTER);
    }

    private void setPanelInferior(Container cp) {
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
            Ordenador ordenador = presenter.verSiguienteOrdenador();
            setOrdenadorForm(ordenador);
        });
        botonMenos.addActionListener(actionEvent -> {
            Ordenador ordenador = presenter.verAnteriorOrdenador();
            setOrdenadorForm(ordenador);
        });
        botonBorrar.addActionListener(actionEvent -> {
            borrarOrdenador();
            Ordenador ordenador = presenter.verAnteriorOrdenador();
            setOrdenadorForm(ordenador);
        });
        botonActualizar.addActionListener(actionEvent -> {
            actualizarOrdenador();
        });
        botonCrear.addActionListener(actionEvent -> {
            crearOrdenador();
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
        cp.add(panelInferior, BorderLayout.SOUTH);
    }

    private void crearOrdenador() {
        String sRam = JOptionPane.showInputDialog("Introduce ram");
        int iRam = Integer.parseInt(sRam);
        String sSSD = JOptionPane.showInputDialog("Introduce SSD");
        int iSSD = Integer.parseInt(sSSD);
        String sPULG = JOptionPane.showInputDialog("Introduce pulgadas");
        int iPULG = Integer.parseInt(sPULG);
        presenter.crearOrdenador(iRam, iSSD, iPULG);
    }

    private void actualizarOrdenador() {
        String sRamAmpliar = JOptionPane.showInputDialog("Introduce ram a añadir");
        int iRamApliar = Integer.parseInt(sRamAmpliar);
        Ordenador ordenador = presenter.actualizarOrdenador(iRamApliar);
        setOrdenadorForm(ordenador);
    }

    private void borrarOrdenador() {
        int idBorrar = Integer.parseInt(inputID.getText());
        presenter.borrarOrdenador(idBorrar);
    }

    private void setOrdenadorForm(Ordenador ordenador) {
        inputID.setText(ordenador.getId() + "");
        inputRAM.setText(ordenador.getRam() + "");
        inputSSD.setText(ordenador.getSsd() + "");
        inputPULGADAS.setText(ordenador.getPantalla() + "");
    }

    public void mostrarError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
