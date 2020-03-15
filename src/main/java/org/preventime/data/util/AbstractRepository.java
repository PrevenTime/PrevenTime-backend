package org.preventime.data.util;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

@NoRepositoryBean
public interface AbstractRepository<E extends AbstractEntity, QE extends EntityPathBase<E>>
        extends JpaRepository<E, String>, QuerydslPredicateExecutor<E>, QuerydslBinderCustomizer<QE> {

    List<E> findByIdIn(@Param("ids") String[] ids);

    @Override
    default public void customize(QuerydslBindings bindings, QE root) {

        bindings.bind(String.class).first((StringPath path, String value) -> {
           String[] strings = value.split(",");
           List<String> list = Arrays.asList(strings);
           BooleanBuilder predicate = new BooleanBuilder();
           list.forEach(v -> predicate.or(path.containsIgnoreCase(v)));
           return predicate;
        });

    }
}
