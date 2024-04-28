// src/main/java/com/CiST2932/SRSProject/Repository/UsersRepository.java
package com.CiST2932.SRSProject.Repository;

import com.CiST2932.SRSProject.Domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

@Transactional
@Modifying
@Query("DELETE FROM Users u WHERE u.developerId.employeeId = :employeeId")
void deleteByEmployeeId(@Param("employeeId") int employeeId);

//findByDeveloperId
Users findByDeveloperId(int developerId);

}