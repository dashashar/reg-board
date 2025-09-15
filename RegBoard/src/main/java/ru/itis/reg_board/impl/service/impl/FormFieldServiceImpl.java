package ru.itis.reg_board.impl.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.reg_board.api.dto.request.FieldRequest;
import ru.itis.reg_board.api.dto.response.FieldResponse;
import ru.itis.reg_board.impl.exception.DataBaseException;
import ru.itis.reg_board.impl.exception.bad_request.EventMismatchException;
import ru.itis.reg_board.impl.exception.not_found.FieldNotFoundException;
import ru.itis.reg_board.impl.mapper.FieldMapper;
import ru.itis.reg_board.impl.model.EventEntity;
import ru.itis.reg_board.impl.model.FieldEntity;
import ru.itis.reg_board.impl.repository.FormFieldRepository;
import ru.itis.reg_board.impl.service.EventService;
import ru.itis.reg_board.impl.service.FormFieldService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FormFieldServiceImpl implements FormFieldService {

    private final EventService eventService;
    private final FormFieldRepository formRepository;
    private final FieldMapper fieldMapper;

    @Override
    public List<FieldResponse> getFormFields(long eventId) {
        try {
            return fieldMapper.toResponses(formRepository.findByEvent_IdOrderByPosition(eventId));
        } catch(DataAccessException e) {
            log.error("Не удалось получить поля формы регистрации на событие id - {} : {}", eventId, e.getMessage());
            throw new DataBaseException("Не удалось получить поля формы регистрации на событие", e);
        }
    }

    @Override
    @Transactional
    public void updateFormFields(long orgId, long eventId, List<FieldRequest> fields) {
        eventService.checkOrgMismatch(orgId, eventId);
        for (FieldRequest requestField : fields) {
            if (requestField.deleted()) {
                formRepository.deleteById(requestField.id());
            } else if (requestField.id() != null) {
                FieldEntity field = formRepository.findById(requestField.id())
                        .orElseThrow(() -> new FieldNotFoundException("Поле %d формы не найдено".formatted(requestField.id())));
                if (field.getEvent().getId() != eventId) {
                    log.error("Событие {}, которому принадлежит поле формы, не соответствует указанному событию {}", field.getEvent().getId(), eventId);
                    throw new EventMismatchException("Событие, которому принадлежит поле формы, не соответствует указанному событию");
                }
                field.setQuestion(requestField.question());
                field.setIsRequired(requestField.isRequired());
                field.setPosition(requestField.position());
                field.setOptions(requestField.options());
                formRepository.save(field);
            } else {
                FieldEntity field = fieldMapper.toEntity(requestField);
                field.setEvent(EventEntity.builder().id(eventId).build());
                formRepository.save(field);
            }
        }
    }

}
