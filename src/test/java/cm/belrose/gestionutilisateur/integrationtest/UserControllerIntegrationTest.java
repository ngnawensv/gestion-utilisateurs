package cm.belrose.gestionutilisateur.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import cm.belrose.gestionutilisateur.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

/**
 * @SpringBootTest: -Declanche le demarrage du serveur d'application sur un port libre,
 * -Crée une ApplicationContext pour les tests
 * -Initialise une servelet embarquée
 * -Enregistre un bean TestRestTemplate pourla gestion des requetes HTTP
 * @TestPropertySource : Charge le fichier de proprietés contenant toutes les informations nécéssaires pour les tests (
 * configuration du profil de test, de la base de données, des logs, etc.)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
public class UserControllerIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(UserControllerIntegrationTest.class);
    //Injection d'une dépendance enregistrée par @SpringBootTest pour écrire les requêtes HTTP
    @Autowired
    private TestRestTemplate restTemplate;
    //permet d'utiliser le port local du serveur, sinon une erreur "Connection refused"
    @LocalServerPort
    private int port;
    //url du serveur REST. Cette URL peut être celle d'un serveur distant
    private static final String URL = "http://localhost:8080";

    private String getURLWithPort(String uri) {
        return URL + uri;
    }

    @Test
    public void testFindAllUsers() throws Exception {
       // ResponseEntity<Object> responseEntity = restTemplate.getForEntity(getURLWithPort("/gestionutilisateur/user/users"), Object.class);

       // Collection<User> userCollections = (Collection<User>) responseEntity.getBody();
       // logger.info("Utilisateur trouvé : " + userCollections.toString());


// On vérifie le code de réponse HTTP, en cas de différence entre les deux valeurs, le message "Réponse inattendue"
       // assertEquals("Réponse inattendue", HttpStatus.FOUND.value(), responseEntity.getStatusCodeValue());

       // assertNotNull(userCollections);
       // assertEquals(1, userCollections.size());//on a bien 3 utilisateurs initialisés par les scripts data.sql au démarrage des tests
    }
}
