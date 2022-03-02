package com.sanotes.postgres.repository;

import com.sanotes.commons.model.user.Role;
import com.sanotes.commons.model.user.RoleName;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Optional<Role> findByName(RoleName name);
}
