/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.belrose.gestionutilisateur.services;

import cm.belrose.gestionutilisateur.entities.Role;

import java.util.Collection;
import java.util.stream.Stream;

/**
 *
 * @author PC-NGNAWEN
 */
public interface RoleService {

    Role FindByRoleName(String roleName);

    // les methodes getAllRoles() et getAllRolesStream() font la meme chose. juste que la deuxieme est du pure java8
    Collection<Role> getAllRoles();

    Stream<Role> getAllRolesStream();
}
