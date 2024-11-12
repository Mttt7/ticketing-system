package com.mt.jwtstarter.dto.Ticket;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mt.jwtstarter.deserializer.EmptyStringToChannelDeserializer;
import com.mt.jwtstarter.deserializer.EmptyStringToPriorityDeserializer;
import com.mt.jwtstarter.enums.Channel;
import com.mt.jwtstarter.enums.Priority;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchTicketRequestDto {
    private Long ticketId;
    private Long customerId;
    private String customerEmail;
    private String customerPhone;
    private String content;
    private Boolean isOpen;
    private Boolean isFollowed;
    @JsonDeserialize(using = EmptyStringToChannelDeserializer.class)
    private Channel channel;
    private Long categoryId;
    private Long subcategoryId;
    @JsonDeserialize(using = EmptyStringToPriorityDeserializer.class)
    private Priority priority;
    private Long openedById;
    private Long closedById;
    private LocalDate createdAfter;
    private LocalDate createdBefore;
}
