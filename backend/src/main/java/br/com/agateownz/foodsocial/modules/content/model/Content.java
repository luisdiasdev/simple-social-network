package br.com.agateownz.foodsocial.modules.content.model;

import br.com.agateownz.foodsocial.modules.content.enums.ContentDiscriminator;
import br.com.agateownz.foodsocial.modules.shared.model.EntityWithTimestamp;
import br.com.agateownz.foodsocial.modules.user.model.User;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "circle_content")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Content extends EntityWithTimestamp {

    private static final String CONTENT_RESOURCE = "/contents/";
    private static final String PRIVATE_CONTENT_RESOURCE = "/contents/internal/";

    @Id
    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false, updatable = false)
    private String path;

    @Column(nullable = false, updatable = false)
    private String contentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private ContentDiscriminator contentDiscriminator;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_content"))
    private User user;

    public boolean isPublic() {
        return contentDiscriminator.isPublic();
    }

    public boolean isPrivate() {
        return !isPublic();
    }

    public String getContentUri() {
        return isPublic()
                ? CONTENT_RESOURCE.concat(uuid)
                : PRIVATE_CONTENT_RESOURCE.concat(uuid);
    }
}
