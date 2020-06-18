package br.com.agateownz.foodsocial.modules.content.exceptions;

public class ContentSaveException extends RuntimeException {

    public ContentSaveException(String uuid) {
        super("Could not save the content with the uuid: " + uuid);
    }
}
