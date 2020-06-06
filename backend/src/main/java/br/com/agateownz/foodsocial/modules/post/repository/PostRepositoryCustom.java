package br.com.agateownz.foodsocial.modules.post.repository;

import br.com.agateownz.foodsocial.modules.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<Post> findPostsForFeed(Long userId, Pageable pageable);
}
