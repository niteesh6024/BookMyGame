package Backend.Project.BookMyGame.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class BookingDetailsFilterChain {

    @Bean
    PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    CustomUserService customUserService(){
        return new CustomUserService();
    }
    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserService());
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }
//    public ProviderManager(List<AuthenticationProvider> providers, AuthenticationManager parent) {
//
//        this.providers = Collections.emptyList();
//        this.messages = SpringSecurityMessageSource.getAccessor();
//        this.eraseCredentialsAfterAuthentication = true;
//        Assert.notNull(providers, "providers list cannot be null");
//        this.providers = providers;
//        this.parent = parent;
//        this.checkState();
//    }
//    @Bean
//    public ProviderManager providerManager(){
//        ProviderManager providerManager=new ProviderManager(Arrays.asList(authProvider()));
//        providerManager.setEraseCredentialsAfterAuthentication(false);
//        System.out.println(providerManager.getProviders());
//        return providerManager;
//    }
    public AuthenticationManager authenticationManager(){
        ProviderManager providerManager=new ProviderManager(Arrays.asList(authProvider()));
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

    @Bean
    @Order(1)
    SecurityFilterChain configureFilterChainAdmin(HttpSecurity http) throws Exception{
        return http.securityMatcher("/api/bookingDetails/admin/**")
                .authorizeHttpRequests(
                        request ->
                            request.requestMatchers("/api/bookingDetails/**").hasRole("ADMIN")
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
    @Order(2)
    @Bean
    SecurityFilterChain configureFilterChainUser(HttpSecurity http) throws Exception{
        return http.securityMatcher("/api/bookingDetails/**")
                .authorizeHttpRequests(request ->
                        {
                            request.requestMatchers("/api/bookingDetails/**").hasRole("USER");

                        }
                )
                .csrf(csrf->csrf.disable())
//                .cors(Customizer.withDefaults())
//                .cors(cors -> cors.disable())
                .httpBasic(withDefaults())
                .build();
    }

    SecurityFilterChain configureFilterCHainBasicAuth(HttpSecurity http) throws Exception{
        return http.securityMatcher("/**")
                .authorizeHttpRequests(request ->
                        {
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


//    @Bean
//    SecurityFilterChain configureFilterChainUser(HttpSecurity http) throws Exception{
//        return http
//                .authorizeHttpRequests(request ->
//                        {
//                            request.anyRequest().permitAll();
//                        }
//
//                )
//                .csrf(csrf->csrf.disable())
//                .httpBasic(withDefaults())
//
//                .build();
//    }
}
