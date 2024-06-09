import db.ReservationDao;
import db.GeneralDB;
import db.PatronDao;
import db.LibrarianDao;
import entities.Reservation;
import entities.Patron;
import entities.Librarian;

import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        GeneralDB db = new GeneralDB();
        Statement statement = db.setConnection();

        Patron patron = new Patron(1324, "Slava", "Gora", "Petrovych", "M", "1912-11-11", "+123424312", "Aoa");
        PatronDao patronDao = new PatronDao(statement.getConnection());
        // patronDao.delete(12);
        // patronDao.add(patron);

        Librarian librarian = new Librarian(3283, "Name1", "Name2", "Name3", "1", "ksjngfskgn@mail.com", "qwerty123");
        LibrarianDao librarianDao = new LibrarianDao(statement.getConnection());
        // librarianDao.update(3283, librarian);
        // System.out.println(patronDao.getNameById(1324));

        Reservation reservation = new Reservation("2024-06-06", 3213, 1324, "Book Title", "Reservation Details", true);
        ReservationDao reservationDao = new ReservationDao(statement.getConnection());
        // reservationDao.add(reservation);
        // reservationDao.updateStatus(8, true);

    }
}
