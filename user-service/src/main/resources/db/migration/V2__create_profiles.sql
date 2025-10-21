CREATE TABLE profiles (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL UNIQUE,
    avatar VARCHAR(255),
    bio TEXT,
    setting JSONB,
    CONSTRAINT fk_profiles_user FOREIGN KEY(user_id) REFERENCES users(id)
);