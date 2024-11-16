package org.example.pp_3_1_3_bootstrap.dao;

import org.example.pp_3_1_3_bootstrap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u left join fetch u.roles where u.username =:username")
    User findByUsername(String username);
}
