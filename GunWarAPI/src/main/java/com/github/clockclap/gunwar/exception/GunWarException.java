package com.github.clockclap.gunwar.exception;

public class GunWarException extends Exception {

    public GunWarException() {
        super();
    }

    public GunWarException(String msg) {
        super(msg);
    }

    public GunWarException(String message, Throwable cause) {
        super(message, cause);
    }

    public GunWarException(Throwable cause) {
        super(cause);
    }

    protected GunWarException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
