package br.com.agateownz.foodsocial.modules.user.model;

import br.com.agateownz.foodsocial.modules.content.model.Content;
import br.com.agateownz.foodsocial.modules.shared.model.EntityWithTimestamp;
import br.com.agateownz.foodsocial.modules.user.dto.request.UserProfileModifyRequest;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

@Data
@Entity
@Table(name = "fs_user_profile")
@EqualsAndHashCode(callSuper = true)
public class UserProfile extends EntityWithTimestamp {

    @Id
    private Long id;

    @Column(length = 60)
    private String displayName;

    @Column(length = 100)
    private String website;

    @Column(length = 200)
    private String bio;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_profile"))
    @MapsId
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_image_id", foreignKey = @ForeignKey(name = "fk_user_profile_image"))
    private Content image;

    @Column(length = 10, nullable = false)
    private String avatarColor;

    public static UserProfile of(UserProfileModifyRequest request,
        User user,
        String avatarColor) {
        var profile = new UserProfile();
        profile.setUser(user);
        profile.setAvatarColor(avatarColor);
        populateRequestFields(profile, request);
        return profile;
    }

    private static void populateRequestFields(UserProfile profile, UserProfileModifyRequest request) {
        request.getDisplayName()
            .ifPresent(profile::setDisplayName);
        request.getWebsite()
            .ifPresent(profile::setWebsite);
        request.getBio()
            .ifPresent(profile::setBio);
    }

    public UserProfile update(UserProfileModifyRequest request) {
        populateRequestFields(this, request);
        return this;
    }

    public String getInitials() {
        if (StringUtils.isEmpty(this.displayName)) {
            return null;
        }
        return Arrays.stream(this.displayName.split(" "))
            .map(part -> part.substring(0, 1))
            .limit(2)
            .collect(Collectors.joining(""))
            .toUpperCase();
    }
}
