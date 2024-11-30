package com.mt.jwtstarter.dto.Ticket;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mt.jwtstarter.deserializer.EmptyStringToPriorityDeserializer;
import com.mt.jwtstarter.enums.Priority;
import lombok.Data;

@Data
public class SearchTicketSimpleRequestDto {
    private Boolean isOpen;
    private Boolean isFollowed;
    @JsonDeserialize(using = EmptyStringToPriorityDeserializer.class)
    private Priority priority;
}
