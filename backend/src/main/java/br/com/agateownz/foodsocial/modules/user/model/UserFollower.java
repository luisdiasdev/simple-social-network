package br.com.agateownz.foodsocial.modules.user.model;

import br.com.agateownz.foodsocial.modules.shared.model.EntityWithTimestamp;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Builder
@Table(name = "circle_user_follower")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, of = "id")
public class UserFollower extends EntityWithTimestamp {

    @EmbeddedId
    private UserFollowerId id;
}