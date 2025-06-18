package ru.itis.semester_work.impl.mapper.impl;

import org.springframework.stereotype.Component;
import ru.itis.semester_work.api.dto.request.FieldAnswerRequest;
import ru.itis.semester_work.api.dto.response.FieldResponse;
import ru.itis.semester_work.api.dto.response.RegistrationTableResponse;
import ru.itis.semester_work.impl.mapper.RegistrationMapper;
import ru.itis.semester_work.impl.model.*;
import ru.itis.semester_work.impl.model.enums.RegistrationStatus;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class RegistrationMapperImpl implements RegistrationMapper {

    @Override
    public RegistrationEntity toEntity(long accountId, long eventId) {
        return RegistrationEntity.builder()
                .id(UUID.randomUUID())
                .status(RegistrationStatus.REGISTERED)
                .account(AccountEntity.builder().id(accountId).build())
                .event(EventEntity.builder().id(eventId).build())
                .build();
    }

    @Override
    public FieldAnswerEntity toEntity(FieldAnswerRequest request, RegistrationEntity registration) {
        if (request == null){
            return null;
        }
        return FieldAnswerEntity.builder()
                .registration(registration)
                .field(FieldEntity.builder().id(request.fieldId()).build())
                .answerValue(request.answerValue())
                .build();
    }

    @Override
    public RegistrationTableResponse toResponse(String eventTitle, List<FieldResponse> fields, List<RegistrationEntity> registrations) {
        if (registrations == null){
            return null;
        }
        List<String> headers = new ArrayList<>(Arrays.asList("№", "id", "статус"));
        fields.forEach(field -> headers.add(field.question()));
        AtomicInteger rowNumber = new AtomicInteger(1);
        List<List<String>> registrationRows = registrations.stream()
                .map(reg -> {
                    List<String> row = new ArrayList<>();
                    row.add(String.valueOf(rowNumber.getAndIncrement()));
                    row.add(reg.getId().toString());
                    row.add(String.valueOf(reg.getStatus()));
                    List<FieldAnswerEntity> answers = new ArrayList<>(reg.getFieldAnswers());
                    fields.forEach(field -> {
                        String answer = answers.stream()
                                .filter(fa -> fa.getField().getId().toString().equals(field.id()))
                                .findFirst()
                                .map(FieldAnswerEntity::getAnswerValue)
                                .orElse("");
                        row.add(answer);
                    });
                    return row;
                })
                .collect(Collectors.toList());
        return new RegistrationTableResponse(eventTitle, headers, registrationRows);
    }
}
