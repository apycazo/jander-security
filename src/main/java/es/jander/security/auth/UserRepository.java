package es.jander.security.auth;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserRecord, Long>
{
    UserRecord findByUsername(String username);
}
