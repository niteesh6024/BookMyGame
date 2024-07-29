package Backend.Project.BookMyGame.controller;

import Backend.Project.BookMyGame.entity.Team;
import Backend.Project.BookMyGame.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("login")
public class AuthenticationController {
    private TeamService teamService;

    public AuthenticationController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(value = "/basic/{teamName}")
    private ResponseEntity<?> basicAuthentication(@PathVariable String teamName){
        return new ResponseEntity<Team>(teamService.getByName(teamName),null, HttpStatus.OK);
    }


    @PutMapping (value = "/basic/{teamName}")
    private ResponseEntity<?> basicAuthenticationput(@PathVariable String teamName){
        return new ResponseEntity<Team>(teamService.getByName(teamName),null, HttpStatus.OK);
    }
}
