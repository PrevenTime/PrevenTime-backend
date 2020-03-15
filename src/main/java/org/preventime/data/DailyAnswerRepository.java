package org.preventime.data;

import org.preventime.data.util.AbstractAppUserOwnedRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyAnswerRepository extends AbstractAppUserOwnedRepository<DailyAnswer, QDailyAnswer> {

}
