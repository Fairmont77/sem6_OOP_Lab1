package entities;

import java.util.Objects;

public class Patron {

    private int id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String sex;
    private String dateOfBirth;
    private String phone;
    private String password;

    public Patron(int id, String lastName, String firstName, String patronymic, String sex, String dateOfBirth, String phone, String password) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Patron patron = (Patron) obj;
        return id == patron.id &&
                Objects.equals(lastName, patron.lastName) &&
                Objects.equals(firstName, patron.firstName) &&
                Objects.equals(patronymic, patron.patronymic) &&
                Objects.equals(phone, patron.phone) &&
                Objects.equals(password, patron.password);
    }

    @Override
    public String toString() {
        return "Patron{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getSex() {
        return sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}
