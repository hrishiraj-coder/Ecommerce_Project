package com.ums.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Data
@Entity
@Table(name = "user_details")
@Builder(builderMethodName = "of")
@NoArgsConstructor
@AllArgsConstructor
@GenericGenerator(name = "user_account_foreign_gen",
        strategy = "foreign",parameters = {@Parameter(name = "property",value = "userAccount")})
public class UserDetails {

    @Id
    @Column(name = "user_account_id")
    @GeneratedValue(generator = "user_account_foreign_gen")
    private long userAccountId;
    @Column(name = "experience")
    private String experience;
    @Column(name = "highest_degree")
    private String highestDegree;
    @Column(name = "position_type")
    private String positionType;
    @Column(name = "hired_dt")
    private String hiredDateTime;
    @Column(name = "terminated_dt")
    private String terminatedDateTime;
    @Column(name = "reason_for_termination")
    private String reasonForTermination;
    @OneToOne
    @PrimaryKeyJoinColumn
    private UserAccount userAccount;
    @Column(name = "last_modified_dt")
    private String lastModifiedDateTime;
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
}
