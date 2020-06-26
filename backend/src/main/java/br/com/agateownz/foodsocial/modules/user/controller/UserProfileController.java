package br.com.agateownz.foodsocial.modules.user.controller;

import br.com.agateownz.foodsocial.modules.content.dto.response.ContentResponse;
import br.com.agateownz.foodsocial.modules.shared.service.StorageService;
import br.com.agateownz.foodsocial.modules.user.dto.request.ModifyUserProfileRequest;
import br.com.agateownz.foodsocial.modules.user.dto.response.UserProfileResponse;
import br.com.agateownz.foodsocial.modules.user.dto.response.UserProfileWithPictureResponse;
import br.com.agateownz.foodsocial.modules.user.service.UserProfileService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static br.com.agateownz.foodsocial.config.swagger.SwaggerConstants.COOKIE_AUTH;

@Validated
@RestController
@RequestMapping("/users/profile")
@Tag(name = "users profile")
@SecurityRequirement(name = COOKIE_AUTH)
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private StorageService storageService;

    /**
     * Gets the current logged user's profile
     *
     * @return The user profile
     */
    @GetMapping
    public UserProfileWithPictureResponse get() {
        return userProfileService.getFromAuthenticatedUser();
    }

    /**
     * Creates or modify the user profile
     *
     * @param request The user profile information
     * @return The updated user profile
     */
    @PostMapping
    @PutMapping
    public UserProfileResponse modify(@RequestBody ModifyUserProfileRequest request) {
        return userProfileService.save(request);
    }

    /**
     * Updates the user profile picture
     *
     * @param file The file to upload
     * @return The uploaded picture URL and uuid
     */
    @PostMapping(value = "picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ContentResponse uploadProfilePicture(MultipartFile file) {
        return userProfileService.saveProfilePicture(file);
    }

    /**
     * Removes the user profile picture
     */
    @DeleteMapping("picture")
    @ApiResponse(responseCode = "204", description = "profile picture removed")
    public ResponseEntity<?> deleteProfilePicture() {
        userProfileService.removeProfilePicture();
        return ResponseEntity.noContent().build();
    }

}
