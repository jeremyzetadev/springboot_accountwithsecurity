

package com.vikingbank.repositories;


import com.vikingbank.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);


    Optional<User> findOneByEmail(String email);


    Optional<User> findOneById(Long id);

}
