package br.com.agateownz.foodsocial.modules.post.model;

import br.com.agateownz.foodsocial.modules.shared.model.EntityWithLogicExclusion;
import br.com.agateownz.foodsocial.modules.user.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "fs_post")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class Post extends EntityWithLogicExclusion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq_gen")
    @SequenceGenerator(name = "post_seq_gen", sequenceName = "post_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String message;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_post"))
    private User user;

    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        mappedBy = "id.post",
        fetch = FetchType.LAZY
    )
    private Set<PostMention> mentions;

    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        mappedBy = "id.post",
        fetch = FetchType.LAZY
    )
    private Set<PostHashtag> hashtags;

    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        mappedBy = "id.post",
        fetch = FetchType.LAZY
    )
    private Set<PostContent> pictures;

    public static Post of(String request, User user) {
        var post = new Post();
        post.setMessage(request);
        post.setUser(user);
        return post;
    }
}
