package cm.belrose.gestionutilisateur.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import cm.belrose.gestionutilisateur.dao.RoleDao;
import cm.belrose.gestionutilisateur.dao.UserDao;
import cm.belrose.gestionutilisateur.entities.Role;
import cm.belrose.gestionutilisateur.entities.User;
import cm.belrose.gestionutilisateur.servicesImpl.UserServiceImpl;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);

    /**
     * pour exécuter le service, nous avons besoin d'un objet instance de service.
     * On peut obtenir cette instance à travers une déclaration de classe statique interne en utilisant l'annotation
     * @TestConfiguration et en annotant cette instance par @Bean.
     *
     * @TestConfiguration permet de n'exposer le bean de service que lors de la phase des tests afin d'eviter les conflits
     * Cette annotation joue le rôle de l'annotation @Autowired.
     *
     * Mockito a été utilisé pour moquer toute la couche DAO, ce qui a permis d'initialiser les tests.
     */
    @TestConfiguration //création des beans nécessaires pour les tests
    static class UserServiceImplContextConfiguration {
        @Bean//bean de service
        public UserService userService() {
            return new UserServiceImpl();
        }

        @Bean // nécessaire pour hacher/crypter le mot de passe sinon échec des tests
        public BCryptPasswordEncoder passwordEncoder() {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            return bCryptPasswordEncoder;
        }
    }

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * @MockBean is annotation of spring that permit us to create an Mock Objet DAO(userDao and roleDao are two Mock in this case)
     */
    @MockBean
    private UserDao userDao;

    @MockBean//création d'un mockBean pour RoleDao
    private RoleDao roleDao;

    @Before
    public void setUp() {

    }

    //User user = new User("Dupont", "password", 1);

    @Test
    public void testFindAllUsers() throws Exception {
        logger.info("testFindAllUsers");
        User user = new User("Dupont", "password", 1);
        User user1 = new User("Samuel", "password", 1);
        Role role = new Role("USER_ROLE");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        List<User> allUsers = Arrays.asList(user, user1);
        logger.info(String.valueOf(allUsers));
        Mockito.when(userDao.findAll()).thenReturn(allUsers);
        Collection<User> users = userService.getAllUsers();
        logger.info(String.valueOf(allUsers));
        assertNotNull(users);
        assertEquals(users, allUsers);
        assertEquals(users.size(), allUsers.size());
        verify(userDao).findAll();
    }

    @Test
    public void testSaveUser() throws Exception {
        User user = new User("Dupont", "password", 1);
        User userMock = new User(1L, "Dupont", "password", 1);
        Mockito.when(userDao.save((user))).thenReturn(userMock);
        User userSaved = userService.saveOrUpdateUser(user);
        logger.info(String.valueOf(userSaved));
        assertNotNull(userSaved);
        assertEquals(userMock.getId(), userSaved.getId());
        assertEquals(userMock.getLogin(), userSaved.getLogin());
        verify(userDao).save(any(User.class));
    }

    @Test
    public void testFindUserByLogin() throws Exception {
        User user = new User("Dupont", "password", 1);
        Mockito.when(userDao.findByLogin(user.getLogin())).thenReturn(Optional.of(user));
        User userFromDB = userService.findByLogin(user.getLogin()).get();
        assertNotNull(userFromDB);
        assertEquals(userFromDB.getLogin(), user.getLogin());
        verify(userDao).findByLogin(any(String.class));
    }

    @Test
    public void testDelete() throws Exception {
        User user = new User("Dupont", "password", 1);
        User userMock = new User(1L, "Dupont", "password", 1);
        Mockito.when(userDao.save((user))).thenReturn(userMock);
        User userSaved = userService.saveOrUpdateUser(user);
        assertNotNull(userSaved);
        assertEquals(userMock.getId(), userSaved.getId());
        userService.deleteUser(userSaved.getId());
        verify(userDao).deleteById(any(Long.class));
    }

    @Test
    public void testUpdateUser() throws Exception {
     /*   User userToUpdate = new User(1L, "Dupont","password" , 1);
        User userUpdated = new User(1L, "Paul", "password", 1);
        //Mockito.when(userDao.findById(1L)).thenReturn(Optional.of(userFoundById));
       // Mockito.when(bCryptPasswordEncoder.matches(any(String.class), any(String.class))).thenReturn(false);
        Mockito.when(bCryptPasswordEncoder.encode(any(String.class))).thenReturn("password");
        Mockito.when(userDao.save((userToUpdate))).thenReturn(userUpdated);
        User userFromDB = userService.saveOrUpdateUser(userToUpdate);
        logger.error(userFromDB.getLogin());
        assertNotNull(userFromDB);
        assertEquals(userUpdated.getLogin(), userFromDB.getLogin());
        verify(userDao).save(any(User.class));*/
    }
}
