package com.kivred.web.db;

import com.kivred.web.db.entity.SmsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmsRepository extends JpaRepository<SmsEntity, Long> {
    void deleteAllByIdentity(String identity);

    List<SmsEntity> findAllByIdentity(String identity);

    List<SmsEntity> findAllByPhoneNumber(String phoneNumber);

}