package cm.belrose.gestionutilisateur.exception;

import org.springframework.http.HttpStatus;

/**
 * Classe POJO(Plain Old Java Objet: Simple classe java avec juste attributs et getters/setters)
 * BusinessResourceExceptionResponse de persistance des messages d'erreurs ExceptionResponse
 */
public class ExceptionResponse {

    private String errorCode;
    private String errorMessage;
    private String requestURL;
    private HttpStatus status;

    public ExceptionResponse() {
    }

    public ExceptionResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ExceptionResponse(String errorCode, String errorMessage, String requestURL) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.requestURL = requestURL;
    }

    public ExceptionResponse(String errorCode, String errorMessage, String requestURL, HttpStatus status) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.requestURL = requestURL;
        this.status = status;
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

    public void setRequestURL(String url) {
        this.requestURL = url;

    }
    public String getRequestURL(){
        return requestURL;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
