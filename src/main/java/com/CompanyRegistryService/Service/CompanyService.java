package com.CompanyRegistryService.Service;

import com.CompanyRegistryService.Entity.Company;
import com.CompanyRegistryService.Entity.CompanyDTO;
import com.CompanyRegistryService.Exception.CompanyNotFoundException;
import com.CompanyRegistryService.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {

	private final CompanyRepository companyRepository;

	@Autowired
	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public Company createCompany(CompanyDTO companyDTO) {

		String generatedRegNo = generateRegistrationNumber();
		while (companyRepository.existsByRegistrationNumber(generatedRegNo)) {
			generatedRegNo = generateRegistrationNumber(); 
		}

		if (companyRepository.existsByCompanyName(companyDTO.getCompanyName())) {
			throw new IllegalArgumentException("Company name already exists"); 
		}

		if (companyRepository.existsByEmail(companyDTO.getEmail())) {
			throw new IllegalArgumentException("Email already exists");
		}

		if (companyRepository.existsByMobile(companyDTO.getMobile())) {
			throw new IllegalArgumentException("Mobile number already exists");
		}

		Company company = Company.builder().companyName(companyDTO.getCompanyName()).registrationNumber(generatedRegNo)
				.address1(companyDTO.getAddress1()).address2(companyDTO.getAddress2()).city(companyDTO.getCity())
				.state(companyDTO.getState()).country(companyDTO.getCountry()).zip(companyDTO.getZip())
				.email(companyDTO.getEmail()).mobile(companyDTO.getMobile()).website(companyDTO.getWebsite())
				.createdBy(companyDTO.getCompanyName()) 
				.build();

		return companyRepository.save(company);
	}

	public Company updateCompany(UUID id, CompanyDTO companyDTO) {
		Company existingCompany = companyRepository.findById(id)
				.orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + id));

		

		if (!existingCompany.getCompanyName().equals(companyDTO.getCompanyName())
				&& companyRepository.findByCompanyNameContainingIgnoreCase(companyDTO.getCompanyName()).stream()
						.anyMatch(company -> !company.getId().equals(id))) {
			throw new IllegalArgumentException("Company name already exists");
		}

		if (!existingCompany.getEmail().equals(companyDTO.getEmail())
				&& companyRepository.existsByEmail(companyDTO.getEmail())) {
			throw new IllegalArgumentException("Email already exists");
		}

		if (!existingCompany.getMobile().equals(companyDTO.getMobile())
				&& companyRepository.existsByMobile(companyDTO.getMobile())) {
			throw new IllegalArgumentException("Mobile number already exists");
		}

		existingCompany.setCompanyName(companyDTO.getCompanyName());
		existingCompany.setAddress1(companyDTO.getAddress1());
		existingCompany.setAddress2(companyDTO.getAddress2());
		existingCompany.setCity(companyDTO.getCity());
		existingCompany.setState(companyDTO.getState());
		existingCompany.setZip(companyDTO.getZip());
		existingCompany.setEmail(companyDTO.getEmail());
		existingCompany.setMobile(companyDTO.getMobile());
		existingCompany.setWebsite(companyDTO.getWebsite());
		existingCompany.setCountry(companyDTO.getCountry());

		existingCompany.setUpdatedBy(companyDTO.getCompanyName());
		existingCompany.setUpdatedDate(LocalDateTime.now());

		return companyRepository.save(existingCompany);
	}

	public Company getCompanyById(UUID id) {
		return companyRepository.findById(id)
				.orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + id));
	}

	public Company getByRegistrationNumber(String regNo) {
		return companyRepository.findByRegistrationNumber(regNo).orElseThrow(
				() -> new CompanyNotFoundException("Company not found with registration number: " + regNo));
	}

	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	public void deleteCompany(UUID id) {
		if (!companyRepository.existsById(id)) {
			throw new CompanyNotFoundException("Company not found with id: " + id);
		}
		companyRepository.deleteById(id);
	}

	public List<Company> searchCompany(String name, String registrationNumber, String email, String mobile) {
	    if (name != null && !name.isEmpty()) {
	        return companyRepository.findByCompanyNameContainingIgnoreCase(name);
	    } else if (registrationNumber != null && !registrationNumber.isEmpty()) {
	        return companyRepository.findByRegistrationNumberContainingIgnoreCase(registrationNumber);
	    } else if (email != null && !email.isEmpty()) {
	        return companyRepository.findByEmailContainingIgnoreCase(email);
	    } else if (mobile != null && !mobile.isEmpty()) {
	        return companyRepository.findByMobileContainingIgnoreCase(mobile);
	    } else {
	        throw new IllegalArgumentException(
	                "At least one search parameter (name, registrationNumber, email, or mobile) must be provided");
	    }
	}
	private String generateRegistrationNumber() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder(10);
		for (int i = 0; i < 10; i++) {
			sb.append(chars.charAt((int) (Math.random() * chars.length())));
		}
		return sb.toString();
	}

}
