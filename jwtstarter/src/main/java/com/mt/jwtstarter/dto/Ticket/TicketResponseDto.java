package com.mt.jwtstarter.dto.Ticket;


import com.mt.jwtstarter.dto.Auth.UserResponseDto;
import com.mt.jwtstarter.dto.Category.CategoryResponseDto;
import com.mt.jwtstarter.dto.Subcategory.SubcategoryResponseDto;
import com.mt.jwtstarter.enums.Channel;
import com.mt.jwtstarter.enums.Priority;
import com.mt.jwtstarter.model.Customer;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class TicketResponseDto {

    private Long id;
    private Customer customer;
    private String content;
    private boolean isOpen;
    private Channel channel;
    private CategoryResponseDto category;
    private SubcategoryResponseDto subcategory;
    private Priority priority;
    private UserResponseDto openedBy;
    private UserResponseDto closedBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp closedAt;
}
