package com.kivred.web.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Table
@Entity
@EqualsAndHashCode(callSuper = true)
public class SupportMessageEntity extends BaseEntity {
    private Long userId;
    private boolean view = false;
    private String name = "";
    private String email = "";
    private String message = "";
    private String response = "";
}
