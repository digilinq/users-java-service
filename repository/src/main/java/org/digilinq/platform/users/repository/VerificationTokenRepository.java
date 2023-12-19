package org.digilinq.platform.users.repository;

import org.digilinq.platform.users.to.VerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity, Long>, JpaSpecificationExecutor<VerificationTokenEntity> {
}
