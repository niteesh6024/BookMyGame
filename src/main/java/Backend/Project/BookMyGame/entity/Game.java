package Backend.Project.BookMyGame.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Game {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameId;
    private String name;
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
//    @JsonIgnoreProperties("game")
    private List<BookingDetails> bookingDetails;

    public List<BookingDetails> getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(List<BookingDetails> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", name='" + name + '\'' +
                ", bookingDetails=" + bookingDetails +
                '}';
    }
}
