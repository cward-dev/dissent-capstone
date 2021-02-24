package capstone.dissent.security;

import org.apache.catalina.authenticator.SpnegoAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 2

    private final JwtConverter jwtConverter;

    public SecurityConfig(JwtConverter jwtConverter) {
        this.jwtConverter = jwtConverter;
    }


    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/api/user/create-account").permitAll() // allow anyone to create an account.
                .antMatchers("/authenticate").permitAll()   // allow anyone to attempt authentication

                // below commented out for development.

//                .antMatchers(HttpMethod.GET,
//                        "/api/article/*",
//                        "/api/post/*",
//                        "/api/topic/*",
//                        "/api/source/*")
//                .hasAnyRole("USER", "ADMIN")
//
//                .antMatchers(HttpMethod.POST,
//                        "/api/post",
//                        "/api/post/",
//                        "/api/post/*")
//                .hasAnyRole("USER", "ADMIN")

//                .antMatchers("/**").denyAll() // forces explicit security declaration of all http endpoints (no leaks!)

                .antMatchers("/**").permitAll() // no security for development.

                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(), jwtConverter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override //overridden to ensure availability through dependency injection
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

//    @Autowired
//    private PasswordEncoder encoder;
//
//    // temporary authentication with in-memory users (not from User database)
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        var userBuilder = User.withUsername("user")
//                .password("user").passwordEncoder(password -> encoder.encode(password))
//                .roles("USER");
//
//        var adminBuilder = User.withUsername("admin")
//                .password("admin").passwordEncoder(password -> encoder.encode(password))
//                .roles("ADMIN");
//
//        auth.inMemoryAuthentication()
//                .withUser(userBuilder)
//                .withUser(adminBuilder);
//    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedOrigins("http://localhost")
                        .allowedOrigins("http://frontend")
                        .allowedOrigins("https://dev10-capstone-team1.azurewebsites.net")
                        .allowedMethods("*");
            }
        };
    }
}
