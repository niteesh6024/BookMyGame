package Backend.Project.BookMyGame.controller;

import Backend.Project.BookMyGame.entity.Game;
import Backend.Project.BookMyGame.entity.Team;
import Backend.Project.BookMyGame.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/team")
//@CrossOrigin(origins = "http://localhost:3000")
public class TeamController {
    private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/all")
    private List<Team> getAll(){
        return teamService.getAll();
    }

    @PostMapping()
    private Team creatTeam(@RequestBody Team team){
        System.out.println(team.getPassword());
        System.out.println(team.getTeamName());
        return teamService.save(team);
    }
    @PutMapping("/update")
    private ResponseEntity<?> updateTeam(@RequestParam String oldPassword, @RequestParam String newPassword){
        try {
            teamService.update(oldPassword,newPassword);
            return new ResponseEntity<String>("Password updated successfully!!!",null, HttpStatus.OK);
        }
        catch (Exception exp){
            return new ResponseEntity<String>(exp.getMessage(),null, HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping(value = "/{id}")
    private ResponseEntity<?> getTeamById(@PathVariable int id){
        Team team=null;
        try {
            team=teamService.getById(id);
            return new ResponseEntity<Team>(team,null, HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<String>(exception.getMessage(),null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    private ResponseEntity<String> deleteTeamById(@PathVariable int id){
        try {
            teamService.delete(id);
            return new ResponseEntity<String>("Successfully deleted",null,HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<String>(exception.getMessage(),null, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping()
    private ResponseEntity<String> deleteTeam(){
        teamService.deleteAll();
        return new ResponseEntity<String>("Successfully deleted",null,HttpStatus.OK);
    }

}
