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
public class CountryCodeEntity extends BaseEntity {
    @Column(unique = true)
    private String code;
    private String d3;
    private String name;
}
