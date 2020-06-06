package br.com.agateownz.foodsocial.modules.user.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CreateUserResponse {

    private Long id;
    private String email;
    private String username;
}
