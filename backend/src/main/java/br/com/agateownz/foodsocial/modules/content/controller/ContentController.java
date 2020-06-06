package br.com.agateownz.foodsocial.modules.content.controller;

import br.com.agateownz.foodsocial.modules.content.service.ContentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static br.com.agateownz.foodsocial.config.swagger.SwaggerConstants.COOKIE_AUTH;

@Valid
@RestController
@RequestMapping("/contents")
@Tag(name = "contents")
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * Gets a content by its uuid
     *
     * @param uuid The uuid of the content
     * @return The content with the given uuid
     */
    @GetMapping("{uuid}")
    FileSystemResource content(@PathVariable String uuid) {
        return contentService.getFileByUuid(uuid);
    }

    /**
     * Gets an internal content by its uuid
     *
     * @param uuid The uuid of the content
     * @return The content with the given uuid
     */
    @GetMapping("internal/{uuid}")
    @SecurityRequirement(name = COOKIE_AUTH)
    FileSystemResource internalContent(@PathVariable String uuid) {
        return contentService.getInternalContentByUuid(uuid);
    }
}
