package com.CompanyRegistryService.Entity;

import java.util.UUID;

import com.CompanyRegistryService.Repository.UpdateValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
	
	private UUID id;


	@NotBlank(message = "Company name is required")
	@Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
	private String companyName;

	private String registrationNumber;  

	@NotBlank(message = "Country is required")
	private String country;

	@NotBlank(message = "Address1 is required")
	@Size(max = 200, message = "Address1 must not exceed 200 characters")
	private String address1;

	@Size(max = 200, message = "Address2 must not exceed 200 characters")
	private String address2;

	@NotBlank(message = "City is required")
	@Size(max = 100, message = "City must not exceed 100 characters")
	private String city;

	@NotBlank(message = "State is required")
	@Size(max = 100, message = "State must not exceed 100 characters")
	private String state;

	@NotBlank(message = "Zip code is required")
	@Pattern(regexp = "^[0-9]{6}$", message = "Invalid zip code format")
	private String zip;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;

	@NotBlank(message = "Mobile number is required")
	@Pattern(regexp = "^\\+\\d{1,3}[0-9]{6,14}$", message = "Invalid mobile number format")
	private String mobile;

	@Pattern(regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$", message = "Invalid website URL format")
	private String website;


	@Pattern(regexp = ".+", message = "Updated by is required", groups = UpdateValidation.class)
	private String updatedBy;
}
