package capstone.dissent.controllers;

import capstone.dissent.domain.Result;
import capstone.dissent.domain.ResultType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ErrorResponse {
    private final String message;

    public String getMessage() {
        return message;
    }

    public ErrorResponse(String message) {
        this.message = message;
    }

    public static <T> ResponseEntity<Object> build(Result<T> result) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (result.getType() == null || result.getType() == ResultType.INVALID) {
            status = HttpStatus.BAD_REQUEST;
        } else if (result.getType() == ResultType.NOT_FOUND) {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(result.getMessages(), status);
    }
}
