package com.kivred.web.db;

import com.kivred.web.db.entity.SupportMessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportRepository extends CrudRepository<SupportMessageEntity, Long> {

}
