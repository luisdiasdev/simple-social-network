package br.com.agateownz.foodsocial.modules.content.model;

import br.com.agateownz.foodsocial.modules.content.enums.ContentDiscriminator;
import br.com.agateownz.foodsocial.modules.shared.model.EntityWithTimestamp;
import br.com.agateownz.foodsocial.modules.user.model.User;
import javax.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@Table(name = "fs_content")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Content extends EntityWithTimestamp {

    public static final String PRIVATE_CONTENT_RESOURCE = "/api/contents/";

    @Id
    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private String uuid;

    @Column
    private String path;

    @Column(nullable = false, updatable = false)
    private String contentType;

    @Column(nullable = false, updatable = false)
    private Long contentLength;

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

    public String getFileName() {
        return contentDiscriminator.name().concat("/").concat(uuid);
    }

    public String getUri() {
        if (contentDiscriminator.isPublic()) {
            return path;
        }
        return PRIVATE_CONTENT_RESOURCE.concat(uuid);
    }

    public String getStoragePath() {
        if (contentDiscriminator.isPublic()) {
            return path;
        }
        return getFileName();
    }
}
