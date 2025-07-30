package com.CompanyRegistryService.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

	@Id
    @GeneratedValue
    @Column(columnDefinition = "UUID", updatable = false)
    private UUID id;

	@NotBlank(message = "Company name is required")
	@Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
	@Column(nullable = false, unique = true)
	private String companyName;

	@NotBlank(message = "Registration number is required")
	@Pattern(regexp = "^[A-Z0-9]{8,20}$", message = "Invalid registration number format")
	@Column(unique = true, nullable = false)
	private String registrationNumber;

	@NotBlank(message = "Country is required")
	@Column(nullable = false)
	private String country;

	@NotBlank(message = "Address1 is required")
	@Size(max = 200, message = "Address1 must not exceed 200 characters")
	@Column(nullable = false)
	private String address1;

	@Size(max = 200, message = "Address2 must not exceed 200 characters")
	private String address2;

	@NotBlank(message = "City is required")
	@Size(max = 100, message = "City must not exceed 100 characters")
	@Column(nullable = false)
	private String city;

	@NotBlank(message = "State is required")
	@Size(max = 100, message = "State must not exceed 100 characters")
	@Column(nullable = false)
	private String state;

	@NotBlank(message = "Zip code is required")
	@Pattern(regexp = "^[0-9]{6}$", message = "Invalid zip code format")
	@Column(nullable = false)
	private String zip;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	@Column(nullable = false, unique = true)
	private String email;

	@NotBlank(message = "Mobile number is required")
	@Pattern(regexp = "^\\+\\d{1,3}[0-9]{6,14}$", message = "Invalid mobile number format")
	@Column(nullable = false, unique = true)
	private String mobile;

	@Pattern(regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$", message = "Invalid website URL format")
	private String website;

	@Column(nullable = false, updatable = false)
	private String createdBy; 

	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdDate;

	
	@Column(nullable = true)
	private String updatedBy;

	
	@Column(nullable = true)
	private LocalDateTime updatedDate;

}