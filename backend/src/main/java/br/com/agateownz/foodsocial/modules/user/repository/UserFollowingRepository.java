package br.com.agateownz.foodsocial.modules.user.repository;

import br.com.agateownz.foodsocial.modules.user.model.UserFollowing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFollowingRepository extends CrudRepository<UserFollowing, Long> {

}
