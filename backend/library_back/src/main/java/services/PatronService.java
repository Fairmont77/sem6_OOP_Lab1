package services;

import db.GeneralDB;
import db.PatronDao;
import db.ReservationDao;
import entities.Patron;
import entities.Reservation;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PatronService {
    private GeneralDB db = new GeneralDB();
    private Statement statement;
    private PatronDao patronDao;
    private ReservationDao reservationDao;

    public PatronService() throws SQLException, ClassNotFoundException {
        setUp();
    }

    private void setUp() throws SQLException, ClassNotFoundException {
        statement = db.setConnection();
        patronDao = new PatronDao(statement.getConnection());
        reservationDao = new ReservationDao(statement.getConnection());
    }

    public List<Reservation> getAllReservations(int patronId) throws SQLException {
        // Retrieves all reservations associated with a specific patron
        return reservationDao.getAllByPatron(patronId);
    }

    public void addPatron(Patron patron) throws SQLException {
        // Adds a new patron to the system
        patronDao.add(patron);
    }

    public Patron getPatron(int patronId) throws SQLException {
        // Retrieves a specific patron by their ID
        return patronDao.get(patronId);
    }

    public void updatePatron(int patronId, Patron updatedPatron) throws SQLException {
        // Updates information for an existing patron
        patronDao.update(patronId, updatedPatron);
    }

    public void deletePatron(int patronId) throws SQLException {
        // Deletes a patron from the system
        patronDao.delete(patronId);
    }
}
