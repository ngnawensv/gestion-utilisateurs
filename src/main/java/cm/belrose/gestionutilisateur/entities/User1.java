/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.belrose.gestionutilisateur.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author PC-NGNAWEN
 */
@Entity
@Table(name = "UTILISATEUR")
public class User1 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_LOGIN")
    private String login;

    @Column(name = "USER_PASSWORD")
    private String password;

    @Column(name = "USER_ACTIVE")
    private Integer active;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role1> roles = new HashSet<>();

    public User1() {
        super();
    }


    public User1(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User1(String login, String password, Integer active) {
        this.login = login;
        this.password = password;
        this.active = active;
    }

    public User1(Long id, String login) {
        this.id = id;
        this.login = login;
    }

    public User1(String login) {
        this.login = login;
    }

    /*public User1(UserDto userDTO) {
        this.setId(userDTO.getId());
        this.setLogin(userDTO.getLogin());
        this.setPassword(userDTO.getPassword());
    }*/

    /*public User1(UserRegistrationForm userRegistrationForm) {
        this.setLogin(userRegistrationForm.getLogin());
        this.setPassword(userRegistrationForm.getPassword());
    }*/

    public User1(Long id, String login, String password, Integer active) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Set<Role1> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role1> roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.login);
        hash = 97 * hash + Objects.hashCode(this.password);
        hash = 97 * hash + Objects.hashCode(this.active);
        hash = 97 * hash + Objects.hashCode(this.roles);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User1 other = (User1) obj;
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.active, other.active)) {
            return false;
        }
        if (!Objects.equals(this.roles, other.roles)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User1{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }
}
