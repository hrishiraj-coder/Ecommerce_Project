package com.ums.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "identity")
@Builder(builderMethodName = "of")
@NoArgsConstructor
@AllArgsConstructor
public class Identity {
    @Id
    @Column(name = "user_identity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userIdentityId;
    @ManyToOne
    @JoinColumn(name = "identity_type_id")
    private IdentityType userIdentityTypeId;
    @Column(name = "identity_number")
    private String identityNumber;
    @Column(name = "issued_authority")
    private String issuedAuthority;
    @Column(name = "expiry_year")
    private String expiryYear;
    @Column(name = "expiry_date")
    private String expiryDate;
    @Column(name = "last_modified_dt")
    private String lastModifiedDate;
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    @Column(name = "identity_file_access_uri")
    private String identityFileAccessUri;

}
