package com.kivred.web.db;

import com.kivred.web.db.entity.PhoneNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumberEntity, Long> {
    @Query("select p from PhoneNumberEntity p where p.url like %:value%")
    Optional<PhoneNumberEntity> findByUrlIsLike(String value);
}