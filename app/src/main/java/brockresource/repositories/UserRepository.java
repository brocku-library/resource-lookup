package brockresource.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import brockresource.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    User findByUsername(String username);    
}
