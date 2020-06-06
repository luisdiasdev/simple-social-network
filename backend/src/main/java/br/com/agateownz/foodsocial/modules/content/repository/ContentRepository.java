package br.com.agateownz.foodsocial.modules.content.repository;

import br.com.agateownz.foodsocial.modules.content.enums.ContentDiscriminator;
import br.com.agateownz.foodsocial.modules.content.model.Content;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends CrudRepository<Content, String> {

    Optional<Content> findByContentDiscriminatorAndUuidAndUserId(ContentDiscriminator discriminator,
                                                                 String uuid,
                                                                 Long userId);

    List<Content> findByUuidInAndUserId(List<String> uuid, Long userId);

    @Query("SELECT COUNT(*) FROM Content c WHERE c.uuid IN :uuids AND c.user.id = :userId")
    long findNumberOfContentsOwnedByUser(List<String> uuids, Long userId);
}
