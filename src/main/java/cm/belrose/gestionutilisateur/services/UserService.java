/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.belrose.gestionutilisateur.services;

import cm.belrose.gestionutilisateur.entities.User;

import java.util.Collection;
import java.util.Optional;

/**
 *
 * @author PC-NGNAWEN
 */
public interface UserService {
    
    Collection<User> getAllUsers();
    
    Optional<User> getUserById(Long id);
    
    User findByLogin(String login);
    
    User saveOrUpdateUser(User user);
    
    void deleteUser(Long id);
}
