package com.emprovise.service.authorizationserver.repository;


import com.emprovise.service.authorizationserver.domain.UserAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

    UserAccount findByUsername(String username);

    @Query("SELECT COUNT(u) FROM UserAccount u WHERE u.username=:username")
    Long countByUsername(@Param("username") String username);
}
