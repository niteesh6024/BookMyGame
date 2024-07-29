package Backend.Project.BookMyGame.repoistory;

import Backend.Project.BookMyGame.entity.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepoistory extends CrudRepository<Game, Integer> {
}

