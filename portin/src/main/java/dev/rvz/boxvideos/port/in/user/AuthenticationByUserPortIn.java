package dev.rvz.boxvideos.port.in.user;

import dev.rvz.boxvideos.core.domain.users.model.User;

public interface AuthenticationByUserPortIn {
    Boolean run(User user);
}
