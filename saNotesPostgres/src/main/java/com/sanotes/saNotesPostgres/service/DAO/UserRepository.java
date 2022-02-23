package com.sanotes.saNotesPostgres.service.DAO;

import com.sanotes.saNotesCommons.model.user.User;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(@NotBlank String email);

    Optional<User> findByUsername(@NotBlank String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    boolean existsByEmail(@NotBlank String email);

    boolean existsByUsername(@NotBlank String username);

}
