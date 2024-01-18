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
public class PhoneNumberEntity extends BaseEntity {
    @Column(unique = true)
    private String identity;
    private String number;
    private String country;
    private String url;
    private String message;
    private boolean valid;
    private String type;
    private int hitCount;
}
