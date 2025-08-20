package br.com.agateownz.foodsocial.modules.post.model;

import br.com.agateownz.foodsocial.modules.shared.model.EntityWithTimestamp;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Entity
@Builder
@Table(name = "fs_post_mention")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, of = "id")
public class PostMention extends EntityWithTimestamp {

    @EmbeddedId
    private PostMentionId id;
}
