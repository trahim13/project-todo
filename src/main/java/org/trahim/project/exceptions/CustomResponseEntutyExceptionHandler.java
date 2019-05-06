package org.trahim.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntutyExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdExeeption(ProjectIdException ex, WebRequest request) {
        ProjectIdExceptionResponce projectIdExceptionResponce = new ProjectIdExceptionResponce(ex.getMessage());

        return new ResponseEntity<>(projectIdExceptionResponce, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    final ResponseEntity<Object> handleNotFoundException(ProjectNotFoundException ex, WebRequest request) {

        ProjectNotFoundExceptionResponce projectNotFoundExceptionResponce = new ProjectNotFoundExceptionResponce(ex.getMessage());

        return new ResponseEntity<>(projectNotFoundExceptionResponce, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    final ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, WebRequest request) {

        UsernameAlreadyExistsExceptionResponse usernameAlreadyExistsExceptionResponse = new UsernameAlreadyExistsExceptionResponse(ex.getMessage());

        return new ResponseEntity<>(usernameAlreadyExistsExceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
