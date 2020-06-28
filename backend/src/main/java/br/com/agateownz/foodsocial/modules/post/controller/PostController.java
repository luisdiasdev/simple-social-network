package br.com.agateownz.foodsocial.modules.post.controller;

import br.com.agateownz.foodsocial.modules.content.dto.response.ContentResponse;
import br.com.agateownz.foodsocial.modules.post.dto.request.CreatePostRequest;
import br.com.agateownz.foodsocial.modules.post.dto.response.PostResponse;
import br.com.agateownz.foodsocial.modules.post.service.PostService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static br.com.agateownz.foodsocial.config.swagger.SwaggerConstants.COOKIE_AUTH;

@Validated
@RequestMapping("/posts")
@RestController
@Tag(name = "posts")
@SecurityRequirement(name = COOKIE_AUTH)
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * Get post by id
     *
     * @param id The id of the post
     * @return The post with the given id
     */
    @GetMapping("{id}")
    @ApiResponse(responseCode = "404", description = "post not found")
    public PostResponse get(@PathVariable Long id) {
        return postService.getById(id);
    }

    /**
     * Creates a post
     *
     * @param request The new post information
     * @return The created post
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse save(@Valid @RequestBody CreatePostRequest request) {
        return postService.save(request);
    }

    /**
     * Uploads a picture that can be associated with a post
     *
     * @param file The picture to upload
     * @return The uploaded picture URL and uuid
     */
    @PostMapping(value = "picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ContentResponse uploadPostPicture(MultipartFile file) {
        return postService.savePostPicture(file);
    }

    /**
     * Removes the user profile picture
     *
     * @param uuid The picture unique identifier
     */
    @DeleteMapping("picture/{uuid}")
    @ApiResponse(responseCode = "204", description = "post picture removed")
    @ApiResponse(responseCode = "404", description = "post picture not found")
    public ResponseEntity<?> deletePostPicture(@PathVariable String uuid) {
        postService.removePostPicture(uuid);
        return ResponseEntity.noContent().build();
    }

    /**
     * Removes the specified post
     *
     * @param postId The id of the post
     */
    @DeleteMapping("{postId}")
    @ApiResponse(responseCode = "204", description = "post removed")
    @ApiResponse(responseCode = "404", description = "post not found")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.removePost(postId);
        return ResponseEntity.noContent().build();
    }

}
