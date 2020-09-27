package cm.belrose.gestionutilisateur;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
Ce Service permet juste de demarrer et tester l'application. Il teste si le serveur a bien démarré
 */
/*@RestController: Annotation permmettant à spring d'enregistrer cette classe comme un controller
et surtout de mémoriser les requetes que cette classe est capable de gérer*/
@RestController
public class RestServices {

    private static final Logger logger = LoggerFactory.getLogger(RestServices.class);

    /*@GetMapping(value = "/") equivaut à RequestMapping(method=RequestMethod.GET, value= "/")*/
    @GetMapping(value = "/api")
    public ResponseEntity<String> pong() {
        logger.info("Démarrage des services OK .....");
        return new ResponseEntity<String>("Réponse du serveur: " + HttpStatus.OK.name(), HttpStatus.OK);
    }
}

