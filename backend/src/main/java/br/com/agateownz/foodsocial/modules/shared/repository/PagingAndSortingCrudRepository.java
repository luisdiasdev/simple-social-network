package br.com.agateownz.foodsocial.modules.shared.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PagingAndSortingCrudRepository<T, IdT> extends CrudRepository<T, IdT> {
    
}
