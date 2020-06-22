package ejercicio2.tablaClase;

import ejercicio2.modelo.Ordenador;
import ejercicio2.modelo.OrdenadorDAO;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class OrdenadoresTableModel extends AbstractTableModel {
    List<Ordenador> ordenadores;
    String[] columnas = {"Id", "Ram", "Ssd", "Pantalla"};

    public OrdenadoresTableModel(List<Ordenador> ordenadores) {
        this.ordenadores = ordenadores;
    }

    public void setOrdenadores(List<Ordenador> ordenadores) {
        this.ordenadores = ordenadores;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return ordenadores.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Ordenador ordenador = ordenadores.get(fila);
        switch (columna) {
            case 0: return ordenador.getId();
            case 1: return ordenador.getRam();
            case 2: return ordenador.getSsd();
            case 3: return ordenador.getPantalla();
        }
        return "";
    }

    @Override
    public boolean isCellEditable(int fila, int columna) {
        if (columna == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void setValueAt(Object aValue, int fila, int columna) {
        int nuevoValor = Integer.parseInt((String)aValue);
        Ordenador ordenador = ordenadores.get(fila);
        switch (columna) {
            case 1: ordenador.setRam(nuevoValor);
            break;
            case 2: ordenador.setSsd(nuevoValor);
            break;
            case 3: ordenador.setPantalla(nuevoValor);
        }
        OrdenadorDAO dao = new OrdenadorDAO();
        dao.actualizarOrdenadorPorId(ordenador);
    }
}
