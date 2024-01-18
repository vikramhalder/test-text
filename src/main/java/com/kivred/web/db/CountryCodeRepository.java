package com.kivred.web.db;

import com.kivred.web.db.entity.CountryCodeEntity;
import org.springframework.data.repository.CrudRepository;

public interface CountryCodeRepository extends CrudRepository<CountryCodeEntity, Long> {
}
