package br.com.agateownz.foodsocial.modules.user.model;

import br.com.agateownz.foodsocial.modules.shared.model.EntityWithTimestamp;
import br.com.agateownz.foodsocial.modules.user.dto.request.UserCreateRequest;
import br.com.agateownz.foodsocial.modules.user.dto.response.MentionUserResponse;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Entity
@Builder
@Table(name = "fs_user", uniqueConstraints = {
    @UniqueConstraint(name = "uk_username_email", columnNames = {"username", "email"}),
    @UniqueConstraint(name = "uk_email", columnNames = {"email"})
})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@NamedNativeQuery(
    name = "UserMentionSearch",
    query = "select"
        + " cu.id as id,"
        + " cu.username as value,"
        + " cup.display_name as displayName,"
        + " cup.avatar_color as avatarColor,"
        + " cup.user_profile_image_id as imageUri"
        + " from fs_user cu"
        + " inner join fs_user_profile cup on cu.id = cup.user_id"
        + " where cu.id in"
        + " (select f1.follower_id from fs_user_follower f1"
        + " inner join fs_user_profile p on f1.follower_id = p.user_id"
        + " inner join fs_user cu1 on f1.follower_id = cu1.id"
        + " where f1.user_id = :userId"
        + " and (p.display_name like :search or cu1.username like :search)"
        + " union all"
        + " select f2.following_id from fs_user_following f2"
        + " inner join fs_user_profile p on f2.following_id = p.user_id"
        + " inner join fs_user cu2 on f2.following_id = cu2.id"
        + " where f2.user_id = :userId "
        + " and (p.display_name like :search or cu2.username like :search))"
        + " order by cu.username asc",
    resultSetMapping = "UserMentionSearchMapping"
)
@SqlResultSetMapping(
    name = "UserMentionSearchMapping",
    classes = {
        @ConstructorResult(
            columns = {
                @ColumnResult(name = "id", type = Long.class),
                @ColumnResult(name = "value", type = String.class),
                @ColumnResult(name = "displayName", type = String.class),
                @ColumnResult(name = "avatarColor", type = String.class),
                @ColumnResult(name = "imageUri", type = String.class),
            },
            targetClass = MentionUserResponse.class
        )
    }
)
public class User extends EntityWithTimestamp implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private UserProfile userProfile;

    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        mappedBy = "id.user",
        fetch = FetchType.LAZY
    )
    private Set<UserFollower> followers;

    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        mappedBy = "id.user",
        fetch = FetchType.LAZY
    )
    private Set<UserFollowing> following;

    public User(Long id) {
        this.id = id;
    }

    public static User of(UserCreateRequest request) {
        var user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
