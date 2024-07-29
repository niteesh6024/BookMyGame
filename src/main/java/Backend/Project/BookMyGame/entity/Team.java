package Backend.Project.BookMyGame.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
import java.util.Set;

@Entity
public class Team {
    @Id
    @GeneratedValue
    private int teamId;
    private String teamName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
//    @JsonIgnore
    private Set<String> roles;
    @OneToMany(mappedBy = "team",cascade = CascadeType.ALL)
//    @JsonIgnoreProperties("team")
    private List<BookingDetails> bookingDetails;
    public List<BookingDetails> getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(List<BookingDetails> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }
//    @JsonIgnore
    public String getPassword() {
        return password;
    }
    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
