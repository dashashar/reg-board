-- liquibase formatted sql

-- changeset dasha:20252521-create-index-mtm
CREATE INDEX org_ac_index ON organization_account (account_id, organization_id)