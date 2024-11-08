import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertData")
public class InsertData extends HttpServlet {
    private static final String URL = "jdbc:mysql://localhost:3306/vehicle"; // Change to your database URL
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ownerName = request.getParameter("ownerName");
        String vehicleNumber = request.getParameter("vehicleNumber");
        String vehicleType = request.getParameter("vehicleType");
        String slotNumber = request.getParameter("slotNumber");

        String sql = "INSERT INTO vehicle_info (`Owner Name`, `Vehicle Number`, `Vehicle Type`, `Parking Slot Number`) VALUES (?, ?, ?, ?)";


        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ownerName);
            pstmt.setString(2, vehicleNumber);
            pstmt.setString(3, vehicleType);
            pstmt.setString(4, slotNumber);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                response.getWriter().println("Vehicle parked successfully!");
            } else {
                response.getWriter().println("Failed to park vehicle.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error occurred.");
        }
    }
}
