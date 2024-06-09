package db;

import db.parser.Parser;
import entities.Patron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PatronDao implements Dao<Patron> {

    private static final Logger logger = Logger.getLogger(PatronDao.class.getName());
    private final Connection connection;

    public PatronDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Patron get(int id) {
        try {
            String sql = "SELECT * FROM public.\"Patrons\" WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            if (!result.next()) {
                return null;
            }
            Parser parser = new Parser();
            return parser.createPatronDB(result);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getNameById(int id) {
        try {
            String sql = "SELECT CONCAT(last_name, ' ', first_name, ' ', patronymic) AS full_name FROM public.\"Patrons\" WHERE id = ?";
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

    @Override
    public List<Patron> getAll() {
        List<Patron> patrons = new ArrayList<>();
        try {
            String sql = "SELECT * FROM public.\"Patrons\"";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            Parser parser = new Parser();
            while (result.next()) {
                patrons.add(parser.createPatronDB(result));
            }
            return patrons;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void add(Patron patron) throws SQLException {
        try {
            String sql = "INSERT INTO public.\"Patrons\" (id, last_name, first_name, patronymic, sex, date_of_birth, phone, password) VALUES (?, ?, ?, ?, ?, to_date(?, 'YYYY-MM-DD'), ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, patron.getId());
            ps.setString(2, patron.getLastName());
            ps.setString(3, patron.getFirstName());
            ps.setString(4, patron.getPatronymic());
            ps.setString(5, patron.getSex());
            ps.setString(6, patron.getDateOfBirth());
            ps.setString(7, patron.getPhone());
            ps.setString(8, patron.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error: can't add new patron");
            throw e;
        }
    }

    @Override
    public void update(int id, Patron patron) {
        try {
            String sql = "UPDATE public.\"Patrons\" SET last_name=?, first_name=?, patronymic=?, sex=?, date_of_birth=to_date(?, 'YYYY-MM-DD'), phone=?, password=? WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, patron.getLastName());
            ps.setString(2, patron.getFirstName());
            ps.setString(3, patron.getPatronymic());
            ps.setString(4, patron.getSex());
            ps.setString(5, patron.getDateOfBirth());
            ps.setString(6, patron.getPhone());
            ps.setString(7, patron.getPassword());
            ps.setInt(8, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                logger.warning("No rows affected while updating patron with ID: " + patron.getId());
            }
        } catch (SQLException e) {
            logger.severe("Error: can't update patron with ID: " + patron.getId());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM public.\"Patrons\" WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error: can't delete patron with ID: " + id);
            throw new RuntimeException(e.getMessage());
        }
    }
}
