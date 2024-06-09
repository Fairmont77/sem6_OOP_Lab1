import db.AppointmentDao;
import db.GeneralDB;
import db.PatientDao;
import db.StaffDao;
import entities.Appointment;
import entities.Patient;
import entities.Staff;

import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        GeneralDB db = new GeneralDB();
        Statement statement = db.setConnection();
        Patient patient = new Patient(1324, "Petrenko", "Petro", "Petrovych", "M", "1992-11-11", "+3809546221", "mypassword033");
        PatientDao patientDao = new PatientDao(statement.getConnection());
        //patientDao.delete(12);
        //patientDao.add(patient);
        Staff staff = new Staff(3283, "Ivanova2", "Ivanna", "Ivanivna", "nurse", "+380972653421", "ghgh6dddgg");
        StaffDao staffDao = new StaffDao(statement.getConnection());
        // staffDao.update(3283, staff);
        //System.out.println(patientDao.getNameById(1324));
        Appointment appointment = new Appointment("2024-01-01", 3213, 1324, "diagnosis", "medication", null, "surgery");
        AppointmentDao appointmentDao = new AppointmentDao(statement.getConnection());
        //appointmentDao.add(appointment);
        //staffDao.fulfillPrescription(statement.getConnection(), 8, 3283);

    }
}