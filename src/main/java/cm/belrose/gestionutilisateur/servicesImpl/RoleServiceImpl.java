/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.belrose.gestionutilisateur.servicesImpl;


import java.util.Collection;
import java.util.stream.Stream;

import cm.belrose.gestionutilisateur.dao.RoleDao;
import cm.belrose.gestionutilisateur.entities.Role1;
import cm.belrose.gestionutilisateur.exception.ResourceNotFoundException;
import cm.belrose.gestionutilisateur.services.RoleService;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ngnawen Samuel
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    public RoleServiceImpl() {
        super();
    }

    @Override
    public Role1 FindByRoleName(String roleName) {
        return roleDao.findByRoleName(roleName);
    }

    @Override
    public Role1 saveOrUpdateRole(Role1 role1) throws ResourceNotFoundException {
        return roleDao.save(role1);
    }

    @Override
    public Collection<Role1> getAllRoles() {//Avant java 8
        return IteratorUtils.toList(roleDao.findAll().iterator());
    }

    @Override
    public Stream<Role1> getAllRolesStream() {//java
        return roleDao.getAllRolesStream();
    }

}
