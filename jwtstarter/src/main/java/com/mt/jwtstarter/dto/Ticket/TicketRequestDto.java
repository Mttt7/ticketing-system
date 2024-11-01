package com.mt.jwtstarter.dto.Ticket;


import com.mt.jwtstarter.enums.Channel;
import com.mt.jwtstarter.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TicketRequestDto {

    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be a positive number")
    private Long customerId;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Channel is required")
    private Channel channel;

    @NotNull(message = "Priority is required")
    private Priority priority;

    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be a positive number")
    private Integer categoryId;

    @NotNull(message = "Subcategory ID is required")
    @Positive(message = "Subcategory ID must be a positive number")
    private Integer subcategoryId;
}

