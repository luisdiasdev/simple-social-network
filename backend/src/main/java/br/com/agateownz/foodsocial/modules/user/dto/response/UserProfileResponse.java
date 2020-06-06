package br.com.agateownz.foodsocial.modules.user.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserProfileResponse {

    private String displayName;
    private String website;
    private String bio;
    private String avatarColor;
}
