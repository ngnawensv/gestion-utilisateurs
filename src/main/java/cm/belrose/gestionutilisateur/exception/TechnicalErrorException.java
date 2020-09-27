package cm.belrose.gestionutilisateur.exception;

/**
 * Classe de gestion d'erreurs techniques TechnicalErrorException
 */
public class TechnicalErrorException extends RuntimeException {

    private Long id;

    public TechnicalErrorException() {
        super();
    }

    public TechnicalErrorException(String message) {
        super(message);
    }

    public TechnicalErrorException(Throwable cause) {
        super(cause);
    }

    public TechnicalErrorException(String message, Throwable cause) {
        super(message,cause);
    }

    public TechnicalErrorException(Long id) {
        super(id.toString());
        this.id=id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
