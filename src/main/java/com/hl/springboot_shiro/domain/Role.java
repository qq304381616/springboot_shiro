package com.hl.springboot_shiro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    private String name;

    @ManyToMany
    @JoinTable(name = "role_permission",
            joinColumns = @JoinColumn(name = "role", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission", referencedColumnName = "id"))
    private Set<Permission> permissions;

}
