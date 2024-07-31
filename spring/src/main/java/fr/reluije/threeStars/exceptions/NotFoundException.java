package fr.reluije.threeStars.exceptions;

import java.io.Serial;

public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4132669004486911997L;

    public NotFoundException(String message) {
        super(message);
    }
}
