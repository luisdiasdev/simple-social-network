package br.com.agateownz.foodsocial.modules.content.exceptions;

import br.com.agateownz.foodsocial.modules.shared.exception.EntityNotFoundException;

public class ContentExceptions {

    public static EntityNotFoundException CONTENT_NOT_FOUND = new EntityNotFoundException("Content");
}
