package capstone.dissent.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityConfig extends WebSecurityConfigurerAdapter { // 2

    @Autowired
    private PasswordEncoder encoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        var userBuilder = User.withUsername("user")
                .password("user").passwordEncoder(password -> encoder.encode(password))
                .roles("USER");

        var adminBuilder = User.withUsername("admin")
                .password("admin").passwordEncoder(password -> encoder.encode(password))
                .roles("ADMIN");

        auth.inMemoryAuthentication()
                .withUser(userBuilder)
                .withUser(adminBuilder);
    }

    @Bean // 3.1
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login", "logout").permitAll()           // 1. allow everyone
                .antMatchers("/add", "/edit/*").hasAnyRole("USER", "ADMIN") // 2. only USER or ADMIN
                .antMatchers("/delete/*").hasRole("ADMIN")                  // 3. only ADMIN
                .and()
                .formLogin()                                                // 4. form login
                .loginPage("/login")
                .failureUrl("/login-err");
    }
}
