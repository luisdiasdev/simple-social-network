package br.com.agateownz.foodsocial.modules.post.service;

import br.com.agateownz.foodsocial.modules.content.dto.response.ContentResponse;
import br.com.agateownz.foodsocial.modules.content.enums.ContentDiscriminator;
import br.com.agateownz.foodsocial.modules.content.service.ContentService;
import br.com.agateownz.foodsocial.modules.hashtag.service.HashtagService;
import br.com.agateownz.foodsocial.modules.post.dto.Mention;
import br.com.agateownz.foodsocial.modules.post.dto.request.PostCreateRequest;
import br.com.agateownz.foodsocial.modules.post.dto.response.PostResponse;
import br.com.agateownz.foodsocial.modules.post.exceptions.PostValidationException;
import br.com.agateownz.foodsocial.modules.post.mapper.PostMapper;
import br.com.agateownz.foodsocial.modules.post.model.*;
import br.com.agateownz.foodsocial.modules.post.repository.PostRepository;
import br.com.agateownz.foodsocial.modules.shared.service.AuthenticationService;
import br.com.agateownz.foodsocial.modules.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import static br.com.agateownz.foodsocial.modules.post.exceptions.PostExceptions.POST_NOT_FOUND;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PostMentionFinderService postMentionFinderService;
    @Autowired
    private HashtagService hashtagService;
    @Autowired
    private ContentService contentService;

    public PostResponse getById(Long id) {
        var post = postRepository.findByIdAndActiveTrue(id)
            .orElseThrow(() -> POST_NOT_FOUND);
        return postMapper.postToPostResponse(post);
    }

    @Transactional
    public PostResponse save(PostCreateRequest request) {
        validateMessage(request);
        validatePictures(request);
        var user = userService.findById(authenticationService.getAuthenticatedUserId());
        var message = getMessageAsString(request.getMessage());
        var mentions = postMentionFinderService.findMentions(request.getMessage());
        var post = Post.of(message, user);
        this.createUserMentions(post, mentions);
        this.createHashtagMentions(post, mentions);
        this.createPostPictures(post, request.getPictures(), user.getId());
        var savedPost = postRepository.save(post);
        return postMapper.postToPostResponse(savedPost);
    }

    private void createUserMentions(Post post, List<Mention> mentions) {
        var mentionUserIds = mentions.stream()
            .filter(Mention::isUserMention)
            .map(Mention::getId)
            .collect(Collectors.toList());
        var postMentions = userService.findByIds(mentionUserIds)
            .stream()
            .map(user ->
                PostMention.builder()
                    .id(new PostMentionId(post, user))
                    .build())
            .collect(Collectors.toSet());
        post.setMentions(postMentions);
    }

    private void createHashtagMentions(Post post, List<Mention> mentions) {
        var hashtagMentions = mentions.stream()
            .filter(Mention::isHashtagMention)
            .map(mention ->
                PostHashtag.builder()
                    .id(new PostHashtagId(
                        post,
                        hashtagService.getOrCreateHashtag(mention.getId(), mention.getName())))
                    .build())
            .collect(Collectors.toSet());
        post.setHashtags(hashtagMentions);
    }

    private void createPostPictures(Post post, List<String> picturesUuids, Long userId) {
        if (!CollectionUtils.isEmpty(picturesUuids)) {
            var postPictures = contentService.getContentsByUuidBelongingToUser(picturesUuids, userId)
                .stream()
                .map(content ->
                    PostContent.builder()
                        .id(new PostContentId(post, content))
                        .build())
                .collect(Collectors.toSet());
            post.setPictures(postPictures);
        }
    }

    private void validatePictures(PostCreateRequest request) {
        if (!CollectionUtils.isEmpty(request.getPictures())
            && !contentService.isContentsOwnedByCurrentUser(request.getPictures())) {
            throw new PostValidationException("Can only use your own pictures");
        }
    }

    private void validateMessage(PostCreateRequest request) {
        if (!isValidMessage(request.getMessage())) {
            throw new PostValidationException("Post message is invalid");
        }
    }

    @SuppressWarnings("unchecked")
    private boolean isValidMessage(Map message) {
        var operations = (List<Map>) message.get("ops");
        var hasOpsKey = Objects.nonNull(operations) && operations.size() > 0;
        return hasOpsKey && operations.stream()
            .anyMatch(m -> Objects.nonNull(m.get("insert")));
    }

    private String getMessageAsString(Map message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("could not convert map to string", e);
        }
    }

    public ContentResponse savePostPicture(MultipartFile file) {
        var content = contentService.save(ContentDiscriminator.POST_PICTURE, file);
        return new ContentResponse(content.getUuid(), content.getUri());
    }

    @Transactional
    public void removePostPicture(String uuid) {
        var userId = authenticationService.getAuthenticatedUserId();

        var content = contentService.findContentBelongingToUser(
            ContentDiscriminator.POST_PICTURE,
            uuid,
            userId);

        contentService.delete(content);
    }

    public Page<Post> getFeedForUser(Long userId, Pageable pageable) {
        return postRepository.findPostsForFeed(userId, pageable);
    }

    @Transactional
    public void removePost(Long postId) {
        var userId = authenticationService.getAuthenticatedUserId();

        postRepository.findByIdAndActiveTrueAndUserId(postId, userId)
            .orElseThrow(() -> POST_NOT_FOUND);

        postRepository.deletePostByIdAndUserId(postId, userId);
    }
}
