package com.github.clockclap.gunwar.exception;

public class TargetSelectorException extends GunWarException {

    public TargetSelectorException() {
        super();
    }

    public TargetSelectorException(String msg) {
        super(msg);
    }

    public TargetSelectorException(String message, Throwable cause) {
        super(message, cause);
    }

    public TargetSelectorException(Throwable cause) {
        super(cause);
    }

    protected TargetSelectorException(String message, Throwable cause,
                              boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
