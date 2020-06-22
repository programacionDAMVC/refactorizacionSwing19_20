package ejercicio2;

import org.sqlite.SQLiteConfig;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
    private static Connection conexion = null;

    private Conexion() throws SQLException, IOException {
        // String url = "BD/uno.db";
        // String driver = "jdbc:sqlite:";
        //driver + url :    jdbc:sqlite:BD/uno.db
        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(new Conexion.Cierre());
        Properties properties = new Properties();
        properties.load(new FileReader("configuracion/db.properties"));
        String url = properties.getProperty("url");
        String driver = properties.getProperty("driver");
        conexion = DriverManager.getConnection(driver + url);
    }

    public static Connection getConexion() throws IOException, SQLException {
        if (conexion == null)
            new Conexion();

        return conexion;
    }

   static class Cierre extends Thread {
        @Override
        public void run() {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        System.out.println(Conexion.getConexion());
    }
}
