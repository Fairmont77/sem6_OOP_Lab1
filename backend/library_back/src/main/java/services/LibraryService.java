package services;

import db.GeneralDB;
import db.ReservationDao;
import entities.Reservation;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class LibraryService {
    private GeneralDB db = new GeneralDB();
    private Statement statement;
    private ReservationDao reservationDao; // Замінено на ReservationDao

    public LibraryService() throws SQLException, ClassNotFoundException {
        setUp();
    }

    private void setUp() throws SQLException, ClassNotFoundException {
        statement = db.setConnection();
        reservationDao = new ReservationDao(statement.getConnection()); // Замінено на ReservationDao
    }

    public void updateReservationStatus(int reservationId, boolean newStatus) throws SQLException {
        reservationDao.updateStatus(reservationId, newStatus);
    }


    public List<Reservation> getAllReservations() throws SQLException {
        return reservationDao.getAll();
    }
}
