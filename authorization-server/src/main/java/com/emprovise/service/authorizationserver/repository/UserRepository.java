package com.emprovise.service.authorizationserver.repository;


import com.emprovise.service.authorizationserver.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    @Query("SELECT COUNT(u) FROM User u WHERE u.username=:username")
    Long countByUsername(@Param("username") String username);
}
