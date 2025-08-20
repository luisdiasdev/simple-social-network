package br.com.agateownz.foodsocial.modules.user.model;

import br.com.agateownz.foodsocial.modules.shared.model.EntityWithTimestamp;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Entity
@Builder
@Table(name = "fs_user_following")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, of = "id")
public class UserFollowing extends EntityWithTimestamp {

    @EmbeddedId
    private UserFollowingId id;
}