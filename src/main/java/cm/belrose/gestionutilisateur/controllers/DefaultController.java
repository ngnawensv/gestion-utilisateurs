package cm.belrose.gestionutilisateur.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author Ngnawen
 * <p>
 * La classe DefaultController est juste un contrôleur utilitaire qui va nous renseigner automatiquement dès le
 * démarrage du client si le serveur REST est à l'écoute grâce à la réponse renvoyée par HttpStatus.
 * Si le serveur est actif, le message de réponse sera: Réponse du serveur: OK, avec le code de réponse HTTP = 200.
 * Sinon, une page d'erreur sera affichée.
 */

/**
 * @RestController: Annotation permmettant à spring d'enregistrer cette classe comme un controller
 * et surtout de mémoriser les requetes que cette classe est capable de gérer
 */
@RestController
public class DefaultController {

    private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);

    /**
     * @GetMapping(value = "/") equivaut à RequestMapping(method=RequestMethod.GET, value= "/")
     *
     * ResponseEntity de Spring permet de prendre en compte la gestion des statuts HTTP et des réponses
     * On peut toutefois utiliser la classe ResponseBody pour traiter les réponses si on n'a pas besoin d'exploiter
     * les codes de réponses HTTP. On peut donc dire que ResponseEntity = ResponseBody + HttpStatus
     */
    @GetMapping(value = "/default")
    public ResponseEntity<String> pong() {
        logger.info("Démarrage des services OK .....");
        return new ResponseEntity<String>("Réponse du serveur: " + HttpStatus.OK.name(), HttpStatus.OK);
    }
}

