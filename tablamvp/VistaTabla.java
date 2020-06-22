package ejercicio2.tabla;

import ejercicio2.modelo.Ordenador;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;


public class VistaTabla extends JFrame implements OrdenadorContract.Vista {

    private JTextField inputID;
    private JTextField inputRAM;
    private JTextField inputSSD;
    private JTextField inputPULGADAS;
    private JButton botonMas;
    private JButton botonMenos;
    private JButton botonBorrar;
    private JButton botonActualizar;
    private JButton botonCrear;
    private Presenter presenter = new Presenter(this);
    private JButton botonSalir;
    private JTable tabla;

    public VistaTabla() {
        super("Vista Tabla");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout();
        setTabla();
        setFormulario();
        setPanelInferior();
        this.pack();
        setPrimerOrdenador(presenter.getOrdenadorActual());
    }

    private void setPrimerOrdenador(Ordenador ordenadorActual) {
        Ordenador ordenador = ordenadorActual;
        setOrdenadorForm(ordenador);
    }

    public void setLayout() {
        getContentPane().setLayout(new BorderLayout());
    }

    public void setTabla() {
        tabla = new JTable(presenter.getTableModel());
        setTableModelSelection();
        JScrollPane scrollPane = new JScrollPane(tabla);
        getContentPane().add(scrollPane, BorderLayout.NORTH);
        tabla.setPreferredScrollableViewportSize(new Dimension(600, 200));
        tabla.setFillsViewportHeight(true);
        setTableSelection();
    }

    private void setTableModelSelection() {
        presenter.getTableModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tableModelEvent) {
                int fila = tableModelEvent.getFirstRow();
                int col = tableModelEvent.getColumn();
                Ordenador ordenador = presenter.getOrdenadorActual();
                setOrdenadorForm(ordenador);
            }
        });
    }

    private void setTableSelection() {
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel rowSM = tabla.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
               if (e.getValueIsAdjusting()) return;
                ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    if (!lsm.isSelectionEmpty()) {
                        int selectedRow = lsm.getMinSelectionIndex();
                        presenter.setContador(selectedRow);
                        Ordenador ordenador = presenter.getOrdenadorActual();
                        setOrdenadorForm(ordenador);
                    }
            }
        });
    }

    private void setFormulario() {
        JLabel etiquetaID = new JLabel("ID", SwingConstants.CENTER);
        JLabel etiquetaRAM = new JLabel("RAM", SwingConstants.CENTER);
        JLabel etiquetaSSD = new JLabel("SSD", SwingConstants.CENTER);
        JLabel etiquetaPULGADAS = new JLabel("PULGADAS", SwingConstants.CENTER);
        etiquetaID.setFont(new Font("Serif", Font.BOLD, 18));
        etiquetaSSD.setFont(new Font("Serif", Font.BOLD, 18));
        etiquetaRAM.setFont(new Font("Serif", Font.BOLD, 18));
        etiquetaPULGADAS.setFont(new Font("Serif", Font.BOLD, 18));

        inputID = new JTextField(5);
        inputID.setEnabled(false);
        inputRAM = new JTextField(5);
        inputSSD = new JTextField(5);
        inputPULGADAS = new JTextField(5);
   
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
        getContentPane().add(panelCentral, BorderLayout.CENTER);
    }

    private void setPanelInferior() {
        crearBotones();
        crearEventosBotonera();
        crearBotonera();
    }

    private void crearBotones() {
        botonMas = new JButton(">>");
        botonMenos = new JButton("<<");
        botonBorrar = new JButton("Borrar");
        botonActualizar = new JButton("Actualizar");
        botonCrear = new JButton("Crear");
    }

    private void crearEventosBotonera() {
        botonMas.addActionListener(actionEvent -> {
            Ordenador ordenador = presenter.getSiguienteOrdenador();
            setOrdenadorForm(ordenador);
        });
        botonMenos.addActionListener(actionEvent -> {
            Ordenador ordenador = presenter.getAnteriorOrdenador();
            setOrdenadorForm(ordenador);
        });
        botonBorrar.addActionListener(actionEvent -> {
            int idBorrar = Integer.parseInt(inputID.getText());
            Ordenador ordenador = presenter.borrarOrdenador(idBorrar);
            setOrdenadorForm(ordenador);
        });
        botonActualizar.addActionListener(actionEvent -> {
            Ordenador ordenador = new Ordenador(Integer.parseInt(inputID.getText()),
                    Integer.parseInt(inputRAM.getText()), 0, 0);
            String sRamAmpliar = JOptionPane.showInputDialog("Introduce ram a añadir");
            int iRamAmpliar = Integer.parseInt(sRamAmpliar);
            Ordenador ordenadorAmpliado = presenter.actualizarRam(ordenador, iRamAmpliar);
            inputRAM.setText(ordenadorAmpliado.getRam() + "");
        });
        botonCrear.addActionListener(actionEvent -> {
            String sRam = JOptionPane.showInputDialog("Introduce ram");
            int iRam = Integer.parseInt(sRam);
            String sSSD = JOptionPane.showInputDialog("Introduce SSD");
            int iSSD = Integer.parseInt(sSSD);
            String sPULG = JOptionPane.showInputDialog("Introduce pulgadas");
            int iPULG = Integer.parseInt(sPULG);
            Ordenador ordenador = new Ordenador(iRam, iSSD, iPULG);
            Ordenador ordenadorNuevo = presenter.crearOrdenador(ordenador);
            setOrdenadorForm(ordenadorNuevo);
        });
        botonSalir = new JButton("Salir");
        botonSalir.addActionListener(actionEvent -> System.exit(0));
    }

    private void crearBotonera() {
        JPanel panelInferior = new JPanel();
        panelInferior.add(botonSalir);
        panelInferior.add(botonBorrar);
        panelInferior.add(botonMenos);
        panelInferior.add(botonMas);
        panelInferior.add(botonActualizar);
        panelInferior.add(botonCrear);
        getContentPane().add(panelInferior, BorderLayout.SOUTH);
    }


    private void setOrdenadorForm(Ordenador ordenador) {
        inputID.setText(ordenador.getId() + "");
        inputRAM.setText(ordenador.getRam() + "");
        inputSSD.setText(ordenador.getSsd() + "");
        inputPULGADAS.setText(ordenador.getPantalla() + "");
    }

    @Override
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this,mensaje);
    }
}
