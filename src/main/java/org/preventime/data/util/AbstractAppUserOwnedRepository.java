package org.preventime.data.util;

import com.querydsl.core.types.dsl.EntityPathBase;
import org.preventime.data.AppUser;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface AbstractAppUserOwnedRepository<E extends AbstractAppUserOwnedEntity, QE extends EntityPathBase<E>>
        extends AbstractRepository<E, QE>{

    List<E> findAllByAppUser(AppUser appUser);

}
