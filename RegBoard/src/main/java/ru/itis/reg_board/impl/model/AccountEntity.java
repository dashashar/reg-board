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
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(name = "hash_password")
    private String hashPassword;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "organization_account",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id"))
    private List<OrganizationEntity> organizations = new ArrayList<>();

    public void addOrganization(OrganizationEntity organization) {
        organizations.add(organization);
        organization.getAccounts().add(this);
    }

    public void removeOrganization(OrganizationEntity organization) {
        organizations.remove(organization);
    }

}
