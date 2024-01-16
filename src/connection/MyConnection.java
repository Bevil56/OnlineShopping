package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class MyConnection {
    public static final String url = "jdbc:sqlserver://localhost:1433;databaseName=OnlineShopping;user=sa;password=amSPBi1E2bP;encrypt=true;trustServerCertificate=true";
    public static Connection connection = null;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(url);          
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "" + ex, "", JOptionPane.WARNING_MESSAGE);
        }
        return connection;
    }
}

