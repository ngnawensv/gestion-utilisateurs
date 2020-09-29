package cm.belrose.gestionutilisateur.controllers;

import cm.belrose.gestionutilisateur.entities.User;
import cm.belrose.gestionutilisateur.exception.ResourceNotFoundException;
import cm.belrose.gestionutilisateur.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

/**
 * L'annotation @CrossOrigin(origins="http://localhost:8080",maxAge= 3600)
 * permet de favoriser une communication distanteentre le client et le serveur, c'est-à-dire lorsque le client et
 * le serveur sont déployés dans deux serveurs distincts, ce qui permet d'éviter des problèmes réseau.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user/*")
public class UserController { //pas de logique métier dans le contrôleur, mais, uniquement l'appel des services

    @Autowired
    private UserService userService;

    /**
     * Service REST d'extraction de tous les utilisateurs
     */
    @GetMapping(value = "/users")
    public ResponseEntity<Collection<User>> getAllUsers() {
        Collection<User> users = userService.getAllUsers();
        return new ResponseEntity<Collection<User>>(users, HttpStatus.FOUND);
    }

    /**
     * Service REST de creation d'un utilisateur
     */
    @PostMapping(value = "/users")
    @Transactional
    public ResponseEntity<User> saveUser(@RequestBody User user) throws ResourceNotFoundException {
        User userSaved = userService.saveOrUpdateUser(user);
        return new ResponseEntity<User>(userSaved, HttpStatus.CREATED);
    }

    /**
     * Service REST de modification d'un utilisateur
     */
    @PutMapping(value = "/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) throws ResourceNotFoundException {
        User userUpdated = userService.saveOrUpdateUser(user);
        return new ResponseEntity<User>(userUpdated, HttpStatus.OK);
    }

    /**
     * Service REST de suppression d'un utilisateur
     */
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id", required = true) Long id) throws ResourceNotFoundException {
        userService.deleteUser(id);
        return new ResponseEntity<Void>(HttpStatus.GONE);
    }

    @GetMapping(value = "/users/login")
    public ResponseEntity<User> findUserByLoginAndPassword(@RequestBody User user) throws ResourceNotFoundException {
        Optional<User> userFound = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        return new ResponseEntity<User>(userFound.get(), HttpStatus.FOUND);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<User> userFound = userService.findUserById(id);
        return new ResponseEntity<>(userFound.get(), HttpStatus.FOUND);
    }

    /**
     * Service REST de recherche d'un utilisateur par son login
     */
    //@GetMapping(value = "/users/{loginName}")
    public ResponseEntity<User> findUserByLogin(@PathVariable("loginName") String login) throws ResourceNotFoundException {
        Optional<User> userFound = userService.findByLogin(login);
        return new ResponseEntity<User>(userFound.get(), HttpStatus.FOUND);
    }





}
