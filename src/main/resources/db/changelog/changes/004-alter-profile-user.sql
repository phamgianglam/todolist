ALTER TABLE profile_user
 ALTER COLUMN username set NOT NULL,
 ALTER COLUMN password set NOT NULL;

ALTER TABLE profile_user
ADD COLUMN image_path VARCHAR(255);