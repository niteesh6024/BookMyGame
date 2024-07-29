package Backend.Project.BookMyGame.security;

import Backend.Project.BookMyGame.entity.Team;
import Backend.Project.BookMyGame.repoistory.TeamRepoistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class CustomUserService implements UserDetailsService {
    @Autowired
    private TeamRepoistory teamRepoistory;

//    public void setTeamRepoistory(TeamRepoistory teamRepoistory) {
//        this.teamRepoistory = teamRepoistory;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Team> team=teamRepoistory.findByTeamName(username);
        if(team.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        String teamName= team.get().getTeamName();
        String password= team.get().getPassword();
        Set<String> authorities = team.get().getRoles();
        Set<GrantedAuthority> grantedAuthorities =
                authorities.stream().map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toSet());
        User user= new User(teamName, password,grantedAuthorities);

//        UserDetails user=User.withUsername(teamName).password(password).authorities("ROLE_USER").build();
        System.out.println(user.toString());
        return user;
    }
}


