/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.belrose.gestionutilisateur.servicesImpl;


import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cm.belrose.gestionutilisateur.dao.RoleDao;
import cm.belrose.gestionutilisateur.dao.UserDao;
import cm.belrose.gestionutilisateur.entities.Role1;
import cm.belrose.gestionutilisateur.entities.User1;
import cm.belrose.gestionutilisateur.exception.ResourceNotFoundException;
import cm.belrose.gestionutilisateur.services.UserService;
import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ngnawen Samuel
 */
//@Service(value = "userService")//l'annotation @Service est optionnelle ici, car il n'existe qu'une seule implementation
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleDao roleDao;

    @Override
    public Collection<User1> getAllUsers() throws ResourceNotFoundException {
        Collection<User1> users = userDao.findAll();
        if (users.isEmpty()) {
            logger.warn("Not users found ");
            throw new ResourceNotFoundException("Not users found ", HttpStatus.NOT_FOUND);
        }
        logger.info("User1 List :");
        users.forEach(user -> {
            logger.info(user.toString());
        });
        return IteratorUtils.toList(users.iterator());
    }

    @Override
    public Optional<User1> findUserById(Long id) throws ResourceNotFoundException {
        Optional<User1> userFound = userDao.findById(id);
        if (userFound.isEmpty()) {
            logger.warn("User1 not  found ");
            throw new ResourceNotFoundException("User1 not found", HttpStatus.NOT_FOUND);
        }
        logger.info("User1:  " + userFound);
        return userFound;
    }

    @Override
    public Optional<User1> findByLogin(String login) throws ResourceNotFoundException {
        Optional<User1> userFound = userDao.findByLogin(login);
        if (userFound.isEmpty()) {
            throw new ResourceNotFoundException("User1 Not Found", "L'utilisateur avec ce login n'existe pas : " + login);
        }
        return userFound;
    }

    @Override
    @Transactional(readOnly = false)
    public User1 saveOrUpdateUser(User1 user1) throws ResourceNotFoundException {
        try {
            if (null == user1.getId()) { //Pas d'id---> création d'un user1
                addUserRole(user1);//Ajout d'un role par defaut
                user1.setPassword(bCryptPasswordEncoder.encode(user1.getPassword()));
            } else { //sinon,mise à jour d'un user1
                Optional<User1> userFromDB = findUserById(user1.getId());
                if (!bCryptPasswordEncoder.matches(user1.getPassword(), userFromDB.get().getPassword())) {
                    user1.setPassword(bCryptPasswordEncoder.encode(user1.getPassword()));//MAJ du mot de passe s'il a été modifié
                } else {
                    user1.setPassword(userFromDB.get().getPassword());//Sinon, on remet le password déjà haché
                }
                updateUserRole(user1);//On extrait le rôle en cas de mise à jour
            }
            User1 result = userDao.save(user1);
            return result;
        } catch (DataIntegrityViolationException ex) {
            logger.error("Utilisateur non existant", ex);
            throw new ResourceNotFoundException("DuplicateValueError", "Un utilisateur existe déjà avec le compte : " + user1.getLogin(), HttpStatus.CONFLICT);
        } catch (ResourceNotFoundException e) {
            logger.error("Utilisateur non existant", e);
            throw new ResourceNotFoundException("UserNotFound", "Aucun utilisateur avec l'identifiant: " + user1.getId(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error("Erreur technique de création ou de mise à jour de l'utilisateur", ex);
            throw new ResourceNotFoundException("SaveOrUpdateUserError", "Erreur technique de création ou de mise à jour de l'utilisateur: " + user1.getLogin(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteUser(Long id) throws ResourceNotFoundException {
        try {
            userDao.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            logger.error(String.format("Aucun utilisateur n'existe avec l'identifiant: " + id, ex));
            throw new ResourceNotFoundException("DeleteUserError", "Erreur de suppression de l'utilisateur avec l'identifiant: " + id, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("DeleteUserError", "Erreur de suppression de l'utilisateur avec l'identifiant: " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void addRoleToUser(String login, String roleName) {
        Role1 role=roleDao.findByRoleName(roleName);
        User1 user1=userDao.findByLogin(login).get();
        user1.getRoles().add(role);

    }

    @Override
    public Optional<User1> findByLoginAndPassword(String login, String password) throws ResourceNotFoundException {
        try {
            Optional<User1> userFound = this.findByLogin(login);
            if (bCryptPasswordEncoder.matches(password, userFound.get().getPassword())) {
                return userFound;
            } else {
                throw new ResourceNotFoundException("UserNotFound", "Mot de passe incorrect", HttpStatus.NOT_FOUND);
            }
        } catch (ResourceNotFoundException ex) {
            logger.error("Login ou mot de passe incorrect", ex);
            throw new ResourceNotFoundException("UserNotFound", "Login ou mot de passe incorrect", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error("Une erreur technique est survenue", ex);
            throw new ResourceNotFoundException("TechnicalError", "Une erreur technique est survenue", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void addUserRole(User1 user1) {
        Set<Role1> roles = new HashSet<>();
        Role1 role1User = new Role1("ROLE_USER");//initialisation du rôle ROLE_USER
        roles.add(role1User);
        user1.setActive(0);
        Set<Role1> role1FromDB = extractRole_Java8(roles, roleDao.getAllRolesStream());
        user1.setRoles(role1FromDB);
    }


    private void updateUserRole(User1 user1) {

        Set<Role1> role1FromDB = extractRole_Java8(user1.getRoles(), roleDao.getAllRolesStream());
        user1.setRoles(role1FromDB);
    }

    private Set<Role1> extractRole_Java8(Set<Role1> role1SetFromUser, Stream<Role1> roleStreamFromDB) {
        // Collect UI role names
        Set<String> uiRoleNames = role1SetFromUser.stream()
                .map(Role1::getRoleName)
                .collect(Collectors.toCollection(HashSet::new));
        // Filter DB roles
        return roleStreamFromDB
                .filter(role -> uiRoleNames.contains(role.getRoleName()))
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("unused")
    private Set<Role1> extractRoleUsingCompareTo_Java8(Set<Role1> role1SetFromUser, Stream<Role1> roleStreamFromDB) {
        return roleStreamFromDB
                .filter(roleFromDB -> role1SetFromUser.stream()
                        .anyMatch(roleFromUser -> roleFromUser.compareTo(roleFromDB) == 0))
                .collect(Collectors.toCollection(HashSet::new));
    }

    @SuppressWarnings("unused")
    private Set<Role1> extractRole_BeforeJava8(Set<Role1> role1SetFromUser, Collection<Role1> role1FromDB) {
        Set<Role1> role1ToAdd = new HashSet<>();
        for (Role1 role1FromUser : role1SetFromUser) {
            for (Role1 roleFromDB : role1FromDB) {
                if (roleFromDB.compareTo(role1FromUser) == 0) {
                    role1ToAdd.add(roleFromDB);
                    break;
                }
            }
        }
        return role1ToAdd;
    }

    public UserServiceImpl() {
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public BCryptPasswordEncoder getbCryptPasswordEncoder() {
        return bCryptPasswordEncoder;
    }

    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
}
