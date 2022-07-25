package cm.belrose.gestionutilisateur.controllers;

import cm.belrose.gestionutilisateur.entities.User1;
import cm.belrose.gestionutilisateur.exception.ResourceNotFoundException;
import cm.belrose.gestionutilisateur.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UserController {

    private Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Service REST d'extraction de tous les utilisateurs
     */
    @GetMapping(value = "/users")
    public ResponseEntity<Collection<User1>> getAllUsers() throws ResourceNotFoundException{
        Collection<User1> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.FOUND);
    }

    /**
     * Service REST de creation d'un utilisateur
     */
    @PostMapping(value = "/users")
    @Transactional
    public ResponseEntity<User1> saveUser(@RequestBody User1 user1) throws ResourceNotFoundException {
        User1 user1Saved = userService.saveOrUpdateUser(user1);
        return new ResponseEntity<User1>(user1Saved, HttpStatus.CREATED);
    }

    /**
     * Service REST de modification d'un utilisateur
     */
    @PutMapping(value = "/users")
    public ResponseEntity<User1> updateUser(@RequestBody User1 user1) throws ResourceNotFoundException {
        User1 user1Updated = userService.saveOrUpdateUser(user1);
        return new ResponseEntity<>(user1Updated, HttpStatus.OK);
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
    public ResponseEntity<User1> findUserByLoginAndPassword(@RequestBody User1 user1) throws ResourceNotFoundException {
        Optional<User1> userFound = userService.findByLoginAndPassword(user1.getLogin(), user1.getPassword());
        return new ResponseEntity<User1>(userFound.get(), HttpStatus.FOUND);
    }

    @GetMapping(value = "/users/id/{id}")
    public ResponseEntity<User1> findUserById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<User1> userFound = userService.findUserById(id);
        return new ResponseEntity<>(userFound.get(), HttpStatus.FOUND);
    }

    /**
     * Service REST de recherche d'un utilisateur par son login
     */
    @GetMapping(value = "/users/login/{loginName}")
    public ResponseEntity<User1> findUserByLogin(@PathVariable("loginName") String loginName) throws ResourceNotFoundException {
        Optional<User1> userFound = userService.findByLogin(loginName);
        return new ResponseEntity<>(userFound.get(), HttpStatus.FOUND);
    }





}
