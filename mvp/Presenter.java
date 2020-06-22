package ejercicio2.mvp;

import ejercicio2.modelo.InterfazOrdenadorModificadoDAO;
import ejercicio2.modelo.Ordenador;
import ejercicio2.modelo.OrdenadorException;
import ejercicio2.modelo.OrdenadorModificadoDAO;

import java.util.List;

public class Presenter implements OrdenadorContract.Presenter {
    private final InterfazOrdenadorModificadoDAO ordenadorDAO = new OrdenadorModificadoDAO();
    private OrdenadorContract.Vista vista;
    private int contador = 0;
    private List<Ordenador> listaOrdenadores = ordenadorDAO.listarTodosOrdenadores();

    public Presenter(OrdenadorContract.Vista vista) {
        this.vista = vista;
    }

    @Override
    public Ordenador verOrdenador() {
        return listaOrdenadores.get(contador);
    }

    @Override
    public Ordenador verAnteriorOrdenador() {
        contador--;
        if (contador < 0)
            contador += listaOrdenadores.size();
        return listaOrdenadores.get(contador);
    }

    @Override
    public Ordenador verSiguienteOrdenador() {
        contador++;
        if (contador >= listaOrdenadores.size())
            contador %= listaOrdenadores.size();
        return listaOrdenadores.get(contador);
    }


    @Override
    public void crearOrdenador(int iRam, int iSSD, int iPULG) {
        Ordenador ordenador = new Ordenador(iRam, iSSD, iPULG);
        ordenadorDAO.crearOrdeandor(ordenador);
    }

    @Override
    public void borrarOrdenador(int idBorrar) {

        try {
            ordenadorDAO.borrarOrdenadorPorId(idBorrar);
            listaOrdenadores.remove(contador);
            listaOrdenadores = ordenadorDAO.listarTodosOrdenadores();
        } catch (OrdenadorException e) {
            vista.mostrarError("No se ha podido eliminar el ordenador");
        }
    }

    @Override
    public Ordenador actualizarOrdenador(int iRamAmpliar) {
        Ordenador ordenador = listaOrdenadores.get(contador);
        ordenador.setRam(ordenador.getRam() + iRamAmpliar);
        ordenadorDAO.ampliarMemoriaRAMOrdenadorPorId(ordenador);
        return ordenador;
    }


}
