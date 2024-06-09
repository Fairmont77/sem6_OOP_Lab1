package entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Reservation {

    private int id;
    private String date;
    private int librarianId;
    private int patronId;
    private String bookTitle;  // Assuming reservations are for books
    private String reservationDetails;  // Details about the reservation
    private boolean status;  // If the reservation is active or completed

    @JsonCreator
    public Reservation(
            @JsonProperty("id") int id,
            @JsonProperty("date") String date,
            @JsonProperty("librarianId") int librarianId,
            @JsonProperty("patronId") int patronId,
            @JsonProperty("bookTitle") String bookTitle,
            @JsonProperty("reservationDetails") String reservationDetails,
            @JsonProperty("status") boolean status) {
        this.id = id;
        this.date = date;
        this.librarianId = librarianId;
        this.patronId = patronId;
        this.bookTitle = bookTitle;
        this.reservationDetails = reservationDetails;
        this.status = status;
    }

    public Reservation() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reservation reservation = (Reservation) obj;
        return id == reservation.id &&
                librarianId == reservation.librarianId &&
                patronId == reservation.patronId &&
                status == reservation.status &&
                Objects.equals(date, reservation.date) &&
                Objects.equals(bookTitle, reservation.bookTitle) &&
                Objects.equals(reservationDetails, reservation.reservationDetails);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", librarianId=" + librarianId +
                ", patronId=" + patronId +
                ", bookTitle='" + bookTitle + '\'' +
                ", reservationDetails='" + reservationDetails + '\'' +
                ", status=" + status +
                '}';
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getLibrarianId() {
        return librarianId;
    }

    public int getPatronId() {
        return patronId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getReservationDetails() {
        return reservationDetails;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
