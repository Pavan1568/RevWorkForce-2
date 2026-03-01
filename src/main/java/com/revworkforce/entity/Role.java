package com.revworkforce.entity;

import com.revworkforce.enums.RoleType;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleType name;



    // getters & setters
    public RoleType getName() {
        return name;
    }

    public void setName(RoleType name) {
        this.name = name;
    }


}