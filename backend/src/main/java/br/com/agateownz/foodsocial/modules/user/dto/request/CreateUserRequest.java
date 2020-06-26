package br.com.agateownz.foodsocial.modules.user.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
