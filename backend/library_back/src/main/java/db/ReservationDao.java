package db;

import db.parser.Parser;
import entities.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class ReservationDao implements Dao<Reservation> {

    private static final Logger logger = Logger.getLogger(ReservationDao.class.getName());
    private final Connection connection;

    public ReservationDao(Connection connection) {
        this.connection = connection;
    }

    public void updateStatus(int reservationId, boolean newStatus) throws SQLException {
        String sql = "UPDATE public.\"Reservations\" SET status = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setBoolean(1, newStatus);
        ps.setInt(2, reservationId);
        ps.executeUpdate();
    }


    @Override
    public Reservation get(int id) {
        String sql = "SELECT * FROM public.\"Reservations\" WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet result = ps.executeQuery()) {
                if (result.next()) {
                    Parser parser = new Parser();
                    return parser.createReservationDB(result);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error getting reservation with ID: " + id + ": " + e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }


    public List<Reservation> getAllByPatron(int patronId) {
        List<Reservation> reservations = new ArrayList<>();

        try {
            String sql = "SELECT * FROM public.\"Reservations\" WHERE patron_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, patronId);
            ResultSet result = ps.executeQuery();

            Parser parser = new Parser();
            while (result.next()) {
                Reservation reservation = parser.createReservationDB(result);
                reservations.add(reservation);
            }

            return reservations;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Reservation> getAllByLibrarian(int librarianId) {
        List<Reservation> reservations = new ArrayList<>();

        try {
            String sql = "SELECT * FROM public.\"Reservations\" WHERE librarian_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, librarianId);
            ResultSet result = ps.executeQuery();

            Parser parser = new Parser();
            while (result.next()) {
                Reservation reservation = parser.createReservationDB(result);
                reservations.add(reservation);
            }

            return reservations;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();

        try {
            String sql = "SELECT * FROM public.\"Reservations\"";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet result = ps.executeQuery();

            Parser parser = new Parser();
            while (result.next()) {
                Reservation reservation = parser.createReservationDB(result);
                reservations.add(reservation);
            }

            return reservations;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void add(Reservation reservation) throws SQLException {
        try {
            String sql = "INSERT INTO public.\"Reservations\" (date, librarian_id, patron_id, book_title, reservation_details, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, reservation.getDate());
            ps.setInt(2, reservation.getLibrarianId());
            ps.setInt(3, reservation.getPatronId());
            ps.setString(4, reservation.getBookTitle());
            ps.setString(5, reservation.getReservationDetails());
            ps.setBoolean(6, reservation.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error: can't add new reservation");
            throw e;
        }
    }

    @Override
    @Override
    public void update(int id, Reservation reservation) {
        String sql = "UPDATE public.\"Reservations\" SET date=?, librarian_id=?, patron_id=?, book_title=?, reservation_details=?, status=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, reservation.getDate());
            ps.setInt(2, reservation.getLibrarianId());
            ps.setInt(3, reservation.getPatronId());
            ps.setString(4, reservation.getBookTitle());
            ps.setString(5, reservation.getReservationDetails());
            ps.setBoolean(6, reservation.getStatus());
            ps.setInt(7, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                logger.warning("No rows affected while updating reservation with ID: " + id);
            }
        } catch (SQLException e) {
            logger.severe("Error: can't update reservation with ID: " + id);
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public void delete(int id) {
        String sql = "DELETE FROM public.\"Reservations\" WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
