package es.jander.security.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private AuthUserDetailsService authUserDetailsService;
    @Autowired
    private UserRepository userRepository;

    private String realm = "jander-security";

    @PostConstruct
    private void init ()
    {
        UserRecord userRecord = new UserRecord(1L, "demo", "1234");
        userRepository.save(userRecord);
    }

    // configures an entry point for authentication: the default is to redirect to a login page
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        log.info("Configuring security");
        http
                .authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/private/**").authenticated()
                .and().httpBasic().realmName(realm).authenticationEntryPoint(basicAuthenticationEntryPoint());

        // sets sessions to be stateless
        log.info("Sessions set to be stateless");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // disables CSRF protection
        log.info("CSRF forgery protection is disabled");
        http.csrf().disable();
    }

    @Bean
    public BasicAuthenticationEntryPoint basicAuthenticationEntryPoint ()
    {
        return new BasicAuthenticationEntryPoint(){
            @Override
            public void commence(final HttpServletRequest request,
                                 final HttpServletResponse response,
                                 final AuthenticationException authException) throws IOException, ServletException
            {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication error on " + getRealmName());
            }

            @Override
            public void afterPropertiesSet() throws Exception
            {
                setRealmName(realm);
                super.afterPropertiesSet();
            }
        };
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth)
    {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(authUserDetailsService);
//        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder(11);
    }
}
