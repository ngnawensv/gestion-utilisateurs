/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.belrose.gestionutilisateur.dao;

import java.util.stream.Stream;

import cm.belrose.gestionutilisateur.entities.Role1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PC-NGNAWEN
 */
@Repository
public interface RoleDao extends JpaRepository<Role1, Long> {

    Role1 findByRoleName(String roleName);

    @Query("select role from Role1 role")
    Stream<Role1> getAllRolesStream(); //On place la liste des roles dans un Stream

}
