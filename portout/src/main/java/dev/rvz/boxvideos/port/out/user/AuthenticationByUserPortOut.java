package dev.rvz.boxvideos.port.out.user;

public interface AuthenticationByUserPortOut {
    void notExistsUserByEmailOrNick(String username);
}