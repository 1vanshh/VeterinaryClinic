package repository;

import domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByLogin(String login);
}
