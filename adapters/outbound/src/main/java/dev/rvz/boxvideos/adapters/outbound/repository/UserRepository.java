package dev.rvz.boxvideos.adapters.outbound.repository;

import dev.rvz.boxvideos.adapters.commons.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);

    Boolean existsByNick(String nick);

    Optional<UserEntity> findByEmailOrNick(String email, String nick);
}
