package Backend.Project.BookMyGame.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class GameFilterChain {
    @Bean
    @Order(3)
    SecurityFilterChain configureFilterChainGameAdmin(HttpSecurity http) throws Exception{
        return http.securityMatcher("/api/game/admin/**")
                .authorizeHttpRequests(
                        request ->
                                request.requestMatchers("/api/game/**").hasRole("ADMIN")
                )
//                .sessionManagement(
//                        session ->
//                                session.sessionCreationPolicy(
//                                        SessionCreationPolicy.STATELESS)
//                )
                .csrf(csrf->csrf.disable())
                .httpBasic(withDefaults())
                .build();
    }
    @Order(4)
    @Bean
    SecurityFilterChain configureFilterChainGameUser(HttpSecurity http) throws Exception{
        return http.securityMatcher("/api/game/**")
                .authorizeHttpRequests(request ->
                            request.requestMatchers("/api/game/**").hasRole("USER")
                )
//                .sessionManagement(
//                        session ->
//                                session.sessionCreationPolicy(
//                                        SessionCreationPolicy.STATELESS)
//                )
                .csrf(csrf->csrf.disable())
                .httpBasic(withDefaults())
                .build();
    }
    @Order(5)
    @Bean
    SecurityFilterChain configureFilterChainTeamUser(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(request ->{
                            request.requestMatchers("/api/team/all").hasRole("ADMIN");
                            request.anyRequest().authenticated();
                        }
                )
                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS)
                )

                .csrf(csrf->csrf.disable())
                .httpBasic(withDefaults())
                .build();
    }
}
