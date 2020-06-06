package br.com.agateownz.foodsocial.modules.post.dto.response;

import br.com.agateownz.foodsocial.modules.user.dto.response.UserProfileWithPictureResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponse {

    private PostResponse post;
    private UserProfileWithPictureResponse user;
}
