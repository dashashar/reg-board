package ru.itis.semester_work.api.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.semester_work.api.dto.request.FieldAnswerRequest;
import ru.itis.semester_work.api.dto.response.ConfirmationRegistrationResponse;
import ru.itis.semester_work.impl.security.details.AccountUserDetails;

import java.util.List;
import java.util.UUID;

@RequestMapping
public interface RegistrationApi {

    @GetMapping("/event")
    String getAllEvents(@RequestParam(required = false) Short categoryId,
                        @RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "9") int size,
                        Model model);

    @GetMapping("/event/{eventId}")
    String getEventDetails(@PathVariable long eventId,
                           @AuthenticationPrincipal AccountUserDetails userDetails,
                           Model model);

    @PostMapping("/registration/event/{eventId}")
    String registerForEvent(@PathVariable long eventId,
                            @Valid @RequestBody List<FieldAnswerRequest> answers,
                            @AuthenticationPrincipal AccountUserDetails userDetails);

    @GetMapping("/account/registration")
    String getEventsAccountHasRegistration(@AuthenticationPrincipal AccountUserDetails userDetails,
                                           Model model);

    @GetMapping("/registration/{regId}")
    @ResponseBody
    ResponseEntity<ConfirmationRegistrationResponse> confirmRegistration(@PathVariable UUID regId);

}
