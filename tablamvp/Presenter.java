package ejercicio2.tabla;

import ejercicio2.modelo.Ordenador;
import ejercicio2.modelo.OrdenadorDAO;
import ejercicio2.modelo.OrdenadorException;

import java.util.List;

public class Presenter implements OrdenadorContract.Presenter {
    private int contador = 0;
    private final OrdenadorDAO ordenadorDAO = new OrdenadorDAO();
    private List<Ordenador> listaOrdenadores = ordenadorDAO.listarTodosOrdenadores();
    private OrdenadorContract.Vista vista;
    private OrdenadoresTableModel tableModel = new OrdenadoresTableModel(listaOrdenadores);

    public Presenter(OrdenadorContract.Vista vista) {
        this.vista = vista;
    }

    @Override
    public Ordenador getOrdenadorActual() {
        return listaOrdenadores.get(contador);
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    @Override
    public Ordenador getSiguienteOrdenador() {
        contador++;
        if (contador >= listaOrdenadores.size()) {
            contador %= listaOrdenadores.size();
        }
        return listaOrdenadores.get(contador);
    }

    @Override
    public Ordenador getAnteriorOrdenador() {
        contador--;
        if (contador < 0) {
            contador += listaOrdenadores.size();
        }
        return listaOrdenadores.get(contador);
    }

    @Override
    public Ordenador borrarOrdenador(int idBorrar) {
        ordenadorDAO.borrarOrdenadorPorId(idBorrar);
        listaOrdenadores.remove(contador);
        tableModel.fireTableRowsDeleted(contador, contador);
        contador--;
        if (contador < 0) {
            contador += listaOrdenadores.size();
        }
        return  listaOrdenadores.get(contador);
    }

    @Override
    public Ordenador actualizarRam(Ordenador ordenador, int iRamAmpliar) {
        listaOrdenadores.get(contador).setRam(ordenador.getRam() + iRamAmpliar);
        tableModel.fireTableRowsUpdated(contador,contador);
        ordenador.setRam(ordenador.getRam() + iRamAmpliar);
        ordenadorDAO.ampliarMemoriaRAMOrdenadorPorId(ordenador);
        return ordenador;
    }

    @Override
    public Ordenador crearOrdenador(Ordenador ordenador) {
        try {
            ordenadorDAO.crearOrdenador(ordenador);
        } catch (OrdenadorException e) {
            this.vista.mostrarError(e.getMessage());
        }
        listaOrdenadores = ordenadorDAO.listarTodosOrdenadores();
        tableModel.setListaOrdenadores(listaOrdenadores);
        contador = listaOrdenadores.size() - 1;
        return listaOrdenadores.get(contador);
    }

    public OrdenadoresTableModel getTableModel() {
        return tableModel;
    }
}
