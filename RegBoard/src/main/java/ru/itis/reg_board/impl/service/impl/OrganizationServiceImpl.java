package ru.itis.reg_board.impl.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.reg_board.api.dto.request.OrganizationRequest;
import ru.itis.reg_board.api.dto.response.AccountResponse;
import ru.itis.reg_board.api.dto.response.OrganizationBriefResponse;
import ru.itis.reg_board.api.dto.response.OrganizationAdminResponse;
import ru.itis.reg_board.api.dto.response.OrganizationResponse;
import ru.itis.reg_board.impl.exception.DataBaseException;
import ru.itis.reg_board.impl.exception.conflict.AdminAlreadyExistsException;
import ru.itis.reg_board.impl.exception.not_found.AccountNotFoundException;
import ru.itis.reg_board.impl.exception.not_found.OrganizationNotFoundException;
import ru.itis.reg_board.impl.mapper.OrganizationMapper;
import ru.itis.reg_board.impl.model.AccountEntity;
import ru.itis.reg_board.impl.model.OrganizationEntity;
import ru.itis.reg_board.impl.repository.AccountRepository;
import ru.itis.reg_board.impl.repository.OrganizationRepository;
import ru.itis.reg_board.impl.service.OrganizationService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationServiceImpl implements OrganizationService {

    private final AccountRepository accountRepository;
    private final OrganizationRepository orgRepository;
    private final OrganizationMapper orgMapper;

    @Override
    public List<OrganizationBriefResponse> getAllBriefOrganizations(long accountId) {
        try {
            return orgRepository.findBriefOrgByAccountId(accountId).stream()
                    .map(orgMapper::toOrgBriefResponse)
                    .toList();
        } catch (DataAccessException e){
            log.error("Не удалось получить данные организаций: {}", e.getMessage());
            throw new DataBaseException("Не удалось получить данные организаций", e);
        }

    }

    @Override
    public OrganizationAdminResponse getOrganization(long orgId) {
        try {
            OrganizationEntity org = orgRepository.findByIdWithAccounts(orgId)
                    .orElseThrow(() -> new OrganizationNotFoundException("Организация не найдена"));
            return orgMapper.toOrgAdminResponse(org);
        } catch (DataAccessException e){
            log.error("Не удалось получить организацию: {}", e.getMessage());
            throw new DataBaseException("Не удалось получить организацию", e);
        }
    }

    @Override
    @Transactional
    public OrganizationAdminResponse createOrganization(long accountId, OrganizationRequest request) {
        try {
            AccountEntity admin = accountRepository.findById(accountId)
                    .orElseThrow(() -> new AccountNotFoundException("Аккаунт не найден"));
            OrganizationEntity org = orgMapper.toEntity(request);
            OrganizationEntity savedOrg = orgRepository.save(org);
            admin.addOrganization(org);
            accountRepository.save(admin);
            return orgMapper.toOrgAdminResponse(savedOrg);
        } catch (DataAccessException e) {
            log.error("Не удалось создать организацию: {}", e.getMessage());
            throw new DataBaseException("Не удалось создать организацию", e);
        }
    }

    @Override
    public OrganizationResponse updateOrganization(long orgId, OrganizationRequest request) {
        try {
            OrganizationEntity organization = orgRepository.findById(orgId)
                    .orElseThrow(() -> new OrganizationNotFoundException("Организация не найдена"));
            organization.setName(request.name());
            organization.setDescription(request.description());
            organization.setEmail(request.email());
            OrganizationEntity savedOrganization = orgRepository.save(organization);
            return orgMapper.toResponse(savedOrganization);
        } catch (DataAccessException e) {
            log.error("Не удалось отредактировать данные организации: {}", e.getMessage());
            throw new DataBaseException("Не удалось отредактировать данные организации", e);
        }
    }

    @Override
    @Transactional
    public void deleteOrganization(long orgId) {
        try {
            OrganizationEntity organization = orgRepository.findByIdWithAccounts(orgId)
                    .orElseThrow(() -> new OrganizationNotFoundException("Организация не найдена"));
            organization.removeAllAccounts();
            orgRepository.delete(organization);
        } catch(DataAccessException e) {
            log.error("Не удалось удалить организацию {}: {}", orgId, e.getMessage());
            throw new DataBaseException("Не удалось удалить организацию");
        }
    }

    @Override
    public AccountResponse addAdmin(long orgId, String email) {
        try {
            OrganizationEntity organization = orgRepository.findById(orgId)
                    .orElseThrow(() -> new OrganizationNotFoundException("Организация не найдена"));
            AccountEntity account = accountRepository.findByEmail(email)
                    .orElseThrow(() -> new AccountNotFoundException("Аккаунт не найден"));
            if (orgRepository.existsByAccountIdAndOrganizationId(account.getId(), orgId)) {
                log.error("Аккаунт {} уже добавлен как админ организации {}", email, orgId);
                throw new AdminAlreadyExistsException("Аккаунт уже добавлен как админ организации");
            }
            account.addOrganization(organization);
            AccountEntity addedAdmin = accountRepository.save(account);
            return orgMapper.toResponse(addedAdmin);
        } catch (DataAccessException e){
            log.error("Не удалось добавить админа у организации {}: {}", orgId, e.getMessage());
            throw new DataBaseException("Не удалось добавить админа", e);
        }
    }

    @Override
    @Transactional
    public void deleteAdmin(long orgId, long accountId) {
        try {
            AccountEntity account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new AccountNotFoundException("Аккаунт не найден"));
            OrganizationEntity organization = orgRepository.findById(orgId)
                    .orElseThrow(() -> new OrganizationNotFoundException("Организация не найдена"));
            account.removeOrganization(organization);
            accountRepository.save(account);
            orgRepository.save(organization);
        } catch(DataAccessException e) {
            log.error("Не удалось удалить администратора {}: {}", accountId, e.getMessage());
            throw new DataBaseException("Не удалось удалить администратора");
        }
    }
}
