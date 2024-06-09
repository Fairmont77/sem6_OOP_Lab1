package db;

import db.parser.Parser;
import entities.Librarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LibrarianDao implements Dao<Librarian> {

    private static final Logger logger = Logger.getLogger(LibrarianDao.class.getName());
    private final Connection connection;

    public LibrarianDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Librarian get(int id) {
        try {
            String sql = "SELECT * FROM public.\"Librarians\" WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            if (!result.next()) {
                return null;
            }
            Parser parser = new Parser();
            return parser.createLibrarianDB(result);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Librarian> getAll() {
        List<Librarian> librarians = new ArrayList<>();
        try {
            String sql = "SELECT * FROM public.\"Librarians\"";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            Parser parser = new Parser();
            while (result.next()) {
                librarians.add(parser.createLibrarianDB(result));
            }
            return librarians;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void add(Librarian librarian) throws SQLException {
        try {
            String sql = "INSERT INTO public.\"Librarians\" (id, last_name, first_name, patronymic, position, phone, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, librarian.getId());
            ps.setString(2, librarian.getLastName());
            ps.setString(3, librarian.getFirstName());
            ps.setString(4, librarian.getPatronymic());
            ps.setString(5, librarian.getPosition());
            ps.setString(6, librarian.getPhone());
            ps.setString(7, librarian.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error: can't add new librarian");
            throw e;
        }
    }

    @Override
    public void update(int id, Librarian librarian) {
        try {
            String sql = "UPDATE public.\"Librarians\" SET last_name=?, first_name=?, patronymic=?, position=?, phone=?, password=? WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, librarian.getLastName());
            ps.setString(2, librarian.getFirstName());
            ps.setString(3, librarian.getPatronymic());
            ps.setString(4, librarian.getPosition());
            ps.setString(5, librarian.getPhone());
            ps.setString(6, librarian.getPassword());
            ps.setInt(7, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                logger.warning("No rows affected while updating librarian with ID: " + id);
            }
        } catch (SQLException e) {
            logger.severe("Error: can't update librarian with ID: " + id);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM public.\"Librarians\" WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error: can't delete librarian with ID: " + id);
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getNameById(int id) {
        try {
            String sql = "SELECT CONCAT(last_name, ' ', first_name, ' ', patronymic) AS full_name FROM public.\"Librarians\" WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            if (!result.next()) {
                return null;
            }
            return result.getString("full_name");
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
