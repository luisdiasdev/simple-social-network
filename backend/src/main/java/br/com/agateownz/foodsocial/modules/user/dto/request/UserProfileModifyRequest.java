package br.com.agateownz.foodsocial.modules.user.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Optional;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class UserProfileModifyRequest {

    @Size(max = 60)
    private String displayName;

    @Size(max = 100)
    private String website;

    @Size(max = 200)
    private String bio;

    public Optional<String> getDisplayName() {
        return Optional.ofNullable(displayName);
    }

    public Optional<String> getWebsite() {
        return Optional.ofNullable(website);
    }

    public Optional<String> getBio() {
        return Optional.ofNullable(bio);
    }
}
