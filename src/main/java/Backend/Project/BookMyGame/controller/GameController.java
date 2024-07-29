package Backend.Project.BookMyGame.controller;

import Backend.Project.BookMyGame.entity.Game;
import Backend.Project.BookMyGame.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/game")
public class GameController {
    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/all")
    private List<Game> getAll(){
        return gameService.getAll();
    }

    @PostMapping("/admin")
    private Game creatGame(@RequestBody Game game){
        return gameService.save(game);
    }
    @PutMapping("/admin/update")
    private ResponseEntity<?> updateGame(@RequestBody Game game){
        try {
            return new ResponseEntity<Game>(gameService.update(game),null, HttpStatus.OK);
        }
        catch (Exception exp){
            return new ResponseEntity<String>(exp.getMessage(),null, HttpStatus.NOT_FOUND);
        }

    }
    @CrossOrigin
    @GetMapping(value = "/{id}")
    private ResponseEntity<?> getGameById(@PathVariable int id){
        Game game=null;
        try {
            game=gameService.getById(id);
            return new ResponseEntity<Game>(game,null, HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<String>(exception.getMessage(),null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/admin/{id}")
    private ResponseEntity<String> deleteGameById(@PathVariable int id){
        try {
            gameService.delete(id);
            return new ResponseEntity<String>("Successfully deleted",null,HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<String>(exception.getMessage(),null, HttpStatus.NOT_FOUND);
        }
    }
//    @DeleteMapping()
//    private ResponseEntity<String> deleteGames(){
//        gameService.deleteAll();
//        return new ResponseEntity<String>("Successfully deleted",null,HttpStatus.OK);
//    }
}
