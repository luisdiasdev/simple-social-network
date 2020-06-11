package br.com.agateownz.foodsocial.modules.content.service;

import br.com.agateownz.foodsocial.modules.content.enums.ContentDiscriminator;
import br.com.agateownz.foodsocial.modules.content.model.Content;
import br.com.agateownz.foodsocial.modules.content.repository.ContentRepository;
import br.com.agateownz.foodsocial.modules.shared.dto.AuthenticatedUser;
import br.com.agateownz.foodsocial.modules.shared.service.AuthenticationService;
import br.com.agateownz.foodsocial.modules.shared.service.StorageService;
import br.com.agateownz.foodsocial.modules.shared.service.UuidService;
import br.com.agateownz.foodsocial.modules.user.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
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

    public Content save(ContentDiscriminator discriminator, MultipartFile file) {
        var content = new Content();
        content.setUuid(uuidService.getRandomUuuid());
        content.setContentDiscriminator(discriminator);

        Optional.ofNullable(file.getContentType())
            .ifPresent(content::setContentType);

        storageService.store(content.getUuid(), discriminator.name(), file)
            .ifPresent(content::setPath);

        authenticationService.getAuthenticatedUser()
            .map(AuthenticatedUser::getUserId)
            .ifPresent(userId -> content.setUser(new User(userId)));

        return contentRepository.save(content);
    }

    public Content findContentBelongingToUser(ContentDiscriminator discriminator,
        String uuid,
        Long userId) {
        return contentRepository
            .findByContentDiscriminatorAndUuidAndUserId(discriminator, uuid, userId)
            .orElseThrow(() -> CONTENT_NOT_FOUND);
    }

    public FileSystemResource getFileByUuid(String uuid) {
        var content = contentRepository.findById(uuid)
            .filter(Content::isPublic)
            .orElseThrow(() -> CONTENT_NOT_FOUND);

        return getSystemFileFromContent(content);
    }

    public FileSystemResource getInternalContentByUuid(String uuid) {
        var content = contentRepository.findById(uuid)
            .filter(Content::isPrivate)
            .orElseThrow(() -> CONTENT_NOT_FOUND);

        return getSystemFileFromContent(content);
    }

    public void delete(Content content) {
        var filePath = content.getPath();
        contentRepository.delete(content);
        storageService.delete(filePath);
    }

    public boolean isContentsOwnedByCurrentUser(List<String> uuids) {
        return contentRepository.findNumberOfContentsOwnedByUser(
            uuids,
            authenticationService.getAuthenticatedUserId()) == uuids.size();
    }

    public List<Content> getContentsByUuidBelongingToUser(List<String> uuids, Long userId) {
        return contentRepository.findByUuidInAndUserId(uuids, userId);
    }

    private FileSystemResource getSystemFileFromContent(Content content) {
        return storageService.find(content.getPath())
            .map(FileSystemResource::new)
            .orElseThrow(() -> CONTENT_NOT_FOUND);
    }
}
