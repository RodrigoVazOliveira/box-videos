package dev.rvz.boxvideos.port.out.user;

import dev.rvz.boxvideos.core.domain.users.model.User;

public interface AuthenticationByUserPortOut {
    Boolean existsUserByEmailOrNick(String email, String nick);

    String getPasswordDescrypted(String password);

    User getUserByEmailOrNick(String email, String nick);
}