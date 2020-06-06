package br.com.agateownz.foodsocial.modules.hashtag.service;

import br.com.agateownz.foodsocial.modules.hashtag.dto.response.HashtagResponse;
import br.com.agateownz.foodsocial.modules.hashtag.mapper.HashtagMapper;
import br.com.agateownz.foodsocial.modules.hashtag.model.Hashtag;
import br.com.agateownz.foodsocial.modules.hashtag.repository.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HashtagService {

    @Autowired
    private HashtagRepository hashtagRepository;
    @Autowired
    private HashtagMapper hashtagMapper;

    public Set<HashtagResponse> searchHashtags(String search) {
        var searchWithLike = "%".concat(search).concat("%");
        return hashtagRepository.findTop10ByHashtagContainingIgnoreCaseOrderByHashtagAsc(searchWithLike)
                .parallelStream()
                .map(hashtagMapper::hashtagToHashtagResponse)
                .collect(Collectors.toSet());
    }

    @Transactional
    public Hashtag getOrCreateHashtag(Long id, String value) {
        var cleanValue = cleanSpacedWords(stripHashtagSymbol(value));
        var hashtag = hashtagRepository.findById(id)
                .map(Hashtag::updateLastUsed)
                .orElseGet(() -> Hashtag.builder()
                        .hashtag(cleanValue)
                        .build()
                        .updateLastUsed());
        return hashtagRepository.save(hashtag);
    }

    private String stripHashtagSymbol(String value) {
        if (Objects.isNull(value)) {
            return value;
        }
        return value.replace("#", "");
    }

    private String cleanSpacedWords(String value) {
        if (Objects.isNull(value)) {
            return value;
        }
        return Arrays.stream(value.split("\\s+"))
                .limit(1)
                .collect(Collectors.joining());
    }
}
