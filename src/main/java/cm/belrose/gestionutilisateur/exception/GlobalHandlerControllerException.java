package cm.belrose.gestionutilisateur.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @ControllerAdvice sur la classe GlobalHandlerControllerException, permet que cette classe soit capable d'intercepter
 * et de gérer plusieurs types d'erreurs grâce à l'annotation @ExceptionHandler avec en paramètre le type d'exception.
 * On peut aussi lui passer plusieurs classes d'exceptions pour le même Handler
 *
 * l'attribut basePackages de @ControllerAdvice permet d'indiquer dans quels packages se trouvent
 * les contrôleurs à prendre en compte par cette classe (Dans notre cas tous les controlleurs
 * du package  cm.belrose.gestionutilisateur sont concernés et leurs erreurs seront gérées par GlobalHandlerControllerException
 */

@ControllerAdvice(basePackages = {"cm.belrose.gestionutilisateur"})
public class GlobalHandlerControllerException extends ResponseEntityExceptionHandler {

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        //L'on peut initialiser toute autre donnée ici

    }

    @ModelAttribute //La variable "technicalError" pourra etre exploité n'importe ou dans l'application
    public void globalAttributes(Model model) {
        model.addAttribute("technicalError", "Une erreur technique est survenue!");
    }

    @ExceptionHandler(TechnicalErrorException.class)
    public ModelAndView technicalErrorException(Exception exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception.getMessage());
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(Exception.class)//toutes les autres erreurs non gérées sont interceptées ici
    public ResponseEntity<BusinessResourceExceptionResponse> unknowError(HttpServletRequest req, Exception ex) {
        BusinessResourceExceptionResponse response = new BusinessResourceExceptionResponse();
        response.setErrorCode("Technical Error");
        response.setErrorMessage(ex.getMessage());
        response.setRequestURL(req.getRequestURL().toString());
        return new ResponseEntity<BusinessResourceExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(BusinessResourceException.class)
    public ResponseEntity<BusinessResourceExceptionResponse> resourceNotFound(HttpServletRequest req, BusinessResourceException ex) {
        BusinessResourceExceptionResponse response = new BusinessResourceExceptionResponse();
        response.setStatus(ex.getStatus());
        response.setErrorCode(ex.getErrorCode());
        response.setErrorMessage(ex.getMessage());
        response.setRequestURL(req.getRequestURL().toString());
        return new ResponseEntity<BusinessResourceExceptionResponse>(response, ex.getStatus());
    }

}
