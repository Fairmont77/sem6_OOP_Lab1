package db.parser;

import entities.Librarian;
import entities.Patron;
import entities.Reservation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Parser {

    public Patron createPatronDB(ResultSet patronDB) throws SQLException {
        Patron patron = new Patron(
                patronDB.getInt("id"),
                patronDB.getString("last_name"),
                patronDB.getString("first_name"),
                patronDB.getString("patronymic"),
                patronDB.getString("sex"),
                patronDB.getString("date_of_birth"),
                patronDB.getString("phone"),
                patronDB.getString("password"));
        return patron;
    }

    public Reservation createReservationDB(ResultSet reservationDB) throws SQLException {
        Reservation reservation = new Reservation(
                reservationDB.getInt("id"),
                reservationDB.getString("date"),
                reservationDB.getInt("librarian_id"),
                reservationDB.getInt("patron_id"),
                reservationDB.getString("book_title"),
                reservationDB.getString("reservation_details"),
                reservationDB.getBoolean("status"));
        return reservation;
    }

    public Librarian createLibrarianDB(ResultSet librarianDB) throws SQLException {
        Librarian librarian = new Librarian(
                librarianDB.getInt("id"),
                librarianDB.getString("last_name"),
                librarianDB.getString("first_name"),
                librarianDB.getString("patronymic"),
                librarianDB.getString("email"),
                librarianDB.getString("position"),
                librarianDB.getString("password"));
        return librarian;
    }
}
