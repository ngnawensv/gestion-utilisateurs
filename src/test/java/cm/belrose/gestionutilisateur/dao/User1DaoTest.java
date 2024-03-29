package cm.belrose.gestionutilisateur.dao;

import cm.belrose.gestionutilisateur.entities.User1;

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
public class User1DaoTest {
    private static final Logger log= LoggerFactory.getLogger(User1DaoTest.class);
   // @TestEntityManager permet de persister les données en base lors des tests
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserDao userDao;

    User1 user1 =new User1("Dupont","password",1);

    @Before
    public void setup(){
        entityManager.persist(user1);//on sauvegarde l'objet user1 au début de chaque test
        entityManager.flush();
    }

    @Test
    public void testFindAllUser(){
        log.info("testFindAllUser");
        List<User1> users=userDao.findAll();
        assertEquals(1,users.size());
        //assertNotNull(userDao.findAll());
    }

    @Test
    public void testSaveUser(){
        log.info("testSaveUser");
        User1 user1 =new User1("Paul","password",1);
        User1 user1Saved =userDao.save(user1);
        assertNotNull(user1Saved.getId());
        assertEquals("Paul", user1Saved.getLogin());
    }

    @Test
    public void testFindByLogin(){
        log.info("testFindByLogin");
        User1 user1Found =userDao.findByLogin(user1.getLogin()).get();
        assertEquals("Dupont", user1Found.getLogin());
    }

    @Test
    public void testDeleteUser(){
        log.info("testFindByLogin");
        User1 user1Found =userDao.findByLogin(user1.getLogin()).get();
        assertEquals("Dupont", user1Found.getLogin());
    }

    @Test
    public void testUpdateUser(){
        log.info("testUpdateUser");
        User1 user1ToUpdate =userDao.findByLogin(user1.getLogin()).get();
        user1ToUpdate.setActive(0);
        userDao.save(user1ToUpdate);
        User1 user1UpdateFromDB =userDao.findByLogin(user1ToUpdate.getLogin()).get();
        assertNotNull(user1ToUpdate);
        assertEquals(0, user1UpdateFromDB.getActive().longValue());
    }

}
