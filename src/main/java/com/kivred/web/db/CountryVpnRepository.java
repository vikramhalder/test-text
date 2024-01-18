package com.kivred.web.db;

import com.kivred.web.db.entity.CountryVpnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryVpnRepository extends JpaRepository<CountryVpnEntity, Long> {
    void deleteAllByIdentityIn(List<String> identity);

    @Query("select DISTINCT(c.countryCode) from CountryVpnEntity c order by c.countryCode")
    List<String> distinctCountryCode();

    List<CountryVpnEntity> findAllByCountryCodeOrderByUpdatedDesc(String countryCode);
}
