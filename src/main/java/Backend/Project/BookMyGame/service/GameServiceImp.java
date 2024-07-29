package Backend.Project.BookMyGame.service;

import Backend.Project.BookMyGame.entity.Game;
import Backend.Project.BookMyGame.repoistory.GameRepoistory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImp implements GameService {
    private GameRepoistory gameRepoistory;

    public GameServiceImp(GameRepoistory gameRepoistory) {
        this.gameRepoistory = gameRepoistory;
    }

    @Override
    public Game getById(int id) throws Exception {
        Optional<Game> optional=gameRepoistory.findById(id);
        if(optional.isEmpty()){
            throw new Exception("no game found");
        }
        return optional.get();
    }

//    @Override
//    public List<Game> search(String pattern) {
//        return null;
//    }

    @Override
    public List<Game> getAll() {
        ArrayList<Game> games=new ArrayList<>();
        gameRepoistory.findAll().forEach(game -> {
            games.add(game);
        });
        return games;
    }

    @Override
    public Game save(Game game) {
        return gameRepoistory.save(game);
    }

    @Override
    public Game update(Game game) throws Exception{
        int id=game.getGameId();
        try{
            getById(id);
            return gameRepoistory.save(game);
        }
        catch (Exception exception){
            throw exception;
        }
    }

    @Override
    public void delete(int id) throws Exception {
        Game game;
        try{
            game=getById(id);
            gameRepoistory.delete(game);
        }
        catch (Exception exception){
            throw exception;
        }
    }
    @Override
    public void deleteAll(){
        gameRepoistory.deleteAll();
    }
}
