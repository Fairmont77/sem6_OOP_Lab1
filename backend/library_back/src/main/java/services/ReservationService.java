package services;

import db.GeneralDB;
import db.ReservationDao;
import entities.Reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReservationService {
    private GeneralDB db = new GeneralDB();
    private Connection connection;  // Using Connection directly instead of Statement
    private ReservationDao reservationDao;

    public ReservationService() throws SQLException, ClassNotFoundException {
        setUp();
    }

    private void setUp() throws SQLException, ClassNotFoundException {
        connection = db.setConnection();  // Ensures a database connection is established
        reservationDao = new ReservationDao(connection);  // Create a DAO specifically for reservations
    }

    public List<Reservation> getAllReservationsByLibrarian(int librarianId) throws SQLException {
        // Retrieves all reservations managed by a specific librarian
        return reservationDao.getAllByLibrarian(librarianId);
    }

    public void addReservation(Reservation reservation) throws SQLException {
        // Adds a new reservation to the database
        reservationDao.add(reservation);
    }

    public List<Reservation> getAllReservations() throws SQLException {
        // Retrieves all reservations in the system
        return reservationDao.getAll();
    }

    public void updateReservation(int reservationId, Reservation updatedReservation) throws SQLException {
        // Updates an existing reservation
        reservationDao.update(reservationId, updatedReservation);
    }

    public void deleteReservation(int reservationId) throws SQLException {
        // Deletes a reservation from the database
        reservationDao.delete(reservationId);
    }

    public Reservation getReservation(int reservationId) throws SQLException {
        // Retrieves a single reservation by its ID
        return reservationDao.get(reservationId);
    }
}
