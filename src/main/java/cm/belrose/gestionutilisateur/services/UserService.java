/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.belrose.gestionutilisateur.services;

import cm.belrose.gestionutilisateur.entities.User1;
import cm.belrose.gestionutilisateur.exception.ResourceNotFoundException;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Ngnawen Samuel
 */
//@Service(value = "userService")
public interface UserService {

    Collection<User1> getAllUsers()throws ResourceNotFoundException;

    User1 saveOrUpdateUser(User1 user1) throws ResourceNotFoundException;

    void deleteUser(Long id) throws ResourceNotFoundException;

    public void addRoleToUser(String login, String roleName);

    Optional<User1> findUserById(Long id) throws ResourceNotFoundException;

    Optional<User1> findByLogin(String login) throws ResourceNotFoundException;

    Optional<User1> findByLoginAndPassword(String login, String password) throws ResourceNotFoundException;
}
