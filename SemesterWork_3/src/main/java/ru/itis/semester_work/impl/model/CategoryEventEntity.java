package ru.itis.semester_work.impl.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category_event")
public class CategoryEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short  id;

    private String name;
}
