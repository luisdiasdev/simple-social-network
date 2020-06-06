package br.com.agateownz.foodsocial.modules.shared.exception;

public class UnauthorizedException extends RuntimeException {

    private static final String MESSAGE = "The user is not currently authenticated.";

    public UnauthorizedException() {
        super(MESSAGE);
    }
}
