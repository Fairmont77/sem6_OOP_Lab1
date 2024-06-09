package entities;

import java.util.Objects;

public class Librarian {

    private final String lastName;
    private final String firstName;
    private final String patronymic;
    private final String phone;
    private final String position;
    private final String password;
    private int id;

    public Librarian(int id, String lastName, String firstName, String patronymic, String position, String phone, String password) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.position = position;
        this.phone = phone;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Librarian librarian = (Librarian) obj;
        return id == librarian.id &&
                Objects.equals(lastName, librarian.lastName) &&
                Objects.equals(firstName, librarian.firstName) &&
                Objects.equals(patronymic, librarian.patronymic) &&
                Objects.equals(phone, librarian.phone) &&
                Objects.equals(position, librarian.position) &&
                Objects.equals(password, librarian.password);
    }

    @Override
    public String toString() {
        return "Librarian{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", position='" + position + '\'' +
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

    public String getPosition() {
        return position;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}
