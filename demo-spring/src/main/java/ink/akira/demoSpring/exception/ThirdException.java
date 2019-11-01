package ink.akira.demoSpring.exception;

import org.springframework.core.NestedCheckedException;

public class ThirdException extends NestedCheckedException {
    public ThirdException(String message) {
        super(message);
    }

    public ThirdException(String message, Throwable cause) {
        super(message, cause);
    }
}
