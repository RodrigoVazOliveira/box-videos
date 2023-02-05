package dev.rvz.adapters.inbound.api.commons;

import dev.rvz.boxvideos.adapters.commons.authentication.UserAuthentication;
import dev.rvz.boxvideos.adapters.commons.entity.UserEntity;
import dev.rvz.boxvideos.adapters.configurations.securities.SecurityAccessConfiguration;
import dev.rvz.boxvideos.adapters.configurations.securities.tokens.GetAuthoryToToken;
import dev.rvz.boxvideos.adapters.configurations.securities.tokens.GetJWTGenerateToken;
import dev.rvz.boxvideos.adapters.configurations.securities.tokens.TokenIsValid;
import dev.rvz.boxvideos.adapters.outbound.adapter.user.AuthenticationByUserAdapter;
import dev.rvz.boxvideos.adapters.outbound.repository.UserRepository;
import dev.rvz.boxvideos.core.domain.user.enumerations.AccessRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(classes = {
        SecurityAccessConfiguration.class,
        AuthenticationByUserAdapter.class,
        UserRepository.class,
        GetJWTGenerateToken.class,
        GetAuthoryToToken.class,
        TokenIsValid.class
})
public class MockSpringSecurity {
    @MockBean
    AuthenticationByUserAdapter authenticationByUserAdapter;

    @MockBean
    UserRepository userRepository;

    @MockBean
    GetJWTGenerateToken getJWTGenerateToken;

    @MockBean
    GetAuthoryToToken getAuthoryToToken;

    @MockBean
    TokenIsValid tokenIsValid;

    @BeforeEach
    public void setup() {
        execute();
    }

    public void execute() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("Rodrigo Vaz");
        userEntity.setNick("rodrigo.vaz");
        userEntity.setEmail("rodrigo.vaz@gmail.com");
        userEntity.setPassword("4f85d3b0191febe20e5507106397b2ce23b2ccfec658128eb65fb287e7741a9fb96a77a4d281dd298e77c77ec3bef888");
        userEntity.setAccessRoleEnum(AccessRoleEnum.ADMIN);
        userEntity.setActive(true);

        final List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));

        Mockito.when(authenticationByUserAdapter.loadUserByUsername(Mockito.any())).thenReturn(new UserAuthentication(userEntity, userEntity.getEmail()));
        Mockito.when(tokenIsValid.execute(Mockito.any())).thenReturn(true);
        Mockito.when(getAuthoryToToken.execute(Mockito.any())).thenReturn(new UsernamePasswordAuthenticationToken(userEntity.getEmail(), null, simpleGrantedAuthorities));
    }
}
