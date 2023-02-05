package dev.rvz.boxvideos.adapters.outbound.adapter.user;

import dev.rvz.boxvideos.adapters.commons.entity.UserEntity;
import dev.rvz.boxvideos.adapters.outbound.repository.UserRepository;
import dev.rvz.boxvideos.core.domain.user.enumerations.AccessRoleEnum;
import dev.rvz.boxvideos.core.domain.user.exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@PropertySource({
        "classpath:application.properties"
})
@ContextConfiguration(classes = {
        UserRepository.class,
        AuthenticationByUserAdapter.class
})
@EntityScan("dev.rvz.*")
@EnableJpaRepositories("dev.rvz.*")
class AuthenticationByUserAdapterTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationByUserAdapter authenticationByUserAdapter;

    @Test
    void user_not_exists() {
        final UserNotFoundException resultException = Assertions.assertThrows(UserNotFoundException.class, () -> {
            authenticationByUserAdapter.notExistsUserByEmailOrNick("XPTO");
        });
        Assertions.assertEquals("Usuário com XPTO não existe!", resultException.getMessage());
    }

    @Test
    void user_exists() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setActive(true);
        userEntity.setName("Usuário teste");
        userEntity.setEmail("user.name@gmail.com");
        userEntity.setNick("user.name");
        userEntity.setPassword("sdafadsfdsfadsf");
        userEntity.setAccessRoleEnum(AccessRoleEnum.ADMIN);

        userRepository.save(userEntity);

        authenticationByUserAdapter.notExistsUserByEmailOrNick("user.name");
        authenticationByUserAdapter.notExistsUserByEmailOrNick("user.name@gmail.com");
        userRepository.deleteAll();
    }

    @Test
    void load_user_with_error_username_is_null() {
        UserNotFoundException resultException = Assertions.assertThrows(UserNotFoundException.class, () -> {
            authenticationByUserAdapter.loadUserByUsername(null);
        });
        Assertions.assertEquals("Usuário está nulo!", resultException.getMessage());
    }

    @Test
    void load_user_with_success() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setActive(true);
        userEntity.setName("Usuário teste");
        userEntity.setEmail("user.name@gmail.com");
        userEntity.setNick("user.name");
        userEntity.setPassword("sdafadsfdsfadsf");
        userEntity.setAccessRoleEnum(AccessRoleEnum.ADMIN);
        userRepository.save(userEntity);

        UserDetails resultUser = authenticationByUserAdapter.loadUserByUsername(userEntity.getEmail());
        Assertions.assertEquals(userEntity.getEmail(), resultUser.getUsername());
        Assertions.assertEquals(userEntity.getPassword(), resultUser.getPassword());

        userRepository.deleteAll();
    }
}
