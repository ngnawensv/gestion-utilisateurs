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
import cm.belrose.gestionutilisateur.entities.Role;
import cm.belrose.gestionutilisateur.entities.User;
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
    public Collection<User> getAllUsers() {
        return IteratorUtils.toList(userDao.findAll().iterator());
    }

    @Override
    public Optional<User> findUserById(Long id) throws ResourceNotFoundException {
        Optional<User> userFound = userDao.findById(id);
        if (userFound.isEmpty()) {
            throw new ResourceNotFoundException(" Utilisateur introuvable", "L'utilisateur avec l' id " + id+" iniexistant",HttpStatus.NOT_FOUND);
        }
        return userFound;
    }

    @Override
    public Optional<User> findByLogin(String login) throws ResourceNotFoundException {
        Optional<User> userFound = userDao.findByLogin(login);
        if (userFound.isEmpty()) {
            throw new ResourceNotFoundException("User Not Found", "L'utilisateur avec ce login n'existe pas : " + login);
        }
        return userFound;
    }

    @Override
    @Transactional(readOnly = false)
    public User saveOrUpdateUser(User user) throws ResourceNotFoundException {
        try {
            if(null==user.getId()){ //Pas d'id---> création d'un user
                addUserRole(user);//Ajout d'un role par defaut
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }else{ //sinon,mise à jour d'un user
                Optional<User> userFromDB=findUserById(user.getId());
                if(!bCryptPasswordEncoder.matches(user.getPassword(),userFromDB.get().getPassword())){
                    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));//MAJ du mot de passe s'il a été modifié
                }else {
                    user.setPassword(userFromDB.get().getPassword());//Sinon, on remet le password déjà haché
                }
                updateUserRole(user);//On extrait le rôle en cas de mise à jour
            }
            User result = userDao.save(user);
            return  result;
        } catch (DataIntegrityViolationException ex) {
            logger.error("Utilisateur non existant",ex);
            throw new ResourceNotFoundException("DuplicateValueError", "Un utilisateur existe déjà avec le compte : "+user.getLogin(), HttpStatus.CONFLICT);
        }catch (ResourceNotFoundException e) {
            logger.error("Utilisateur non existant", e);
            throw new ResourceNotFoundException("UserNotFound", "Aucun utilisateur avec l'identifiant: "+user.getId(), HttpStatus.NOT_FOUND);
        }catch (Exception ex) {
            logger.error("Erreur technique de création ou de mise à jour de l'utilisateur", ex);
            throw new ResourceNotFoundException("SaveOrUpdateUserError", "Erreur technique de création ou de mise à jour de l'utilisateur: "+user.getLogin(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteUser(Long id) throws ResourceNotFoundException {
        try{
            userDao.deleteById(id);
        }catch(EmptyResultDataAccessException ex){
            logger.error(String.format("Aucun utilisateur n'existe avec l'identifiant: "+id, ex));
            throw new ResourceNotFoundException("DeleteUserError", "Erreur de suppression de l'utilisateur avec l'identifiant: "+id, HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            throw new ResourceNotFoundException("DeleteUserError", "Erreur de suppression de l'utilisateur avec l'identifiant: "+id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) throws ResourceNotFoundException {
        try {
            Optional<User> userFound = this.findByLogin(login);
            if(bCryptPasswordEncoder.matches(password, userFound.get().getPassword())) {
                return userFound;
            } else {
                throw new ResourceNotFoundException("UserNotFound", "Mot de passe incorrect", HttpStatus.NOT_FOUND);
            }
        } catch (ResourceNotFoundException ex) {
            logger.error("Login ou mot de passe incorrect", ex);
            throw new ResourceNotFoundException("UserNotFound", "Login ou mot de passe incorrect", HttpStatus.NOT_FOUND);
        }catch (Exception ex) {
            logger.error("Une erreur technique est survenue", ex);
            throw new ResourceNotFoundException("TechnicalError", "Une erreur technique est survenue", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void addUserRole(User user) {
        Set<Role> roles= new HashSet<>();
        Role roleUser = new Role("ROLE_USER");//initialisation du rôle ROLE_USER
        roles.add(roleUser);
        user.setActive(0);

        Set<Role> roleFromDB = extractRole_Java8(roles, roleDao.getAllRolesStream());
        user.setRoles(roleFromDB);
    }


    private void updateUserRole(User user) {

        Set<Role> roleFromDB = extractRole_Java8(user.getRoles(), roleDao.getAllRolesStream());
        user.setRoles(roleFromDB);
    }

    private Set<Role> extractRole_Java8(Set<Role> rolesSetFromUser, Stream<Role> roleStreamFromDB) {
        // Collect UI role names
        Set<String> uiRoleNames = rolesSetFromUser.stream()
                .map(Role::getRoleName)
                .collect(Collectors.toCollection(HashSet::new));
        // Filter DB roles
        return roleStreamFromDB
                .filter(role -> uiRoleNames.contains(role.getRoleName()))
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("unused")
    private Set<Role> extractRoleUsingCompareTo_Java8(Set<Role> rolesSetFromUser, Stream<Role> roleStreamFromDB) {
        return roleStreamFromDB
                .filter(roleFromDB -> rolesSetFromUser.stream()
                        .anyMatch( roleFromUser -> roleFromUser.compareTo(roleFromDB) == 0))
                .collect(Collectors.toCollection(HashSet::new));
    }

    @SuppressWarnings("unused")
    private Set<Role>  extractRole_BeforeJava8(Set<Role> rolesSetFromUser, Collection<Role> rolesFromDB) {
        Set<Role> rolesToAdd = new HashSet<>();
        for(Role roleFromUser:rolesSetFromUser){
            for(Role roleFromDB:rolesFromDB){
                if(roleFromDB.compareTo(roleFromUser)==0){
                    rolesToAdd.add(roleFromDB);
                    break;
                }
            }
        }
        return rolesToAdd;
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
