package Backend.Project.BookMyGame.repoistory;

import Backend.Project.BookMyGame.entity.Game;
import Backend.Project.BookMyGame.entity.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeamRepoistory extends CrudRepository<Team, Integer> {
    Optional<Team> findByTeamName(String teamName);
}

