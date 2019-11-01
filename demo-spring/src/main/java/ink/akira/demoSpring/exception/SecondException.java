package ink.akira.demoSpring.exception;

import org.springframework.core.NestedCheckedException;

public class SecondException extends NestedCheckedException {

    public SecondException(String message) {
        super(message);
    }

    public SecondException(String message, Throwable cause) {
        super(message, cause);
    }
}
