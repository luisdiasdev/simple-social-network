package br.com.agateownz.foodsocial.modules.user.repository;

import br.com.agateownz.foodsocial.modules.user.dto.response.MentionUserResponse;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * This class was needed to workaround a Micronaut Data JPA limitation: 1. I can't create a native query in a @Repository
 * interface with custom result mappings (dto, projections, whatever).
 */
@Service
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public CustomUserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public List<MentionUserResponse> findMentionableUsers(Long userId, String search) {
        return entityManager
            .createNamedQuery("UserMentionSearch", MentionUserResponse.class)
            .setParameter("userId", userId)
            .setParameter("search", search)
            .getResultList();
    }
}
