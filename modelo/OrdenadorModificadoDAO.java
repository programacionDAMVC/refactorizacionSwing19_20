package ejercicio2.modelo;

import ejercicio2.Conexion;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdenadorModificadoDAO implements InterfazOrdenadorModificadoDAO {
    private static Connection conexion;

    static {
        try {
            conexion = Conexion.getConexion();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Ordenador> listarTodosOrdenadores() {
        List<Ordenador> listaOrdenadores = new ArrayList<>();
        String sql = "SELECT * FROM portatil;";
        try (Statement statement = conexion.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next())
                    listaOrdenadores.add(new Ordenador(resultSet.getInt(1), resultSet.getInt(2),
                            resultSet.getInt(3), resultSet.getInt(4)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaOrdenadores;
    }

    @Override
    public Ordenador listarOrdenadorePorId(int id) {
        Ordenador ordenador = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM portatil WHERE id = ?;";
        try {
            preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println(id);

                ordenador = new Ordenador(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getInt(3), resultSet.getInt(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return ordenador;
    }

    @Override
    public boolean ampliarMemoriaRAMOrdenadorPorId(Ordenador ordenador) {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE portatil SET ram = ? WHERE id = ?;";
        int resultado = 0;
        try {
            preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setInt(1, ordenador.getRam());
            preparedStatement.setInt(2, ordenador.getId());
            resultado = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return resultado == 1;
    }

    @Override
    public boolean borrarOrdenadorPorId(int id) throws OrdenadorException {
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM portatil  WHERE id = ?;";
        int resultado = 0;
        try {
            preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultado = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new OrdenadorException(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return resultado == 1;
    }

    @Override
    public boolean crearOrdeandor(Ordenador ordenador) {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO portatil (ram, ssd, pantalla) VALUES (?, ?, ?);";
        int resultado = 0;
        try {
            preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setInt(1, ordenador.getRam());
            preparedStatement.setInt(2, ordenador.getSsd());
            preparedStatement.setInt(3, ordenador.getPantalla());

            resultado = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return resultado == 1;
    }

    public static void main(String[] args) {
        OrdenadorModificadoDAO ordenadorDAO = new OrdenadorModificadoDAO();

        System.out.printf("Ordenador de id = %d%n%s%n", 100, ordenadorDAO.listarOrdenadorePorId(100));
        System.out.println("--------------------------------------------");
        Ordenador ordenador = new Ordenador(100, 128, 0, 0);
        System.out.printf("Actualizando ram a %d de ordenador de id = %d%n%s%n",
                ordenador.getRam(),ordenador.getId(), ordenadorDAO.ampliarMemoriaRAMOrdenadorPorId(ordenador));
        System.out.println("--------------------------------------------");
        try {
            System.out.printf("Borrado ordenador de id %d: %B%n", 100, ordenadorDAO.borrarOrdenadorPorId(100));
        } catch (OrdenadorException e) {
            e.printStackTrace();
        }
        System.out.printf("Ordenador de id = %d%n%s%n", 100, ordenadorDAO.listarOrdenadorePorId(100));
        System.out.println("--------------------------------------------");
        System.out.printf("Insertado de nuevo ordenador: %B%n", ordenadorDAO.crearOrdeandor(new Ordenador(100, 100, 100)));
        List<Ordenador> listaOrdenadores = ordenadorDAO.listarTodosOrdenadores();
        listaOrdenadores.forEach(System.out::println);
    }
}
