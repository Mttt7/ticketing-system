package com.mt.jwtstarter.dto.Ticket;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatsResponseDto {
    private Long totalTickets;
    private Long openedToday;
    private Long closedToday;
    private Long openedTodayUser;
    private Long closedTodayUser;
    private Long commentedTodayUser;
    private Long openFollowed;
}
