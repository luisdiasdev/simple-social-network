package br.com.agateownz.foodsocial.modules.user.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUserRequest {

    @Size(min = 4, max = 30)
    @NotNull
    private String username;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;
}
