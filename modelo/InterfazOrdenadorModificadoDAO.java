package ejercicio2.modelo;

import java.util.List;

public interface InterfazOrdenadorModificadoDAO {
    List<Ordenador> listarTodosOrdenadores();
    Ordenador listarOrdenadorePorId(int id);
    boolean ampliarMemoriaRAMOrdenadorPorId(Ordenador ordenador);
    boolean borrarOrdenadorPorId(int id) throws OrdenadorException;
    boolean crearOrdeandor(Ordenador ordenador);
}
