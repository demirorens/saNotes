package com.sanotes.saNotesPostgres.service.DAO;

import com.sanotes.saNotesPostgres.service.model.user.Role;
import com.sanotes.saNotesPostgres.service.model.user.RoleName;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Optional<Role> findByName(RoleName name);
}
