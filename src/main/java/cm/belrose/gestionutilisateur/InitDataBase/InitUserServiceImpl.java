package cm.belrose.gestionutilisateur.InitDataBase;

import cm.belrose.gestionutilisateur.dao.RoleDao;
import cm.belrose.gestionutilisateur.dao.UserDao;
import cm.belrose.gestionutilisateur.entities.Role1;
import cm.belrose.gestionutilisateur.entities.User1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void initRole() {

        Role1 role1 = new Role1();
        role1.setRoleName("ROLE_ADMIN");
        roleDao.save(role1);

        Role1 role11 = new Role1();
        role11.setRoleName("ROLE_USER");
        roleDao.save(role11);
    }


    @Override
    public void initUser() {
        Set<Role1> roles= new HashSet<>();
        User1 user11 = new User1();
        Role1 role1Admin = new Role1("ROLE_ADMIN");//initialisation du rôle ROLE_ADMIN
        Role1 role1User = new Role1("ROLE_USER");//initialisation du rôle ROLE_USER
        roles.add(role1Admin);
        roles.add(role1User);
        user11.setRoles(roles);
        user11.setPassword(bCryptPasswordEncoder.encode("admin"));
        user11.setLogin("admin");
        user11.setActive(1);

        User1 user12 = new User1();
        Role1 role1User1 = new Role1("ROLE_USER");//initialisation du rôle ROLE_USER
        roles.add(role1User1);
        user12.setPassword(bCryptPasswordEncoder.encode("user"));
        user12.setLogin("user");
        user12.setActive(1);

        User1 user13 = new User1();
        Role1 role1User3 = new Role1("ROLE_USER");//initialisation du rôle ROLE_USER
        roles.add(role1User3);
        user13.setPassword(bCryptPasswordEncoder.encode("user11"));
        user13.setLogin("user");
        user13.setActive(0);
        userDao.save(user11);
        userDao.save(user12);
        userDao.save(user13);

    }
}
