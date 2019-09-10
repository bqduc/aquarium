package net.sunrise.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.sunrise.domain.User;

import java.util.Optional;

/**
 * Created on February, 2018
 *
 * @author bqduc
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByUsername(String username);

}
