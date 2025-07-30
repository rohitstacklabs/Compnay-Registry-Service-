package com.CompanyRegistryService.Repository;

import com.CompanyRegistryService.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
    boolean existsByRegistrationNumber(String registrationNumber);
    Optional<Company> findByRegistrationNumber(String registrationNumber);
    List<Company> findByCompanyNameContainingIgnoreCase(String companyName);
    List<Company> findByRegistrationNumberContainingIgnoreCase(String registrationNumber);
    List<Company> findByEmailContainingIgnoreCase(String email);
    boolean existsByEmail(String email);
    boolean existsByCompanyName(String companyName);
    boolean existsByMobile(String mobile);
    List<Company> findByMobileContainingIgnoreCase(String mobile); 
}