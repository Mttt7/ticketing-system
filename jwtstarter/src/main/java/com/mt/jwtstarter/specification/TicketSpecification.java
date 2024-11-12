package com.mt.jwtstarter.specification;

import com.mt.jwtstarter.enums.Channel;
import com.mt.jwtstarter.enums.Priority;
import com.mt.jwtstarter.model.Ticket;
import com.mt.jwtstarter.model.UserEntity;
import com.mt.jwtstarter.model.UserTicketFollower;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;

public class TicketSpecification {

    public static Specification<Ticket> hasTicketId(Long ticketId) {
        return (root, query, criteriaBuilder) -> {
            if (ticketId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("id"), ticketId);
        };
    }

    public static Specification<Ticket> hasCustomerId(Long customerId) {
        return (root, query, criteriaBuilder) -> {
            if (customerId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("customer").get("id"), customerId);
        };
    }

    public static Specification<Ticket> hasContent(String content) {
        return (root, query, criteriaBuilder) -> {
            if (content == null || content.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), "%" + content.toLowerCase() + "%");
        };
    }

    public static Specification<Ticket> isOpen(Boolean isOpen) {
        return (root, query, criteriaBuilder) -> {
            if (isOpen == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("isOpen"), isOpen);
        };
    }
    public static Specification<Ticket> isFollowed(UserEntity user, Boolean isFollowed) {
        return (root, query, criteriaBuilder) -> {
            if (isFollowed == null) {
                return criteriaBuilder.conjunction();
            }

            // Dołącz encję UserTicketFollower, aby uzyskać dostęp do relacji obserwujących
            Join<Ticket, UserTicketFollower> followersJoin = root.join("followers");

            // Warunek zależny od wartości isFollowed
            if (isFollowed) {
                // Zwróć tylko te tickety, które są obserwowane przez podanego użytkownika
                return criteriaBuilder.equal(followersJoin.get("user"), user);
            } else {
                // Zwróć tickety, które nie są obserwowane przez podanego użytkownika
                return criteriaBuilder.notEqual(followersJoin.get("user"), user);
            }
        };
    }
    public static Specification<Ticket> hasChannel(Channel channel) {
        return (root, query, criteriaBuilder) -> {
            if (channel == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("channel"), channel);
        };
    }

    public static Specification<Ticket> hasCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }

    public static Specification<Ticket> hasSubcategoryId(Long subcategoryId) {
        return (root, query, criteriaBuilder) -> {
            if (subcategoryId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("subcategory").get("id"), subcategoryId);
        };
    }

    public static Specification<Ticket> hasPriority(Priority priority) {
        return (root, query, criteriaBuilder) -> {
            if (priority == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("priority"), priority);
        };
    }

    public static Specification<Ticket> hasOpenedById(Long openedById) {
        return (root, query, criteriaBuilder) -> {
            if (openedById == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("openedBy").get("id"), openedById);
        };
    }

    public static Specification<Ticket> hasClosedById(Long closedById) {
        return (root, query, criteriaBuilder) -> {
            if (closedById == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("closedBy").get("id"), closedById);
        };
    }

    public static Specification<Ticket> createdAfter(Timestamp createdAfter) {
        return (root, query, criteriaBuilder) -> {
            if (createdAfter == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAfter);
        };
    }

    public static Specification<Ticket> createdBefore(Timestamp createdBefore) {
        return (root, query, criteriaBuilder) -> {
            if (createdBefore == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), createdBefore);
        };
    }

    public static Specification<Ticket> hasCustomerEmail(String customerEmail) {
        return (root, query, criteriaBuilder) -> {
            if (customerEmail == null || customerEmail.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.join("customer").get("email"), customerEmail);
        };
    }

    public static Specification<Ticket> hasCustomerPhone(String customerPhone) {
        return (root, query, criteriaBuilder) -> {
            if (customerPhone == null || customerPhone.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.join("customer").get("phone"), customerPhone);
        };
    }
}
