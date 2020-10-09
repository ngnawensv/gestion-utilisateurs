package cm.belrose.gestionutilisateur.dto;

import java.io.Serializable;
import javassist.SerialVersionUID;

/**
 *
 * @author PC-NGNAWEN
 */
public class UserDto  {

    private String login;
    private String password;

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
