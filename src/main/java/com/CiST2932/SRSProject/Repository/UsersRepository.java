// src/main/java/com/CiST2932/SRSProject/Repository/UsersRepository.java
package com.CiST2932.SRSProject.Repository;

import com.CiST2932.SRSProject.Domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    @Modifying
    @Query("DELETE FROM Users u WHERE u.employeeId = :employeeId")
    void deleteByEmployeeId(@Param("employeeId") int employeeId);

}