package ejercicio2.tabla;

import ejercicio2.modelo.Ordenador;

public interface OrdenadorContract {

    public interface Vista {
        public void mostrarError(String mensaje);
    }

    public interface Presenter{
        Ordenador getOrdenadorActual();
        Ordenador getSiguienteOrdenador();
        Ordenador getAnteriorOrdenador();
        Ordenador borrarOrdenador(int idBorrar);
        Ordenador actualizarRam(Ordenador ordenador, int iRamAmpliar);
        Ordenador crearOrdenador(Ordenador ordenador);
    }

}
