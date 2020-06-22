package ejercicio2.vista2;

import ejercicio2.modelo.Ordenador;
import ejercicio2.modelo.OrdenadorDAO;
import ejercicio2.modelo.OrdenadorException;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Vista2  extends JFrame {
    private Font font1 = new Font("Agency FB", Font.BOLD, 14);
    private OrdenadorDAO ordenadorDAO = new OrdenadorDAO();
    private List<Ordenador> listaOrdenadores = ordenadorDAO.listarTodosOrdenadores();
    private int contador = 0;
    private JTextField inputID;
    private JTextField inputRAM;
    private JTextField inputSSD;
    private  JTextField inputPULG;
    public Vista2()  {
        super("App ordenadores de aula");
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize(); //obtener características ventana
        int height = pantalla.height;
        int width = pantalla.width;
        setSize(width / 3,height / 3); //tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x cierre
        setLocationRelativeTo(null);  //centrar la ventana
        /////////// LAYAOUT ////////////////////////////////
        Container cp = getContentPane();
        BorderLayout bl = new BorderLayout();
        bl.setHgap(5); bl.setVgap(5);
        cp.setLayout(bl);
        //////////ARRIBA/////////
        JPanel panelArriba = new JPanel();
        JLabel etiquetaSuperior = new JLabel("DATOS DE ORDENADORES DEL AULA");
        etiquetaSuperior.setFont(font1);
        etiquetaSuperior.setForeground(Color.red);
        panelArriba.add(etiquetaSuperior);
        cp.add(panelArriba, BorderLayout.NORTH);
        /////////ABAJO////////////////
        JPanel panelInferior = new JPanel();
        JButton botonAvanzar = new JButton(">>");
        botonAvanzar.addActionListener(actionEvent -> {
            //System.out.println("Botón avanzar pulsado");
            contador++; //posición de los elementos en la lista
            if (contador >= listaOrdenadores.size())
                contador %= listaOrdenadores.size();
            inputID.setText(listaOrdenadores.get(contador).getId() +"");
            inputRAM.setText(listaOrdenadores.get(contador).getRam() +"");
            inputSSD.setText(listaOrdenadores.get(contador).getSsd() +"");
            inputPULG.setText(listaOrdenadores.get(contador).getPantalla() +"");

        });
        JButton botonRetroceder = new JButton("<<");
        botonRetroceder.addActionListener(actionEvent -> {
            contador--; //posición de los elementos en la lista
            if (contador < 0)
                contador += listaOrdenadores.size();
            inputID.setText(listaOrdenadores.get(contador).getId() +"");
            inputRAM.setText(listaOrdenadores.get(contador).getRam() +"");
            inputSSD.setText(listaOrdenadores.get(contador).getSsd() +"");
            inputPULG.setText(listaOrdenadores.get(contador).getPantalla() +"");
        });
        JButton botonBorrar = new JButton("Borrar");
        botonBorrar.addActionListener(actionEvent -> {
            int id = Integer.parseInt(inputID.getText());
            ordenadorDAO.borrarOrdenadorPorId(id);
            listaOrdenadores.remove(contador);
            contador--;
            if (contador < 0)
                contador += listaOrdenadores.size();
            inputID.setText(listaOrdenadores.get(contador).getId() +"");
            inputRAM.setText(listaOrdenadores.get(contador).getRam() +"");
            inputSSD.setText(listaOrdenadores.get(contador).getSsd() +"");
            inputPULG.setText(listaOrdenadores.get(contador).getPantalla() +"");
        });
        JButton botonAmpliarRAM = new JButton("Ampliar RAM");
        botonAmpliarRAM.addActionListener(actionEvent -> {
            int id = Integer.parseInt(inputID.getText());
            int ramActual = Integer.parseInt(inputRAM.getText());
            String sRamAmpliar = "";
            int ramAmpliacion = 0;
            while (true) {
                sRamAmpliar = JOptionPane.showInputDialog("Introduce ram a añadir");
                if (sRamAmpliar.matches("[0-9]+")){
                    ramAmpliacion = Integer.parseInt(sRamAmpliar);
                    break;
                }
            }
            int nuevaRAM = ramActual + ramAmpliacion;
            ordenadorDAO.ampliarMemoriaRAMOrdenadorPorId(new Ordenador(id, nuevaRAM, 0, 0));
            inputRAM.setText(nuevaRAM + "");
            listaOrdenadores.get(contador).setRam(nuevaRAM);
        });
        JButton botonCrear = new JButton("Crear");
        botonCrear.addActionListener(actionEvent -> {
            String sRam = "", sSSD = "", sPULG = "";
            int iRam = 0, iSSD = 0, iPULG = 0;
            while (true){
                sRam = JOptionPane.showInputDialog("Introduce ram");
                if (sRam.matches("[0-9]+")){
                    iRam = Integer.parseInt(sRam);
                    break;
                }
            }
            while (true){
                sSSD = JOptionPane.showInputDialog("Introduce SSD");
                if (sSSD.matches("[0-9]+")){
                    iSSD = Integer.parseInt(sSSD);
                    break;
                }
            }
            while (true){
                sPULG = JOptionPane.showInputDialog("Introduce tamaño en pulgadas");
                if (sPULG.matches("[0-9]+")){
                    iPULG = Integer.parseInt(sPULG);
                    break;
                }
            }
            try {
                ordenadorDAO.crearOrdenador(new Ordenador(iRam, iSSD, iPULG));
            } catch (OrdenadorException e) {
                e.printStackTrace();
            }
            listaOrdenadores = ordenadorDAO.listarTodosOrdenadores();
        });
        JButton botonSalir = new JButton("Salir");
        botonSalir.addActionListener(actionEvent -> {
            System.exit(0);
        });
        panelInferior.add(botonSalir);
        panelInferior.add(botonBorrar);
        panelInferior.add(botonRetroceder);
        panelInferior.add(botonAvanzar);
        panelInferior.add(botonAmpliarRAM);
        panelInferior.add(botonCrear);
        cp.add(panelInferior, BorderLayout.SOUTH);
        //CENTRAL
        JPanel panelCentral = new JPanel();
        GridLayout gl = new GridLayout(7,2);
        gl.setHgap(5); gl.setVgap(5);
        panelCentral.setLayout(gl);
        JLabel etiquetaVacia = new JLabel("");
        JLabel etiquetaID = new JLabel("ID",SwingConstants.CENTER );
        etiquetaID.setFont(font1);
        JLabel etiquetaRAM = new JLabel("RAM", SwingConstants.CENTER);
        etiquetaRAM.setFont(font1);
        JLabel etiquetaSSD = new JLabel("SSD", SwingConstants.CENTER);
        etiquetaSSD.setFont(font1);
        JLabel etiquetaPULG= new JLabel("PULGADAS", SwingConstants.CENTER);
        etiquetaPULG.setFont(font1);
        inputID = new JTextField(10); inputID.setText(listaOrdenadores.get(0).getId() +"");
        inputRAM = new JTextField(10); inputRAM.setText(listaOrdenadores.get(0).getRam() +"");
        inputSSD = new JTextField(10); inputSSD.setText(listaOrdenadores.get(0).getSsd() +"");
        inputPULG = new JTextField(10); inputPULG.setText(listaOrdenadores.get(0).getPantalla() +"");
        panelCentral.add(etiquetaVacia);  panelCentral.add(etiquetaVacia);
        panelCentral.add(etiquetaVacia);  panelCentral.add(etiquetaVacia);
        panelCentral.add(etiquetaID); panelCentral.add(inputID);
        panelCentral.add(etiquetaRAM); panelCentral.add(inputRAM);
        panelCentral.add(etiquetaSSD); panelCentral.add(inputSSD);
        panelCentral.add(etiquetaPULG); panelCentral.add(inputPULG);
        panelCentral.add(etiquetaVacia);  panelCentral.add(etiquetaVacia);
        cp.add(panelCentral, BorderLayout.CENTER);



    }
}
