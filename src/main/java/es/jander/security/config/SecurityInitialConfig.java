package es.jander.security.config;

import es.jander.security.auth.UserRecord;
import es.jander.security.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class SecurityInitialConfig
{
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void init ()
    {
        UserRecord userRecord = new UserRecord(1L, "demo", "1234");
        userRepository.save(userRecord);
    }
}
