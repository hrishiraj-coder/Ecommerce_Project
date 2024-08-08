package com.ums.entites;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "roles")
@Builder(builderMethodName = "of")
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;
    @Column(name = "role_cd")
    private String roleCode;
    @Column(name = "description")
    private String description;
    @Column(name = "last_modified_dt")
    private String last_modified_date;
    @Column(name = "last_modified_by")
    private String last_modified_by;
}
