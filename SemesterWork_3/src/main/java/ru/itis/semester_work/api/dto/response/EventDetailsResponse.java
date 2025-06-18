package ru.itis.semester_work.api.dto.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDetailsResponse {
    private Long id;
    private String title;
    private String description;
    private String timeStart;
    private String timeEnd;
    private String city;
    private String address;
    private String imgId;
    private UUID registrationId;
    private OrganizationResponse org;
    private TicketResponse ticket;
    private List<FieldResponse> fields;
    private boolean registrationIsOpen;
    private boolean accountHasReg;
}
