package com.codingame.game.action;

public class InvalidAction extends Exception{

    public InvalidAction() {
    }

    public InvalidAction(String message) {
        super(message);
    }

    public InvalidAction(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAction(Throwable cause) {
        super(cause);
    }

    public InvalidAction(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
