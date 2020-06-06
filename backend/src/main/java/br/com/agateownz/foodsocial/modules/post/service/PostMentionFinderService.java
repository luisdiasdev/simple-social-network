package br.com.agateownz.foodsocial.modules.post.service;

import br.com.agateownz.foodsocial.modules.post.dto.Mention;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostMentionFinderService {

    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("unchecked")
    public List<Mention> findMentions(Map post) {
        var operations = (List<Map>) post.get("ops");
        return operations.stream()
                .map(m -> m.get("insert"))
                .filter(i -> i instanceof Map)
                .map(insertMap -> ((Map) insertMap).getOrDefault("mention", null))
                .filter(Objects::nonNull)
                .map(mentions -> this.convertMapToMention((Map<String, String>) mentions))
                .collect(Collectors.toList());
    }

    private Mention convertMapToMention(Map<String, String> mentionMap) {
        return objectMapper.convertValue(mentionMap, Mention.class);
    }
}
