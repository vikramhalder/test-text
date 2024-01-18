package com.kivred.web.db.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Table
@Entity
@EqualsAndHashCode(callSuper = true)
public class TwilioSMSEntity extends BaseEntity {
    private String numberTo;
    private String numberFrom;
    @Type(type = "text")
    private String text;
}
