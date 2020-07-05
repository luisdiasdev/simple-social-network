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

    @Query(nativeQuery = true)
    List<MentionUserResponse> findUsersToMention(Long userId, String search);
}
