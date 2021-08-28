package com.github.clockclap.gunwar.exception;

public class InvalidTargetSelectorException extends TargetSelectorException {

    public InvalidTargetSelectorException() {
        super();
    }

    public InvalidTargetSelectorException(String msg) {
        super(msg);
    }

    public InvalidTargetSelectorException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTargetSelectorException(Throwable cause) {
        super(cause);
    }

}
