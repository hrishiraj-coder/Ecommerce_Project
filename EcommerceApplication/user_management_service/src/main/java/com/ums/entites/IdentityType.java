package com.ums.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "identity_types")
@Builder(builderMethodName = "of")
@NoArgsConstructor
@AllArgsConstructor
public class IdentityType {
    @Id
    @Column(name = "identity_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long identityTypeId;
    @Column(name = "identity_type")
    private String identityType;
    @Column(name = "display_nm")
    private String display_name;
    @Column(name = "last_modified_dt")
    private String last_modified_date;
    @Column(name = "last_modified_by")
    private String last_modified_by;
}
