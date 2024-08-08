package com.ums.repository;

import com.ums.dto.UserAccountDto;
import com.ums.entites.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    long countByEmailAddress(String email);

    long countByMobileNo(String mobNo);
    @Modifying
    @Query("update UserAccount ua set ua.emailVerificationStatus=?2 ,ua.mobileNoVerificationStatus=?3," +
            " ua.lastModifiedDate=?4,ua.activatedDate=?5,ua.status=?6 where ua.userAccountId=?1")
    int updateUserAccount(long userAccountId, int emailVerificationCodeVerifiedStatus,
                          int mobileNoVerificationCodeVerifiedStatus, LocalDateTime lastModifiedDate,
                          LocalDate activatedDate,String accountStatus);

    Optional<UserAccount> findByEmailAddress(String emailAddress);


}
