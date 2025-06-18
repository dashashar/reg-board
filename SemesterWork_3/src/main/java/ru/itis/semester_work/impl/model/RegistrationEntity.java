package ru.itis.semester_work.impl.model;

import jakarta.persistence.*;
import lombok.*;
import ru.itis.semester_work.impl.model.enums.RegistrationStatus;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registration")
public class RegistrationEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    @Builder.Default
    @OneToMany(mappedBy = "registration", fetch = FetchType.LAZY)
    private List<FieldAnswerEntity> fieldAnswers = new ArrayList<>();
}
