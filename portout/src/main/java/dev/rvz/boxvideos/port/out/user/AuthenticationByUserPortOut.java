package dev.rvz.boxvideos.port.out.user;

public interface AuthenticationByUserPortOut {
    Boolean existsUserByEmailOrNick(String email, String nick);
}