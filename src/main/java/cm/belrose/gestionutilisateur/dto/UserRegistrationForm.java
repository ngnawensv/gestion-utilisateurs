package cm.belrose.gestionutilisateur.dto;

import java.io.Serializable;

public class UserRegistrationForm  implements Serializable {
    private String login;
    private String password;


    public UserRegistrationForm() {
    }

    public UserRegistrationForm(String login) {
        this.login = login;
    }

    public UserRegistrationForm(String login, String password) {
        this.login = login;
        this.password = password;
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
}
