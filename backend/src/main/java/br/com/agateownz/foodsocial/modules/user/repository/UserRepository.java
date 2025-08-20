package br.com.agateownz.foodsocial.modules.user.repository;

import br.com.agateownz.foodsocial.modules.user.dto.response.MentionUserResponse;
import br.com.agateownz.foodsocial.modules.user.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findByIdIn(List<Long> ids);

    @Query(value = """
        SELECT cu.id as id, 
               cu.username as "value", 
               cup.display_name as displayName, 
               cup.avatar_color as avatarColor, 
               cup.user_profile_image_id as imageUri 
        FROM fs_user cu 
        INNER JOIN fs_user_profile cup ON cu.id = cup.user_id 
        WHERE cu.id IN (
            SELECT f1.follower_id 
            FROM fs_user_follower f1 
            INNER JOIN fs_user_profile p ON f1.follower_id = p.user_id 
            INNER JOIN fs_user cu1 ON f1.follower_id = cu1.id 
            WHERE f1.user_id = ?1 AND (p.display_name LIKE ?2 OR cu1.username LIKE ?2)
            UNION ALL
            SELECT f2.following_id 
            FROM fs_user_following f2 
            INNER JOIN fs_user_profile p ON f2.following_id = p.user_id 
            INNER JOIN fs_user cu2 ON f2.following_id = cu2.id 
            WHERE f2.user_id = ?1 AND (p.display_name LIKE ?2 OR cu2.username LIKE ?2)
        ) 
        ORDER BY cu.username ASC
        """, nativeQuery = true)
    List<MentionUserResponse> findUsersToMention(Long userId, String search);
}
