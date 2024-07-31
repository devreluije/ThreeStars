package fr.reluije.threeStars.exceptions;

import java.io.Serial;

public class ExecutionException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8997033296075099860L;

    public ExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
