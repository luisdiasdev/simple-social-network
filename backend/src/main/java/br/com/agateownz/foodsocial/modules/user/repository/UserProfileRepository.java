package br.com.agateownz.foodsocial.modules.user.repository;

import br.com.agateownz.foodsocial.modules.user.model.UserProfile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {

    Optional<UserProfile> findByUserId(@NotNull Long userId);

    @Modifying
    @Query("update UserProfile u set u.image.uuid = :uuid WHERE u.id = :id")
    void update(Long id, String uuid);
}
