package dev.rvz.boxvideos.adapters.commons.entity;

import dev.rvz.boxvideos.core.domain.user.enumerations.AccessRoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String nick;

    @Column(length = 255, nullable = false)
    private String email;


    @Column(length = 255, nullable = false)
    private String password;


    @Column(length = 20, nullable = false, name = "access_role")
    @Enumerated(EnumType.STRING)
    private AccessRoleEnum accessRoleEnum;

    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccessRoleEnum getAccessRoleEnum() {
        return accessRoleEnum;
    }

    public void setAccessRoleEnum(AccessRoleEnum accessRoleEnum) {
        this.accessRoleEnum = accessRoleEnum;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nick='" + nick + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", accessRoleEnum=" + accessRoleEnum +
                ", active=" + active +
                '}';
    }
}
