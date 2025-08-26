package techthor;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");

        String url = "jdbc:oracle:thin:@localhost:1521/XE";
        String user = "ec";
        String password = "ec";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM product");
             PrintWriter out = resp.getWriter()) {

            StringBuilder json = new StringBuilder();
            json.append("[");

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            boolean first = true;

            while (rs.next()) {
                if (!first) json.append(",");
                json.append("{");
                for (int i = 1; i <= colCount; i++) {
                    json.append("\"").append(meta.getColumnName(i)).append("\":");
                    Object val = rs.getObject(i);
                    if (val instanceof Number) json.append(val);
                    else json.append("\"").append(val).append("\"");
                    if (i < colCount) json.append(",");
                }
                json.append("}");
                first = false;
            }
            json.append("]");

            out.print(json.toString());

        } catch (SQLException e) {
            e.printStackTrace(resp.getWriter());
        }
    }
}
