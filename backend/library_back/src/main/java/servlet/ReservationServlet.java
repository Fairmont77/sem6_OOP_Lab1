package servlet;

import com.google.gson.Gson;
import entities.Reservation;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.LibraryService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class ReservationServlet extends HttpServlet {
    private LibraryService libraryService;

    @Override
    public void init() throws ServletException {
        try {
            libraryCategoryService = new LibraryService();
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Failed to initialize LibraryService", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String json;

        try {
            List<Reservation> reservations = libraryService.getAllReservations();
            json = gson.toJson(reservations);
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving reservations");
            return;
        }

        out.print(json);
        out.flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo != null ? pathInfo.split("/") : new String[0];
        String idParameter = pathParts.length > 1 ? pathParts[1] : null;

        if (idParameter != null && !idParameter.isEmpty()) {
            int reservationId = Integer.parseInt(idParameter);

            try {
                libraryService.updateReservationStatus(reservationId, true); // Assuming we are setting the reservation as completed
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = response.getWriter();
                out.print("Reservation status updated successfully");
                out.flush();
            } catch (SQLException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating reservation status");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = response.getWriter();
            out.print("Missing or invalid 'id' parameter");
            out.flush();
        }
    }
}
