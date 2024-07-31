package fr.reluije.threeStars.exceptions;

import java.io.Serial;

public class CreateException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6580002384540837582L;

    public CreateException(String message) {
        super(message);
    }
}
