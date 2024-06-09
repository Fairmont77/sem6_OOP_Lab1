package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import entities.Patron;
import entities.Reservation;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.PatronService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class PatronServlet extends HttpServlet {
    private PatronService patronService;

    @Override
    public void init() throws ServletException {
        try {
            patronService = new PatronService();
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Failed to initialize PatronService", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo != null ? pathInfo.split("/") : new String[0];
        String idParameter = pathParts.length > 1 ? pathParts[1] : "";

        if (!idParameter.isEmpty()) {
            int id = Integer.parseInt(idParameter);
            PrintWriter out = response.getWriter();
            Gson gson = new Gson();
            String json;

            try {
                List<Reservation> reservations = patronService.getAllReservations(id);
                json = gson.toJson(reservations);
            } catch (SQLException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving reservations for patron");
                return;
            }

            out.print(json);
            out.flush();
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or invalid 'id' parameter");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        Patron patron = objectMapper.readValue(request.getReader(), Patron.class);

        try {
            patronService.addPatron(patron);
            response.setStatus(HttpServletResponse.SC_CREATED);
            PrintWriter out = response.getWriter();
            out.print("{\"message\": \"Patron added successfully\"}");
            out.flush();
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding new patron");
        }
    }
}
