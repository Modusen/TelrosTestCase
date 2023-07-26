package com.vaadin.TelrosTestCase.repository;

import com.vaadin.TelrosTestCase.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM User u" +
            " WHERE CONCAT(u.lastName, ' ', u.firstName, ' ', u.patronymic) " +
            " LIKE CONCAT('%', :name, '%')")
    List<User> findByName(@Param("name") String name);

    Optional<User> findByLogin(String login);
}
