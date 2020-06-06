package br.com.agateownz.foodsocial.modules.user.repository;

import br.com.agateownz.foodsocial.modules.user.dto.response.MentionUserResponse;

import java.util.List;

public interface CustomUserRepository {

    List<MentionUserResponse> findMentionableUsers(Long userId, String search);
}
