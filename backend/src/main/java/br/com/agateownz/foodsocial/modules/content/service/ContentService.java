package br.com.agateownz.foodsocial.modules.content.service;

import br.com.agateownz.foodsocial.modules.content.dto.response.InternalContentResponse;
import br.com.agateownz.foodsocial.modules.content.enums.ContentDiscriminator;
import br.com.agateownz.foodsocial.modules.content.exceptions.ContentSaveException;
import br.com.agateownz.foodsocial.modules.content.model.Content;
import br.com.agateownz.foodsocial.modules.content.repository.ContentRepository;
import br.com.agateownz.foodsocial.modules.shared.dto.AuthenticatedUser;
import br.com.agateownz.foodsocial.modules.shared.service.AuthenticationService;
import br.com.agateownz.foodsocial.modules.shared.service.StorageService;
import br.com.agateownz.foodsocial.modules.shared.service.UuidService;
import br.com.agateownz.foodsocial.modules.user.model.User;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static br.com.agateownz.foodsocial.modules.content.exceptions.ContentExceptions.CONTENT_NOT_FOUND;

@Service
public class ContentService {

    @Autowired
    private StorageService storageService;
    @Autowired
    private UuidService uuidService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private ContentRepository contentRepository;

    @SneakyThrows
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Content save(ContentDiscriminator discriminator, MultipartFile file) {
        var content = new Content();
        content.setUuid(uuidService.getRandomUuuid());
        content.setContentDiscriminator(discriminator);
        content.setContentLength(file.getSize());

        Optional.ofNullable(file.getContentType())
            .ifPresent(content::setContentType);

        authenticationService.getAuthenticatedUser()
            .map(AuthenticatedUser::getUserId)
            .ifPresent(userId -> content.setUser(new User(userId)));

        var path = storageService.store(
            content.getUuid(),
            discriminator.name(),
            file.getContentType(),
            file.getBytes(),
            discriminator.isPublic()
        ).orElseThrow(() -> new ContentSaveException(content.getUuid()));

        content.setPath(path);

        return contentRepository.save(content);
    }

    public Content findContentBelongingToUser(
        ContentDiscriminator discriminator,
        String uuid,
        Long userId) {
        return contentRepository
            .findByContentDiscriminatorAndUuidAndUserId(discriminator, uuid, userId)
            .orElseThrow(() -> CONTENT_NOT_FOUND);
    }

    public void delete(Content content) {
        var filePath = content.getStoragePath();
        storageService.delete(filePath);
        contentRepository.delete(content);
    }

    public boolean isContentsOwnedByCurrentUser(List<String> uuids) {
        return contentRepository.findNumberOfContentsOwnedByUser(
            uuids,
            authenticationService.getAuthenticatedUserId()) == uuids.size();
    }

    public List<Content> getContentsByUuidBelongingToUser(List<String> uuids, Long userId) {
        return contentRepository.findByUuidInAndUserId(uuids, userId);
    }

    public Optional<InternalContentResponse> getInternalContentByUuid(String uuid) {
        return contentRepository.findById(uuid)
            .filter(Content::isPrivate)
            .map(content -> new InternalContentResponse(
                content.getContentType(),
                content.getContentLength(),
                storageService.get(content.getFileName())
            ));
    }
}
