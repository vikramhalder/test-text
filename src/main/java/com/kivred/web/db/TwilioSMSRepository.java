package com.kivred.web.db;

import com.kivred.web.db.entity.TwilioSMSEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwilioSMSRepository extends CrudRepository<TwilioSMSEntity, Long> {
}
