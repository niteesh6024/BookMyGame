package Backend.Project.BookMyGame.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
public class BookingDetails {
    @Id
    @GeneratedValue
    private UUID bookingId;
    @ManyToOne
    @JoinColumn(name = "fk_game_id")
    @JsonIgnoreProperties("bookingDetails")
    private Game game;
    @ManyToOne
    @JoinColumn(name = "fk_team_id")
    @JsonIgnoreProperties("bookingDetails")
    private Team team;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "BookingDetails{" +
                "bookingId=" + bookingId +
                ", game=" + game +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
