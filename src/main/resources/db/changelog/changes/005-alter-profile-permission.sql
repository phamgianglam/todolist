ALTER TABLE profile_user
ADD COLUMN account_non_blocked boolean DEFAULT true,
ADD COLUMN days_to_expire_password int DEFAULT 180,
ADD COLUMN password_non_expired boolean DEFAULT false,
ADD COLUMN last_password_reset_date timestamp DEFAULT now(),
ADD COLUMN enabled boolean DEFAULT true;

CREATE TABLE IF NOT EXISTS permission (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS profile_permissions (
    profile_id BIGINT REFERENCES profile_user(id) ON DELETE CASCADE,
    permission_id BIGINT REFERENCES permission(id) ON DELETE CASCADE
);