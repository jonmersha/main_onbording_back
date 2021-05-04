package com.coopay.repository;

import com.coopay.onboarding.entity.BankOfficer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BankOfficerRepo extends CrudRepository<BankOfficer,Integer> {

    @Query("select u from BankOfficer u where u.mobileNumber=?1 and u.password=?2")
    BankOfficer getRequestCredentials(String mobileNumber, String password);

    @Query("select u from BankOfficer u")
    List<BankOfficer> bankInformation();

    @Query("select u from BankOfficer u where (u.mobileNumber=?1 or u.userName=?1) and u.password=?2")
    BankOfficer offocerLogin(String userName, String password);


}
