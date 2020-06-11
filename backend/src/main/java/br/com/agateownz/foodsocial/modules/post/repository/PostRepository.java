package br.com.agateownz.foodsocial.modules.post.repository;

import br.com.agateownz.foodsocial.modules.post.model.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long>,
    QuerydslPredicateExecutor<Post>, PostRepositoryCustom {

    Optional<Post> findByIdAndActiveTrue(Long id);

    Optional<Post> findByIdAndActiveTrueAndUserId(Long id, Long userId);

    @Modifying
    @Query("UPDATE Post p SET p.active = false WHERE p.id = :id AND p.user.id = :userId")
    void deletePostByIdAndUserId(Long id, Long userId);
}
