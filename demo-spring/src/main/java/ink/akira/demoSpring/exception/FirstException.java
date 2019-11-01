package ink.akira.demoSpring.exception;

import org.springframework.core.NestedCheckedException;

public class FirstException extends NestedCheckedException {

    public FirstException(String message) {
        super(message);
    }

    public FirstException(String message, Throwable cause) {
        super(message, cause);
    }
}
