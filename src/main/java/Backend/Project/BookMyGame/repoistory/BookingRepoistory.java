package Backend.Project.BookMyGame.repoistory;

import Backend.Project.BookMyGame.entity.BookingDetails;
import Backend.Project.BookMyGame.entity.Game;
import Backend.Project.BookMyGame.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepoistory extends JpaRepository<BookingDetails,UUID> {

    List<BookingDetails> findAllByTeam(Team team);
    List<BookingDetails> findAllByGame(Game game);
//    @Query("SELECT bd FROM BookingDetails bd WHERE bd.team.id = :teamId AND bd.game.id = :gameId")
//    List<BookingDetails> findAllByTeamIdAndGameId(int teamId, int gameId);
    Optional<List<BookingDetails>> findAllByTeamAndGame(Team team, Game game);

    Optional<BookingDetails> findById(UUID uuid);

    List<BookingDetails> findByGameAndEndTimeGreaterThanAndStartTimeLessThan(
             Game game,LocalDateTime startTime, LocalDateTime endTime);

    List<BookingDetails> findByGameAndStartTimeGreaterThanAndEndTimeLessThan(
            Game game,LocalDateTime startTime, LocalDateTime endTime);

}
