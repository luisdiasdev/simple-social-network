package br.com.agateownz.foodsocial.modules.post.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequest {

    @NotNull
    @NotBlank
    private Map message;

    private List<@NotNull @NotBlank String> pictures;
}
