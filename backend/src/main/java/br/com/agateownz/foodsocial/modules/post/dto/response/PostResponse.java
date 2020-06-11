package br.com.agateownz.foodsocial.modules.post.dto.response;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private Long id;
    private Long userId;
    private Map message;
    private Set<String> pictures;
    private LocalDateTime createdAt;
}
