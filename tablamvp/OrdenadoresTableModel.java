package ejercicio2.tabla;

import ejercicio2.modelo.Ordenador;
import ejercicio2.modelo.OrdenadorDAO;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class OrdenadoresTableModel extends AbstractTableModel {

    List<Ordenador> ordenadores;
    String[] columnas = {"Id", "Ram", "SSD", "Pantalla"};

    public OrdenadoresTableModel(List<Ordenador> ordenadores) {
        this.ordenadores = ordenadores;
    }

    public void setListaOrdenadores(List<Ordenador> ordenadores) {
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
    public Object getValueAt(int row, int col) {
        Ordenador ordenador = ordenadores.get(row);
        switch (col) {
            case 0: return ordenador.getId();
            case 1: return ordenador.getRam();
            case 2: return ordenador.getSsd();
            case 3: return ordenador.getPantalla();
        }
        return "";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex==0)
            return false;
        else
            return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Ordenador ordenador = ordenadores.get(rowIndex);
        String sValor = (String)aValue;
        int iValor = Integer.parseInt(sValor);
        switch (columnIndex) {
            case 1: ordenador.setRam(iValor);
            break;
            case 2: ordenador.setSsd(iValor);
            break;
            case 3: ordenador.setPantalla(iValor);
            break;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
        OrdenadorDAO ordenadorDAO = new OrdenadorDAO();
        ordenadorDAO.ampliarMemoriaRAMOrdenadorPorId(ordenador);
    }
}
