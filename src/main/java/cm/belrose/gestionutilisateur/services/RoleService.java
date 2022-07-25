/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.belrose.gestionutilisateur.services;

import cm.belrose.gestionutilisateur.entities.Role1;
import cm.belrose.gestionutilisateur.exception.ResourceNotFoundException;

import java.util.Collection;
import java.util.stream.Stream;

/**
 *
 * @author Ngnawen Samuel
 */
//@Service
public interface RoleService {

    Role1 FindByRoleName(String roleName)throws ResourceNotFoundException;

    Role1 saveOrUpdateRole(Role1 role1) throws ResourceNotFoundException;

    // les methodes getAllRoles() et getAllRolesStream() font la meme chose. juste que la deuxieme est du pure java8
    Collection<Role1> getAllRoles();

    Stream<Role1> getAllRolesStream();
}
