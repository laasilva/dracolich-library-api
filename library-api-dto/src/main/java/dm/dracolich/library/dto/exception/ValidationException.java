package dm.dracolich.library.dto.exception;

import dm.dracolich.library.dto.error.ErrorCodes;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends IllegalArgumentException {

    private final List<ErrorCodes> errors;

    public ValidationException(List<ErrorCodes> errors, String message) {
      super(message);
      this.errors = errors;
    }
}
