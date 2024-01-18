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
public class SmsEntity extends BaseEntity {
    private String identity;
    private String sender;
    private String time;
    private String message;
    private String phoneNumber;
}
