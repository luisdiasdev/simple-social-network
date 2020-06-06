package br.com.agateownz.foodsocial.modules.user.dto.response;

import br.com.agateownz.foodsocial.modules.shared.annotations.JsonDateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class DefaultUserResponse {

    private Long id;
    private String username;
    @JsonDateTimeFormat
    private LocalDateTime createdAt;
}
