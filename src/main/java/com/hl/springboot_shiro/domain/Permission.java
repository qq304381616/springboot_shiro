package com.hl.springboot_shiro.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "permission")
public class Permission implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    private String name;
}
