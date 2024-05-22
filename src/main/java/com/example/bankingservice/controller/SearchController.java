package com.example.bankingservice.controller;

import com.example.bankingservice.model.User;
import com.example.bankingservice.repository.UserRepository;
import com.example.bankingservice.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> searchUsers(@RequestParam(required = false) LocalDate birthDate,
                                  @RequestParam(required = false) String phoneNumber,
                                  @RequestParam(required = false) String email,
                                  @RequestParam(required = false) String fullName,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {

        Specification<User> specification = Specification.where(null);

        if (birthDate != null) {
            specification = specification.and(UserSpecification.hasBirthDateGreaterThan(birthDate));
        }
        if (phoneNumber != null) {
            specification = specification.and(UserSpecification.hasPhoneNumber(phoneNumber));
        }
        if (email != null) {
            specification = specification.and(UserSpecification.hasEmail(email));
        }
        if (fullName != null) {
            specification = specification.and(UserSpecification.hasFullName(fullName));
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return userRepository.findAll(specification, pageable).getContent();
    }
}
