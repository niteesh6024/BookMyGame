package Backend.Project.BookMyGame.service;

import Backend.Project.BookMyGame.entity.Game;
import Backend.Project.BookMyGame.entity.Team;

import java.util.List;

public interface TeamService {
    Team getById(int id) throws Exception;
    List<Team> getAll();
    Team save(Team team);
    void update(String oldPassword, String newPassword) throws Exception;
    void delete(int id) throws Exception;
    void deleteAll();

    Team getByName(String team) ;
}
