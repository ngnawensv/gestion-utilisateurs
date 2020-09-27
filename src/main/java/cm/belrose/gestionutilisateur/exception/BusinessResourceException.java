package cm.belrose.gestionutilisateur.exception;

import org.springframework.http.HttpStatus;


/**
 * Classe d'exception classique BusinessResourceException
 */
public class BusinessResourceException extends RuntimeException {
    private Long resourceId;
    private String errorCode;
    private HttpStatus status;

    public BusinessResourceException(String message) {
        super(message);
    }

    public BusinessResourceException(String message, Long resourceId) {
        super(message);
        this.resourceId = resourceId;
    }

    public BusinessResourceException(String message, Long resourceId, String errorCode) {
        super(message);
        this.resourceId = resourceId;
        this.errorCode = errorCode;
    }

    public BusinessResourceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessResourceException(String message, String errorCode, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
