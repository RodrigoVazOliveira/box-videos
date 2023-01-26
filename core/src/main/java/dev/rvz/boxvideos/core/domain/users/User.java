package dev.rvz.boxvideos.core.domain.users;

import dev.rvz.boxvideos.core.domain.users.enumerations.AccessRoleEnum;

public record User(Long id, String name, String email, String password, AccessRoleEnum accessRole, Boolean active) {
}
