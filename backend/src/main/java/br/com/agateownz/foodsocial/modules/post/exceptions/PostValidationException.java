package br.com.agateownz.foodsocial.modules.post.exceptions;

public class PostValidationException extends RuntimeException {

    public PostValidationException(String message) {
        super(message);
    }
}
