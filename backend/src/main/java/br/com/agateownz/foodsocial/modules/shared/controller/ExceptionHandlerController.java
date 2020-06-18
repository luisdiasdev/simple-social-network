package br.com.agateownz.foodsocial.modules.shared.controller;

import br.com.agateownz.foodsocial.modules.content.exceptions.ContentSaveException;
import br.com.agateownz.foodsocial.modules.post.exceptions.PostValidationException;
import br.com.agateownz.foodsocial.modules.shared.dto.Message;
import br.com.agateownz.foodsocial.modules.shared.exception.EntityNotFoundException;
import br.com.agateownz.foodsocial.modules.shared.exception.UnauthorizedException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public List<Message> handleEntityNotFoundException(EntityNotFoundException ex) {
        return getMessageFromException(ex);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public List<Message> handleUnauthorizedException(UnauthorizedException ex) {
        return getMessageFromException(ex);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(PostValidationException.class)
    public List<Message> handlePostValidationException(PostValidationException ex) {
        return getMessageFromException(ex);
    }

    public List<Message> handleContentSaveException(ContentSaveException ex) {
        return getMessageFromException(ex);
    }

    private List<Message> getMessageFromException(RuntimeException ex) {
        return List.of(Message.of(ex.getMessage()));
    }
}
