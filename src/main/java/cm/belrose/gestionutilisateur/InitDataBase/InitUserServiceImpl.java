package cm.belrose.gestionutilisateur.InitDataBase;

import cm.belrose.gestionutilisateur.dao.RoleDao;
import cm.belrose.gestionutilisateur.dao.UserDao;
import cm.belrose.gestionutilisateur.entities.Role;
import cm.belrose.gestionutilisateur.entities.User;
import cm.belrose.gestionutilisateur.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
@Service
@Transactional
public class InitUserServiceImpl implements InitUserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public void initRole() {

        Role role = new Role();
        role.setRoleName("ROLE_ADMIN");
        roleDao.save(role);

        Role role1 = new Role();
        role1.setRoleName("ROLE_USER");
        roleDao.save(role1);
    }


    @Override
    public void initUser() {
        Set<Role> roles= new HashSet<>();
        User user1 = new User();
        Role roleAdmin = new Role("ROLE_ADMIN");//initialisation du rôle ROLE_ADMIN
        Role roleUser = new Role("ROLE_USER");//initialisation du rôle ROLE_USER
        roles.add(roleAdmin);
        roles.add(roleUser);
        user1.setRoles(roles);
        user1.setPassword("admin1");
        user1.setLogin("admin");
        user1.setActive(1);

        User user2 = new User();
        Role roleUser1 = new Role("ROLE_USER");//initialisation du rôle ROLE_USER
        roles.add(roleUser1);
        user2.setPassword("user");
        user2.setLogin("user");
        user2.setActive(1);

        User user3 = new User();
        Role roleUser3 = new Role("ROLE_USER");//initialisation du rôle ROLE_USER
        roles.add(roleUser3);
        user3.setPassword("user1");
        user3.setLogin("user");
        user3.setActive(0);
        userDao.save(user1);
        userDao.save(user2);
        userDao.save(user3);

    }
}
