package org.preventime.data;

import org.springframework.stereotype.Repository;
import org.preventime.data.util.AbstractRepository;

@Repository
public interface AppUserRepository extends AbstractRepository<AppUser, QAppUser> {

}
