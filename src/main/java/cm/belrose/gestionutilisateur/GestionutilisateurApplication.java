package cm.belrose.gestionutilisateur;

import cm.belrose.gestionutilisateur.InitDataBase.InitUserService;
import cm.belrose.gestionutilisateur.entities.Role1;
import cm.belrose.gestionutilisateur.entities.User1;
import cm.belrose.gestionutilisateur.services.RoleService;
import cm.belrose.gestionutilisateur.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @author Ngnawen Samuel
 */
//@SpringBootApplication
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class GestionutilisateurApplication implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    public static void main(String[] args) {
        SpringApplication.run(GestionutilisateurApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        userService.saveOrUpdateUser(new User1("admin", "123"));
        userService.saveOrUpdateUser(new User1("user", "123"));

        roleService.saveOrUpdateRole(new Role1("ADMIN"));
        roleService.saveOrUpdateRole(new Role1("USER"));

        userService.addRoleToUser("admin", "ADMIN");
        userService.addRoleToUser("user", "USER");

    }
}
