package br.com.agateownz.foodsocial.modules.content.controller;

import br.com.agateownz.foodsocial.modules.content.service.ContentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.concurrent.TimeUnit;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.agateownz.foodsocial.config.swagger.SwaggerConstants.COOKIE_AUTH;

@Valid
@RestController
@RequestMapping("/contents")
@Tag(name = "contents")
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * Gets an internal content by its uuid
     *
     * @param uuid The uuid of the content
     * @return The content with the given uuid
     */
    @GetMapping("{uuid}")
    @SecurityRequirement(name = COOKIE_AUTH)
    ResponseEntity<InputStreamResource> internalContent(@PathVariable String uuid) {
        return contentService.getInternalContentByUuid(uuid)
            .map(response ->
                ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(response.getContentType()))
                    .contentLength(response.getContentLength())
                    .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
                    .body(new InputStreamResource(response.getStoreObject().getInputStream()))
            )
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
