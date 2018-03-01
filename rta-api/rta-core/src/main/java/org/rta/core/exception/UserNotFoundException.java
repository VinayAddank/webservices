package org.rta.core.exception;

public class UserNotFoundException extends NotFoundException{
    
    /**
     * 
     */
    private static final long serialVersionUID = -4248870205900875459L;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }

}
