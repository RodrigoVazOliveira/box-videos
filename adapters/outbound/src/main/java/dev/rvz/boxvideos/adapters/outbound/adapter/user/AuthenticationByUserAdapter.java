package dev.rvz.boxvideos.adapters.outbound.adapter.user;

import dev.rvz.boxvideos.adapters.commons.authentication.UserAuthentication;
import dev.rvz.boxvideos.adapters.commons.entity.UserEntity;
import dev.rvz.boxvideos.adapters.outbound.repository.UserRepository;
import dev.rvz.boxvideos.core.domain.user.exception.UserNotFoundException;
import dev.rvz.boxvideos.port.out.user.AuthenticationByUserPortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationByUserAdapter implements AuthenticationByUserPortOut, UserDetailsService {
    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationByUserAdapter.class);

    private final UserRepository userRepository;

    public AuthenticationByUserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            LOGGER.error("loadUserByUsername - username is empty");
            throw new UserNotFoundException("Usuário %s não existe!".formatted(username));
        }

        notExistsUserByEmailOrNick(username);
        UserEntity userEntity = getUserEntity(username);

        return new UserAuthentication(userEntity, username);
    }

    @Override
    public void notExistsUserByEmailOrNick(String username) {
        LOGGER.info("notExistsUserByEmailOrNick - username : {}", username);
        Boolean existsByEmailOrNick = userRepository.existsByEmailOrNick(username, username);
        LOGGER.info("notExistsUserByEmailOrNick - existsByEmailOrNick : {}", existsByEmailOrNick);

        if (!existsByEmailOrNick) {
            LOGGER.error("existsUserByEmailOrNick - user with email or nick not found!");
            throw new UserNotFoundException("Usuário com %s não existe!");
        }
    }

    private UserEntity getUserEntity(String username) {
        LOGGER.info("getUserEntity - username : {}", username);
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmailOrNick(username, username);
        UserEntity userEntity = optionalUserEntity.get();
        LOGGER.info("getUserEntity - userEntity : {}", userEntity);

        return userEntity;
    }
}
