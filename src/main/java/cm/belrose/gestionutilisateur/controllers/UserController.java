package cm.belrose.gestionutilisateur.controllers;

import cm.belrose.gestionutilisateur.entities.Role;
import cm.belrose.gestionutilisateur.entities.User;
import cm.belrose.gestionutilisateur.services.RoleService;
import cm.belrose.gestionutilisateur.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * L'annotation @CrossOrigin(origins="http://localhost:8080",maxAge= 3600)
 * permet de favoriser une communication distanteentre le client et le serveur, c'est-à-dire lorsque le client et
 * le serveur sont déployés dans deux serveurs distincts, ce qui permet d'éviter des problèmes réseau.
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RequestMapping("/user/*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     * Service REST d'extraction de tous les utilisateurs
     */
    @GetMapping(value = "/users")
    public ResponseEntity<Collection<User>> getAllUsers() {
        Collection<User> users = userService.getAllUsers();
        logger.info("liste des utilisateurs : " + users.toString());
        return new ResponseEntity<Collection<User>>(users, HttpStatus.FOUND);
    }


    /**
     * Service REST de creation d'un utilisateur
     */
    @PostMapping(value = "/users")
    @Transactional
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        Set<Role> roles = new HashSet<>();
        Role roleUser = new Role("ROLE_USER"); //initialisation du role ROLE_USER
        roles.add(roleUser);
        user.setRoles(roles);
        user.setActive(user.getActive());

        Set<Role> roleFormDB = extractRole_java8(user.getRoles(), roleService.getAllRolesStream());
        user.getRoles().removeAll(user.getRoles());
        user.setRoles(roleFormDB);
        User userSave = userService.saveOrUpdateUser(user);
        logger.info("userSave : " + userSave.toString());
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    private Set<Role> extractRole_java8(Set<Role> rolesSetFromUser, Stream<Role> roleStreamFromDB) {
        //Collect UI role names
        Set<String> uiRoleName = rolesSetFromUser.stream()
                .map(Role::getRoleName)
                .collect(Collectors.toCollection(HashSet::new));

        //Filter DB roles
        return roleStreamFromDB.filter(role -> uiRoleName.contains(role.getRoleName())).collect(Collectors.toSet());
    }

    private Set<Role> extractRoleUsingCompareTo_java8(Set<Role> rolesSetFromUser, Stream<Role> roleStreamFromDB) {
        return roleStreamFromDB.filter(roleFromeDB -> rolesSetFromUser.stream()
                .anyMatch(roleFromUser -> roleFromUser.compareTo(roleFromeDB) == 0))
                .collect(Collectors.toCollection(HashSet::new));
    }

    private Set<Role> extractRole_BeforeJava8(Set<Role> rolesSetFromUser, Collection<Role> rolesFromDB) {
        Set<Role> rolesToAdd = new HashSet<>();
        for (Role roleFromUser : rolesSetFromUser) {
            for (Role roleFromDB : rolesFromDB) {
                if (roleFromDB.compareTo(roleFromUser) == 0) {
                    rolesToAdd.add(roleFromDB);
                    break;
                }
            }
        }
        return rolesToAdd;
    }


    /**
     * Service REST de recherche d'un utilisateur par son login
     */
    @GetMapping(value = "/users/{loginName}")
    public ResponseEntity<User> findUserByLogin(@PathVariable("loginName") String login) {
        User user = userService.findByLogin(login);
        logger.debug("Utilisateur trouvé : " + user);
        return new ResponseEntity<User>(user, HttpStatus.FOUND);
    }

    /**
     * Service REST de modification d'un utilisateur
     */
    @PutMapping(value = "/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @RequestBody User user) {
        User userToUpdate = userService.getUserById(id).get();
        if (userToUpdate == null) {
            logger.debug("L'utilisateur avec l'identifiant " + id + " n'existe pas");
            return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
        }
        logger.info("UPDATE ROLE: " + userToUpdate.getRoles().toString());
        userToUpdate.setLogin(user.getLogin());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setActive(user.getActive());
        User userUpdated = userService.saveOrUpdateUser(userToUpdate);
        return new ResponseEntity<User>(userUpdated, HttpStatus.OK);
    }

    /**
     * Service REST de suppression d'un utilisateur
     */
    @DeleteMapping(value = "/users/{id}")
 public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long id) {
         userService.deleteUser(id);
         return new ResponseEntity<Void>(HttpStatus.GONE);
}

}
