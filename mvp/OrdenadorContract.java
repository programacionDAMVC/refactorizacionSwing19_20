package ejercicio2.mvp;

import ejercicio2.modelo.Ordenador;

public interface OrdenadorContract {
    public interface Vista {
        void mostrarError(String error);
    }

    public interface Presenter {
        Ordenador verOrdenador();
        Ordenador verAnteriorOrdenador();
        Ordenador verSiguienteOrdenador();
        void borrarOrdenador(int idBorrar);
        void crearOrdenador(int iRam, int iSSD, int iPULG);
        Ordenador actualizarOrdenador(int iRamAmpliar);
    }
}
