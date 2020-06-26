package br.com.agateownz.foodsocial.modules.post.dto.request;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {

    @NotNull
    @NotBlank
    private Map message;

    private List<@NotNull @NotBlank String> pictures;
}
