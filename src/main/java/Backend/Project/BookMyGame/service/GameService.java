package Backend.Project.BookMyGame.service;

import Backend.Project.BookMyGame.entity.Game;

import java.util.List;

public interface GameService {

    Game getById(int id) throws Exception;
//    List<Game> search(String pattern);
    List<Game> getAll();
    Game save(Game game);
    Game update(Game game) throws Exception;
    void delete(int id) throws Exception;
    void deleteAll();
}
