package Backend.Project.BookMyGame.service;

import Backend.Project.BookMyGame.entity.Team;
import Backend.Project.BookMyGame.repoistory.TeamRepoistory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TeamServiceImp implements TeamService {
    private TeamRepoistory teamRepoistory;
    private final PasswordEncoder passwordEncoder;

    public TeamServiceImp(TeamRepoistory teamRepoistory, PasswordEncoder passwordEncoder) {
        this.teamRepoistory = teamRepoistory;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Team getById(int id) throws Exception {
        Optional<Team> optionalTeam = teamRepoistory.findById(id);
        if(optionalTeam.isEmpty()){
            throw  new Exception("Team Not Found");
        }
        return optionalTeam.get();
    }
    @Override
    public Team getByName(String team){
        return teamRepoistory.findByTeamName(team).get();
    }
    @Override
    public List<Team> getAll() {
        List<Team> list=new ArrayList<>();
        teamRepoistory.findAll().forEach(team -> {
            list.add(team);
        });
        return list;
    }

    @Override
    public Team save(Team team) {
        team.setPassword(passwordEncoder.encode(team.getPassword()));
        team.setRoles(Stream.of("ROLE_USER").collect(Collectors.toSet()));
        return teamRepoistory.save(team);
    }

    @Override
    public void update(String oldPassword, String newPassword) throws Exception {
        var currentTeam =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName=currentTeam.getUsername();
        Optional<Team> team=teamRepoistory.findByTeamName(userName);

        if(team.isEmpty()){
            throw  new Exception("Team Not Found");
        }

        if(!passwordEncoder.matches(oldPassword, team.get().getPassword())){
            throw new IllegalStateException("Wrong password");
        }

        team.get().setPassword(passwordEncoder.encode(newPassword));
        teamRepoistory.save(team.get());
    }

    @Override
    public void delete(int id) throws Exception {
        try{
            getById(id);
            teamRepoistory.deleteById(id);
        }
        catch (Exception exception){
            throw exception;
        }
    }
    @Override
    public void deleteAll(){
        teamRepoistory.deleteAll();
    }
}
