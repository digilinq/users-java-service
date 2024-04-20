package org.digilinq.platform.users.repository;

import org.digilinq.platform.users.to.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {
    boolean existsByUsername(String username);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
}
