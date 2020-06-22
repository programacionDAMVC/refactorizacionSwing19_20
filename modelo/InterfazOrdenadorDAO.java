package ejercicio2.modelo;

import java.util.List;

public interface InterfazOrdenadorDAO {
    List<Ordenador> listarTodosOrdenadores();
    Ordenador listarOrdenadorePorId(int id);
    boolean ampliarMemoriaRAMOrdenadorPorId(Ordenador ordenador);
    boolean actualizarOrdenadorPorId(Ordenador ordenador);
    boolean borrarOrdenadorPorId(int id);
    boolean crearOrdenador(Ordenador ordenador) throws OrdenadorException;

}
