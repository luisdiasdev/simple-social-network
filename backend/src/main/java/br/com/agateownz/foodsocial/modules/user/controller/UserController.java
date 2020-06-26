package br.com.agateownz.foodsocial.modules.user.controller;

import br.com.agateownz.foodsocial.modules.user.dto.request.CreateUserRequest;
import br.com.agateownz.foodsocial.modules.user.dto.response.CreateUserResponse;
import br.com.agateownz.foodsocial.modules.user.dto.response.DefaultUserResponse;
import br.com.agateownz.foodsocial.modules.user.dto.response.MentionUserResponse;
import br.com.agateownz.foodsocial.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static br.com.agateownz.foodsocial.config.swagger.SwaggerConstants.COOKIE_AUTH;

@Validated
@RequestMapping("/users")
@RestController
@Tag(name = "users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Creates an user
     *
     * @param request The user information
     * @return The new user information
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse save(@Valid @RequestBody CreateUserRequest request) {
        return userService.save(request);
    }

    /**
     * Gets an user by its name
     *
     * @param name The user name to search for
     * @return The existing user information
     */
    @GetMapping("{name}")
    @SecurityRequirement(name = COOKIE_AUTH)
    public DefaultUserResponse findByName(@PathVariable String name) {
        return userService.findByUsername(name);
    }

    /**
     * Gets a list of users to mention
     *
     * @param search The search query, which may contain username and/or display name
     * @return A list of persons the current user can mention
     */
    @GetMapping("name")
    @SecurityRequirement(name = COOKIE_AUTH)
    public List<MentionUserResponse> findUsersToMention(@NotBlank String search) {
        return userService.findUsersToMention(search);
    }
}
