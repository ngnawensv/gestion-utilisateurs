package cm.belrose.gestionutilisateur.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private Long resourceId;
	private String errorCode;
	private HttpStatus status;

    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(Long resourceId, String message) {
        super(message);
        this.resourceId = resourceId;
    }
    public ResourceNotFoundException(Long resourceId, String errorCode, String message) {
    	super(message);
        this.resourceId = resourceId;
        this.errorCode = errorCode;
    }
    
    public ResourceNotFoundException(String errorCode, String message) {
    	super(message);
        this.errorCode = errorCode;
    }

	public ResourceNotFoundException(String errorCode, HttpStatus status) {
		this.errorCode = errorCode;
		this.status = status;
	}
    
    public ResourceNotFoundException(String errorCode, String message, HttpStatus status) {
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
