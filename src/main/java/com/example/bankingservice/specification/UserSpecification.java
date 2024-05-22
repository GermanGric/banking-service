package com.example.bankingservice.specification;

import com.example.bankingservice.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class UserSpecification {

    public static Specification<User> hasBirthDateGreaterThan(LocalDate date) {
        return (user, cq, cb) -> cb.greaterThan(user.get("dateOfBirth"), date);
    }

    public static Specification<User> hasPhoneNumber(String phoneNumber) {
        return (user, cq, cb) -> cb.equal(user.get("phoneNumber"), phoneNumber);
    }

    public static Specification<User> hasEmail(String email) {
        return (user, cq, cb) -> cb.equal(user.get("email"), email);
    }

    public static Specification<User> hasFullName(String fullName) {
        return (user, cq, cb) -> cb.like(cb.lower(user.get("fullName")), fullName.toLowerCase() + "%");
    }
}
