package com.ums.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Entity
@Table(name = "address")
@Builder(builderMethodName = "of")
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    @Column(name = "address_line_1")
    private String addressLine1;
    @Column(name = "address_line_2")
    private String addressLine2;
    private String city;
    private String state;
    private String zip;
    private String country;
    @Column(name = "last_modified_dt")
    private Date lastModifiedDate;
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
}
