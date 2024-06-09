package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import entities.Reservation;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ReservationService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class ReservationsServlet extends HttpServlet {
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        try {
            reservationService = new ReservationService();
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Failed to initialize ReservationService", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo != null ? pathData.split("/") : new String[0];
        String idParameter = pathParts.length > 1 ? pathParts[1] : null;

        if (idParameter != null && !idParameter.isEmpty()) {
            int patronId = Integer.parseInt(idParameter);
            PrintWriter out = response.getWriter();
            Gson gson = new Gson();
            String json;

            try {
                List<Reservation> reservations = reservationService.getAllReservationsByPatron(patronId);
                json = gson.toJson(reservations);
            } catch (SQLException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving reservations");
                return;
            }

            out.print(json);
            out.flush();
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("Missing or invalid 'id' parameter");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        Reservation reservation = objectMapper.readValue(request.getReader(), Reservation.class);

        try {
            reservationService.addReservation(reservation);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_AUT_SERVER_ERROR, "Error adding new reservation");
        }
    }
}
