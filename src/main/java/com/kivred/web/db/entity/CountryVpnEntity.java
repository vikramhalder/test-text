package com.kivred.web.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Table
@Entity
@EqualsAndHashCode(callSuper = true)
public class CountryVpnEntity extends BaseEntity {
    @Column(unique = true)
    private String identity;
    private String ip;
    private String port;
    private boolean active;
    private String speed;
    private String city;
    private String type;
    private String countryCode;
}
