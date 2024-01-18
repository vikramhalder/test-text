package com.kivred.web.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Table
@Entity
@EqualsAndHashCode(callSuper = true)
public class UsersEntity extends BaseEntity {
    private String name;
    private String email;
    private String phone;
    private String password;

    private String app;
    private String fcm;
    private String dob;
    private String types;
    private String address;
    private String status;
}
