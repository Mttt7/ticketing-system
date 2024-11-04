package com.mt.jwtstarter.specification;

import com.mt.jwtstarter.model.Customer;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

    public static Specification<Customer> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> {
            if (firstName == null || firstName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("firstName")),
                    firstName.toLowerCase()
            );
        };
    }

    public static Specification<Customer> hasLastName(String lastName) {
        return (root, query, criteriaBuilder) -> {
            if (lastName == null || lastName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("lastName")),
                    lastName.toLowerCase()
            );
        };
    }

    public static Specification<Customer> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null || email.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("email")),
                    email.toLowerCase()
            );
        };
    }

    public static Specification<Customer> hasPhone(String phone) {
        return (root, query, criteriaBuilder) -> {
            if (phone == null || phone.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("phone")),
                    phone.toLowerCase()
            );
        };
    }
}
