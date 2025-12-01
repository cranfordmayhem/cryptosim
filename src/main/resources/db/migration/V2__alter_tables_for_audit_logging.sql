ALTER TABLE user_account
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN created_date TIMESTAMP,
ADD COLUMN last_modified_by VARCHAR(255),
ADD COLUMN last_modified_date TIMESTAMP;

ALTER TABLE user_profile
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN created_date TIMESTAMP,
ADD COLUMN last_modified_by VARCHAR(255),
ADD COLUMN last_modified_date TIMESTAMP;

ALTER TABLE portfolio
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN created_date TIMESTAMP,
ADD COLUMN last_modified_by VARCHAR(255),
ADD COLUMN last_modified_date TIMESTAMP;

ALTER TABLE portfolio_holding
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN created_date TIMESTAMP,
ADD COLUMN last_modified_by VARCHAR(255),
ADD COLUMN last_modified_date TIMESTAMP;

ALTER TABLE crypto_asset
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN created_date TIMESTAMP,
ADD COLUMN last_modified_by VARCHAR(255),
ADD COLUMN last_modified_date TIMESTAMP;

ALTER TABLE crypto_price_history
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN created_date TIMESTAMP,
ADD COLUMN last_modified_by VARCHAR(255),
ADD COLUMN last_modified_date TIMESTAMP;

ALTER TABLE refresh_tokens
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN created_date TIMESTAMP,
ADD COLUMN last_modified_by VARCHAR(255),
ADD COLUMN last_modified_date TIMESTAMP;