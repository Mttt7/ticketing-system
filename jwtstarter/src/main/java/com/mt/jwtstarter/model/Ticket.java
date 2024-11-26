package com.mt.jwtstarter.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mt.jwtstarter.enums.Channel;
import com.mt.jwtstarter.enums.Priority;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "ticket")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "content")
    private String content;

    @Column(name = "is_open")
    private Boolean isOpen;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel")
    private Channel channel;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private Subcategory subcategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "opened_by_user_id")
    private UserEntity openedBy;

    @ManyToOne
    @JoinColumn(name = "closed_by_user_id")
    private UserEntity closedBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "ticket")
    @JsonManagedReference
    @ToString.Exclude
    private List<UserTicketFollower> followers;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "closed_at")
    private Timestamp closedAt;
}
