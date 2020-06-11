package br.com.agateownz.foodsocial.modules.hashtag.repository;

import br.com.agateownz.foodsocial.modules.hashtag.model.Hashtag;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends CrudRepository<Hashtag, Long> {

    List<Hashtag> findTop10ByHashtagContainingIgnoreCaseOrderByHashtagAsc(String value);
}
