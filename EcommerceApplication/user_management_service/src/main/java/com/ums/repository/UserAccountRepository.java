package com.ums.repository;

import com.ums.entites.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {

    long countByEmailAddress(String email);
    long countByMobileNo(String mobNo);



}
