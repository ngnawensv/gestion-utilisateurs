/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.belrose.gestionutilisateur.services;

import cm.belrose.gestionutilisateur.entities.User;
import cm.belrose.gestionutilisateur.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Ngnawen Samuel
 */
//@Service(value = "userService")
public interface UserService {

    Collection<User> getAllUsers();

    Optional<User> findUserById(Long id) throws ResourceNotFoundException;

    Optional<User> findByLogin(String login) throws ResourceNotFoundException;

    User saveOrUpdateUser(User user) throws ResourceNotFoundException;

    void deleteUser(Long id) throws ResourceNotFoundException;

    Optional<User> findByLoginAndPassword(String login, String password) throws ResourceNotFoundException;
}
