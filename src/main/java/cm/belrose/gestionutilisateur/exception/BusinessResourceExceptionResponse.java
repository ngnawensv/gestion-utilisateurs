package cm.belrose.gestionutilisateur.exception;

import org.springframework.http.HttpStatus;

/**
 * Classe POJO BusinessResourceExceptionResponse de persistance des messages d'erreurs ExceptionResponse
 */
public class BusinessResourceExceptionResponse {
    private String errorCode;
    private String errorMessage;
    private String requestURL;
    private HttpStatus status;

    public BusinessResourceExceptionResponse() {
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
