package ru.itis.reg_board.impl.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "organization")
public class OrganizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String email;

    @Builder.Default
    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventEntity> events = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "organizations", fetch = FetchType.LAZY)
    private List<AccountEntity> accounts = new ArrayList<>();

    public void removeAllAccounts() {
        for (AccountEntity account : accounts) {
            account.getOrganizations().remove(this);
        }
        accounts.clear();
    }

}
