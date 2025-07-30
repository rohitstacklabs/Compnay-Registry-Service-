package com.CompanyRegistryService.Controller;


import com.CompanyRegistryService.Entity.Company;
import com.CompanyRegistryService.Entity.CompanyDTO;
import com.CompanyRegistryService.Exception.CompanyNotFoundException;
import com.CompanyRegistryService.Service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "http://localhost:5173")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<Company>createCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        return new ResponseEntity<>(companyService.createCompany(companyDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable UUID id,
                                                 @Valid @RequestBody CompanyDTO companyDTO) {
        return ResponseEntity.ok(companyService.updateCompany(id, companyDTO));
    }
    
    @GetMapping("/by-registration/{regNo}")
    public ResponseEntity<Company> getCompanyByRegistrationNumber(@PathVariable String regNo) {
        Company company = companyService.getByRegistrationNumber(regNo);
        return ResponseEntity.ok(company);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable UUID id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable UUID id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCompanyNotFoundException(CompanyNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    
    @GetMapping("/search")
    public ResponseEntity<List<Company>> searchCompanies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String registrationNumber,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String mobile) {
        return ResponseEntity.ok(companyService.searchCompany(name, registrationNumber, email, mobile));
    }

    

}
