package br.com.agateownz.foodsocial.modules.post.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {

    @NotNull
    @Size(min = 1, message = "size must be greater than {min}")
    private Map message;

    private List<@NotEmpty String> pictures;
}
