package techthor;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@//localhost:1521/XE"; // anpassen!
        String user = "ec"; // dein User
        String password = "ec"; // dein Passwort

        String sql = "SELECT * FROM product"; // Tabelle product

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(meta.getColumnName(i) + "=" + rs.getObject(i) + " ");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
