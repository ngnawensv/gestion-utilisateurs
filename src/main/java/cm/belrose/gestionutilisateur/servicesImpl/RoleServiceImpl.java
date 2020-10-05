/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.belrose.gestionutilisateur.servicesImpl;


import java.util.Collection;
import java.util.stream.Stream;

import cm.belrose.gestionutilisateur.dao.RoleDao;
import cm.belrose.gestionutilisateur.entities.Role;
import cm.belrose.gestionutilisateur.services.RoleService;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author PC-NGNAWEN
 */
@Service(value="roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    public RoleServiceImpl() {
        super();
    }

    @Override
    public Role FindByRoleName(String roleName) {
        return roleDao.findByRoleName(roleName);
    }

    @Override
    public Collection<Role> getAllRoles() {//Avant java 8
        return IteratorUtils.toList(roleDao.findAll().iterator());
    }

    @Override
    public Stream<Role> getAllRolesStream() {//java
        return roleDao.getAllRolesStream();
    }

}
