package cm.belrose.gestionutilisateur.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {
   /* @InitBinder
    public void dataBinding(WebDataBinder binder) {
        //Vous pouvez intialiser toute autre donnée ici
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "customDateBinder", new CustomDateEditor(dateFormat, true));

    }
    @ModelAttribute //La variable "technicalError" pourra etre exploité n'importe ou dans l'application
    public void globalAttributes(Model model) {
        model.addAttribute("technicalError", "Une erreur technique est survenue !");
    }

   @ExceptionHandler(TechnicalErrorException.class)
    public ModelAndView technicalErrorException(Exception exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception.getMessage());
        mav.setViewName("error");
        return mav;
    }*/

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public  @ResponseBody ExceptionResponse resourceNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        ExceptionResponse error = new ExceptionResponse();
        error.setErrorCode(ex.getErrorCode());
        error.setErrorMessage(ex.getMessage());
        error.setRequestURL(req.getRequestURL().toString());
        return error;
    }

    @ExceptionHandler(Exception.class)//toutes les autres erreurs non gérées sont interceptées ici
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ExceptionResponse unknowError(Exception ex,HttpServletRequest req) {
        ExceptionResponse error = new ExceptionResponse();
        error.setErrorCode("Technical Error");
        error.setErrorMessage(ex.getMessage());
        error.setRequestURL(req.getRequestURL().toString());
        return error ;

    }

}
