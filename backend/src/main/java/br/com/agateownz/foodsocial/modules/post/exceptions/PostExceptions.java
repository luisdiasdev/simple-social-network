package br.com.agateownz.foodsocial.modules.post.exceptions;

import br.com.agateownz.foodsocial.modules.shared.exception.EntityNotFoundException;

public class PostExceptions {

    public static final EntityNotFoundException POST_NOT_FOUND = new EntityNotFoundException("Post");
}
