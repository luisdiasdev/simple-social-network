package br.com.agateownz.foodsocial.modules.post.repository;

import br.com.agateownz.foodsocial.modules.content.model.QContent;
import br.com.agateownz.foodsocial.modules.post.model.Post;
import br.com.agateownz.foodsocial.modules.user.model.QUser;
import br.com.agateownz.foodsocial.modules.user.model.QUserProfile;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static br.com.agateownz.foodsocial.modules.content.model.QContent.content;
import static br.com.agateownz.foodsocial.modules.hashtag.model.QHashtag.hashtag1;
import static br.com.agateownz.foodsocial.modules.post.model.QPost.post;
import static br.com.agateownz.foodsocial.modules.post.model.QPostContent.postContent;
import static br.com.agateownz.foodsocial.modules.post.model.QPostHashtag.postHashtag;
import static br.com.agateownz.foodsocial.modules.post.model.QPostMention.postMention;
import static br.com.agateownz.foodsocial.modules.user.model.QUser.user;
import static br.com.agateownz.foodsocial.modules.user.model.QUserFollowing.userFollowing;
import static br.com.agateownz.foodsocial.modules.user.model.QUserProfile.userProfile;

public class PostRepositoryImpl implements PostRepositoryCustom {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Page<Post> findPostsForFeed(Long userId, Pageable pageable) {
        var queryFactory = new JPAQueryFactory(entityManager);

        var subQuery = JPAExpressions.select(userFollowing.id.following.id)
            .from(userFollowing)
            .where(userFollowing.id.user.id.eq(userId));

        // First query: Get total count
        var totalElements = queryFactory
            .select(post.countDistinct())
            .from(post)
            .where(post.user.id.in(subQuery).and(post.active.eq(true)))
            .fetchOne();

        if (ObjectUtils.isEmpty(totalElements)) {
            totalElements = 0L;
        }

        // Second query: Get paginated Post IDs only (no fetch joins)
        List<Long> postIds = queryFactory
            .select(post.id)
            .from(post)
            .where(post.user.id.in(subQuery).and(post.active.eq(true)))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(post.createdAt.desc())
            .fetch();

        if (postIds.isEmpty()) {
            return new PageImpl<>(
                Collections.emptyList(),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()),
                totalElements
            );
        }

        // Third query: Fetch full data for the paginated posts with all joins
        var postMentionUser = new QUser("postMentionUser");
        var postMentionUserProfile = new QUserProfile("postMentionUserProfile");
        var postMentionUserProfileImage = new QContent("postMentionUserProfileImage");

        List<Post> pageContent = queryFactory
            .select(post)
            .from(post)
            .leftJoin(post.user, user).fetchJoin()
            .leftJoin(user.userProfile, userProfile).fetchJoin()
            .leftJoin(userProfile.image).fetchJoin()
            .leftJoin(post.mentions, postMention).fetchJoin()
            .leftJoin(postMention.id.user, postMentionUser).fetchJoin()
            .leftJoin(postMentionUser.userProfile, postMentionUserProfile).fetchJoin()
            .leftJoin(postMentionUserProfile.image, postMentionUserProfileImage).fetchJoin()
            .leftJoin(post.hashtags, postHashtag).fetchJoin()
            .leftJoin(postHashtag.id.hashtag, hashtag1).fetchJoin()
            .leftJoin(post.pictures, postContent).fetchJoin()
            .leftJoin(postContent.id.content, content).fetchJoin()
            .where(post.id.in(postIds))
            .orderBy(post.createdAt.desc()) // Maintain the same ordering
            .fetch();

        return new PageImpl<>(
            pageContent,
            PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()),
            totalElements
        );
    }
}
