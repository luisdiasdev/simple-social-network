package br.com.agateownz.foodsocial.modules.hashtag.repository;

import br.com.agateownz.foodsocial.modules.hashtag.model.Hashtag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashtagRepository extends CrudRepository<Hashtag, Long> {

    List<Hashtag> findTop10ByHashtagContainingIgnoreCaseOrderByHashtagAsc(String value);
}
