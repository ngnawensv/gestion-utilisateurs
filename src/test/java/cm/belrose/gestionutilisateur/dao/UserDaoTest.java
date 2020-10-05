package cm.belrose.gestionutilisateur.dao;

import cm.belrose.gestionutilisateur.entities.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * @author Ngnawen Samuel
 * @RunWith(SpringRunner.class): permet d'établir la liaison d'implementation Spring de JUnit
 * @DataJpaTest est une implementation Spring de JPA qui fournit une configuration intégrée de la base
 * de données H2, Hibernate, SpringData et de la dataSource. Cette annotation active aussi la détection des
 * entités annotées par Entity et intègre aussi la gestion des logs SQL
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
//@TestPropertySource(locations="classpath:application.properties")
public class UserDaoTest {
    private static final Logger log= LoggerFactory.getLogger(UserDaoTest.class);
   // @TestEntityManager permet de persister les données en base lors des tests
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserDao userDao;

    User user=new User("Dupont","password",1);

    @Before
    public void setup(){
        entityManager.persist(user);//on sauvegarde l'objet user au début de chaque test
        entityManager.flush();
    }

    @Test
    public void testFindAllUser(){
        log.info("testFindAllUser");
        List<User> users=userDao.findAll();
        assertEquals(1,users.size());
        //assertNotNull(userDao.findAll());
    }

    @Test
    public void testSaveUser(){
        log.info("testSaveUser");
        User user=new User("Paul","password",1);
        User userSaved=userDao.save(user);
        assertNotNull(userSaved.getId());
        assertEquals("Paul",userSaved.getLogin());
    }

    @Test
    public void testFindByLogin(){
        log.info("testFindByLogin");
        User userFound=userDao.findByLogin(user.getLogin()).get();
        assertEquals("Dupont",userFound.getLogin());
    }

    @Test
    public void testDeleteUser(){
        log.info("testFindByLogin");
        User userFound=userDao.findByLogin(user.getLogin()).get();
        assertEquals("Dupont",userFound.getLogin());
    }

    @Test
    public void testUpdateUser(){
        log.info("testUpdateUser");
        User userToUpdate=userDao.findByLogin(user.getLogin()).get();
        userToUpdate.setActive(0);
        userDao.save(userToUpdate);
        User userUpdateFromDB=userDao.findByLogin(userToUpdate.getLogin()).get();
        assertNotNull(userToUpdate);
        assertEquals(0,userUpdateFromDB.getActive().longValue());
    }

}
