package services;

import db.GeneralDB;
import db.LibrarianDao;
import db.PatronDao;
import entities.Librarian;
import entities.Patron;

import java.sql.SQLException;
import java.sql.Statement;

public class LoginSession {
    private GeneralDB db = new GeneralDB();
    private Statement statement;
    private LibrarianDao librarianDao;
    private PatronDao patronDao;

    public LoginSession() {
    }

    public Librarian loginLibrarian(String id, String password) {
        try {
            statement = db.setConnection();
            librarianDao = new LibrarianDao(statement.getConnection());
            Librarian librarian = librarianDao.get(Integer.parseInt(id));
            String librarianPassword = password.replaceAll("\"", "");
            if (librarian != null && librarian.getPassword().equals(librarianPassword)) {
                return librarian;
            } else {
                return null;
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Patron loginPatron(String id, String password) {
        try {
            statement = db.setConnection();
            patronDao = new PatronDao(statement.getConnection());
            Patron patron = patronDao.get(Integer.parseInt(id));
            if (patron != null && patron.getPassword().equals(password)) {
                return patron;
            } else {
                return null;
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
