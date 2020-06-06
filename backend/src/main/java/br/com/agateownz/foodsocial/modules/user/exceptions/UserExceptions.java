package br.com.agateownz.foodsocial.modules.user.exceptions;

import br.com.agateownz.foodsocial.modules.shared.exception.EntityNotFoundException;

public class UserExceptions {

    public static final EntityNotFoundException USER_NOT_FOUND = new EntityNotFoundException("User");
}
